package com.jeongg.ppap.presentation.subscribe

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeongg.ppap.data.subscribe.SubscribeRepository
import com.jeongg.ppap.data.subscribe.dto.SubscribeCreateRequestDTO
import com.jeongg.ppap.data.subscribe.dto.SubscribeUpdateRequestDTO
import com.jeongg.ppap.presentation.util.PEvent
import com.jeongg.ppap.util.log
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubscribeAddViewModel @Inject constructor(
    private val subscribeRepository: SubscribeRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private var subscribeId = mutableLongStateOf(-1L)

    private val _title = mutableStateOf("")
    val title = _title

    private val _noticeLink: MutableState<String?> = mutableStateOf(null)
    val noticeLink = _noticeLink

    private val _rssLink = mutableStateOf("")
    val rssLink = _rssLink

    private val _eventFlow = MutableSharedFlow<PEvent>()
    val eventFlow = _eventFlow

    init {
        savedStateHandle.get<Long>("subscribeId")?.let{
            subscribeId.longValue = it
        }
        if(subscribeId.longValue >= 0) getSubscribe()
        "SubscribeId: ${subscribeId.longValue}".log()
    }

    fun isUpdate(): Boolean {
        return subscribeId.longValue >= 0
    }

    fun onEvent(event: SubscribeAddEvent){
        when (event){
            is SubscribeAddEvent.EnteredTitle -> {
                _title.value = event.title
            }
            is SubscribeAddEvent.EnteredNoticeLink -> {
                _noticeLink.value = event.notice
            }
            is SubscribeAddEvent.EnteredRssLink -> {
                _rssLink.value = event.rss
            }
            is SubscribeAddEvent.SaveSubscribe -> {
                if (subscribeId.longValue < 0) saveSubscribe()
                else updateSubscribe()
            }
        }
    }
    private fun saveSubscribe(){
        viewModelScope.launch {
            if (title.value.isEmpty() || rssLink.value.isEmpty()){
                _eventFlow.emit(PEvent.ERROR("제목과 rss 링크는 비어있으면 안됩니다."))
                return@launch
            }
            _eventFlow.emit(PEvent.LOADING)
            val response = subscribeRepository.createSubscribe(
                SubscribeCreateRequestDTO(
                    title = title.value,
                    noticeLink = noticeLink.value?.ifBlank { null },
                    rssLink = rssLink.value
                )
            )
            if (response.success){
                _eventFlow.emit(PEvent.ADD)
            } else {
                "구독 저장 실패 ${response.error?.status} ${noticeLink.value.isNullOrBlank()}".log()
                _eventFlow.emit(PEvent.ERROR("${response.error?.message}"))
            }
        }
    }

    private fun updateSubscribe(){
        viewModelScope.launch {
            if (title.value.isEmpty()){
                _eventFlow.emit(PEvent.ERROR("제목은 비어있으면 안됩니다."))
                return@launch
            }
            _eventFlow.emit(PEvent.LOADING)
            val response = subscribeRepository.updateSubscribe(
                subscribeId = subscribeId.longValue,
                subscribeUpdateRequestDTO = SubscribeUpdateRequestDTO(
                    title = title.value,
                    noticeLink = noticeLink.value?.ifBlank { null }
                )
            )
            if (response.success){
                _eventFlow.emit(PEvent.UPDATE)
            } else {
                "구독 업데이트 실패 ${response.error?.status}".log()
                _eventFlow.emit(PEvent.ERROR("${response.error?.message}"))
            }
        }
    }

    private fun getSubscribe(){
        viewModelScope.launch{
            _eventFlow.emit(PEvent.LOADING)
            val response = subscribeRepository.getSubscribeById(subscribeId.longValue)
            if (response.success){
                _title.value = response.response?.title ?: ""
                _noticeLink.value = response.response?.noticeLink
                _rssLink.value = response.response?.rssLink ?: ""
                _eventFlow.emit(PEvent.GET)
            } else {
                "불러오기 실패 ${response.error?.message}".log()
                _eventFlow.emit(PEvent.ERROR("구독 정보를 불러오는데 실패하였습니다."))
            }
        }
    }
}

