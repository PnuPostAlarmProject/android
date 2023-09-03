package com.jeongg.ppap.presentation.scrap

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.jeongg.ppap.data.notice.dto.SubscribeDTO
import com.jeongg.ppap.data.scrap.ScrapRepository
import com.jeongg.ppap.data.scrap.dto.ScrapDTO
import com.jeongg.ppap.presentation.util.PEvent
import com.jeongg.ppap.util.log
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScrapViewModel @Inject constructor(
    private val scrapRepository: ScrapRepository,
): ViewModel(){
    private val _subscribes = mutableStateOf<List<SubscribeDTO>>(emptyList())
    val subscribes = _subscribes

    private val _curSubscribeId: MutableState<Long?> = mutableStateOf(null)

    private val _contents: Flow<PagingData<ScrapDTO>> = flow{}
    var contents = _contents

    private val _eventFlow = MutableSharedFlow<PEvent>()
    val eventFlow = _eventFlow

    fun isEmptyList(): Boolean{
        return subscribes.value.isEmpty()
    }

    fun getScrapList(subscribeId: Long?) {
        viewModelScope.launch{
            _eventFlow.emit(PEvent.LOADING)
            val response = scrapRepository.getScrapList(subscribeId, null)
            if (response.success){
                _subscribes.value = response.response?.subscribes ?: emptyList()
                _curSubscribeId.value = response.response?.curSubscribeId
                if (_subscribes.value.isEmpty()) _eventFlow.emit(PEvent.ERROR("스크랩된 게시글이 없습니다."))
                else _eventFlow.emit(PEvent.SUCCESS)
            } else {
                "스크랩 리스트 불러오기 실패".log()
                _eventFlow.emit(PEvent.ERROR("${response.error?.message}"))
            }
        }
    }
    fun getScrapPage(subscribeId: Long?){
        contents = scrapRepository.getScrapPage(subscribeId).cachedIn(viewModelScope)
    }
    fun scrapEvent(isScraped: Boolean = false, contentId: Long = 0){
        if (isScraped) deleteScrap(contentId)
        else addScrap(contentId)
    }

    private fun addScrap(contentId: Long){
        viewModelScope.launch{
            _eventFlow.emit(PEvent.LOADING)
            val response = scrapRepository.addScrap(contentId)
            if (response.success){
                "스크랩 성공".log()
                _eventFlow.emit(PEvent.ADD)
            } else {
                _eventFlow.emit(PEvent.ERROR("${response.error?.message}"))
            }
        }
    }
    private fun deleteScrap(contentId: Long){
        viewModelScope.launch{
            _eventFlow.emit(PEvent.LOADING)
            val response = scrapRepository.deleteScrap(contentId)
            if (response.success){
                "스크랩 삭제 성공".log()
                _eventFlow.emit(PEvent.DELETE)
            } else {
                _eventFlow.emit(PEvent.ERROR("${response.error?.message}"))
            }
        }
    }
}