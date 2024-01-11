package com.jeongg.ppap.presentation.subscribe_add

import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeongg.ppap.data.dto.SubscribeCreateRequestDTO
import com.jeongg.ppap.data.dto.SubscribeUpdateRequestDTO
import com.jeongg.ppap.domain.usecase.subscribe.CreateSubscribe
import com.jeongg.ppap.domain.usecase.subscribe.GetSubscribeById
import com.jeongg.ppap.domain.usecase.subscribe.UpdateSubscribe
import com.jeongg.ppap.presentation.util.PEvent
import com.jeongg.ppap.util.Resource
import com.jeongg.ppap.util.log
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubscribeAddViewModel @Inject constructor(
    private val createSubscribeUseCase: CreateSubscribe,
    private val updateSubscribeUseCase: UpdateSubscribe,
    private val getSubscribeByIdUseCase: GetSubscribeById,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private var subscribeId = mutableLongStateOf(-1L)

    private val _subscribe = mutableStateOf(SubscribeCreateRequestDTO("", "", null))
    val subscribe = _subscribe

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
                _subscribe.value = _subscribe.value.copy(
                    title = event.title
                )
            }
            is SubscribeAddEvent.EnteredNoticeLink -> {
                _subscribe.value = _subscribe.value.copy(
                    noticeLink = event.notice?.ifBlank { null }
                )
            }
            is SubscribeAddEvent.EnteredRssLink -> {
                _subscribe.value = _subscribe.value.copy(
                    rssLink = event.rss
                )
            }
            is SubscribeAddEvent.SaveSubscribe -> {
                if (subscribeId.longValue < 0) saveSubscribe()
                else updateSubscribe()
            }
        }
    }
    private fun saveSubscribe(){
        viewModelScope.launch {
            if (subscribe.value.title.isEmpty() || subscribe.value.rssLink.isEmpty()){
                _eventFlow.emit(PEvent.ERROR("제목과 rss 링크는 비어있으면 안됩니다."))
                return@launch
            }
            createSubscribeUseCase(subscribe.value).collect { response ->
                when(response){
                    is Resource.Loading -> _eventFlow.emit(PEvent.LOADING)
                    is Resource.Success -> _eventFlow.emit(PEvent.ADD)
                    is Resource.Error -> _eventFlow.emit(PEvent.ERROR(response.message))
                }
            }
        }
    }

    private fun updateSubscribe(){
        viewModelScope.launch {
            if (subscribe.value.title.isEmpty()){
                _eventFlow.emit(PEvent.ERROR("제목은 비어있으면 안됩니다."))
                return@launch
            }
            updateSubscribeUseCase(
                subscribeId = subscribeId.longValue,
                requestDTO = SubscribeUpdateRequestDTO(
                    title = subscribe.value.title,
                    noticeLink = subscribe.value.noticeLink
                )
            ).collect { response ->
                when(response){
                    is Resource.Loading -> _eventFlow.emit(PEvent.LOADING)
                    is Resource.Success -> _eventFlow.emit(PEvent.ADD)
                    is Resource.Error -> _eventFlow.emit(PEvent.ERROR(response.message))
                }
            }
        }
    }

    private fun getSubscribe(){
        viewModelScope.launch{
            getSubscribeByIdUseCase(subscribeId.longValue).collect { response ->
                when(response){
                    is Resource.Loading -> _eventFlow.emit(PEvent.LOADING)
                    is Resource.Success -> {
                        _subscribe.value = _subscribe.value.copy(
                            title = response.data?.title ?: "",
                            noticeLink = response.data?.noticeLink ?: "",
                            rssLink = response.data?.rssLink ?: ""
                        )
                        _eventFlow.emit(PEvent.GET)
                    }
                    is Resource.Error -> _eventFlow.emit(PEvent.ERROR(response.message))
                }
            }
        }
    }
}

