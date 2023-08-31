package com.jeongg.ppap.presentation.notice

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.jeongg.ppap.data.notice.NoticeRepository
import com.jeongg.ppap.data.notice.dto.ContentScrapDTO
import com.jeongg.ppap.data.notice.dto.SubscribeDTO
import com.jeongg.ppap.presentation.util.PEvent
import com.jeongg.ppap.util.log
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoticeViewModel  @Inject constructor(
    private val repository: NoticeRepository
): ViewModel() {
    private val _subscribes = mutableStateOf<List<SubscribeDTO>>(emptyList())
    val subscribes = _subscribes

    private val _curSubscribeId: MutableState<Long?> = mutableStateOf(null)

    private val _contents: Flow<PagingData<ContentScrapDTO>> = flow{}
    var contents = _contents

    private val _eventFlow = MutableSharedFlow<PEvent>()
    val eventFlow = _eventFlow

    init {
        getNoticePage(null)
        getNoticeList(null)
    }

    fun isEmptyList(): Boolean{
        return subscribes.value.isEmpty()
    }
    fun getNoticeList(subscribeId: Long?) {
        viewModelScope.launch{
            _eventFlow.emit(PEvent.LOADING)
            val response = repository.getNoticeList(subscribeId, null)
            if (response.success){
                _subscribes.value = response.response?.subscribes ?: emptyList()
                _curSubscribeId.value = response.response?.curSubscribeId
                if (_subscribes.value.isEmpty()) _eventFlow.emit(PEvent.ERROR("구독 목록이 비어있습니다."))
                else {
                    _eventFlow.emit(PEvent.SUCCESS)
                }
            } else {
                "공지 리스트 불러오기 실패".log()
                _eventFlow.emit(PEvent.ERROR("${response.error?.message}"))
            }
        }
    }
    fun getNoticePage(subscribeId: Long?){
        contents = repository.getNoticePage(subscribeId).cachedIn(viewModelScope)
    }
    fun scrapEvent(isScraped: Boolean = false, contentId: Long = 0){
        if (isScraped) deleteScrap(contentId)
        else addScrap(contentId)
    }

    private fun addScrap(contentId: Long){
        viewModelScope.launch{
            _eventFlow.emit(PEvent.LOADING)
            val response = repository.addScrap(contentId)
            if (response.success){
                "스크랩 성공".log()
                _eventFlow.emit(PEvent.SUCCESS)
            } else {
                _eventFlow.emit(PEvent.ERROR("${response.error?.message}"))
            }
        }
    }
    private fun deleteScrap(contentId: Long){
        viewModelScope.launch{
            _eventFlow.emit(PEvent.LOADING)
            val response = repository.deleteScrap(contentId)
            if (response.success){
                "스크랩 삭제 성공".log()
                _eventFlow.emit(PEvent.SUCCESS)
            } else {
                _eventFlow.emit(PEvent.ERROR("${response.error?.message}"))
            }
        }
    }
}