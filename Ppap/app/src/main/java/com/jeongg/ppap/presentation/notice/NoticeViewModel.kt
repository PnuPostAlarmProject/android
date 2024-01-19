package com.jeongg.ppap.presentation.notice

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.jeongg.ppap.data.dto.NoticeDTO
import com.jeongg.ppap.data.dto.SubscribeDTO
import com.jeongg.ppap.data.dto.SubscribeGetResponseDTO
import com.jeongg.ppap.domain.usecase.notice.GetNoticeList
import com.jeongg.ppap.domain.usecase.scrap.AddScrap
import com.jeongg.ppap.domain.usecase.scrap.DeleteScrap
import com.jeongg.ppap.domain.usecase.subscribe.GetSubscribes
import com.jeongg.ppap.presentation.util.PEvent
import com.jeongg.ppap.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoticeViewModel  @Inject constructor(
    private val getNoticeListUseCase: GetNoticeList,
    private val getSubscribesUseCase: GetSubscribes,
    private val addScrapUseCase: AddScrap,
    private val deleteScrapUseCase: DeleteScrap
): ViewModel() {
    private val _subscribes = mutableStateOf<List<SubscribeGetResponseDTO>>(emptyList())
    val subscribes = _subscribes

    private val _contents: Flow<PagingData<NoticeDTO>> = flow{}
    var contents = _contents

    private val _eventFlow = MutableSharedFlow<PEvent>()
    val eventFlow = _eventFlow

    init {
        getNoticePage(null)
    }

    fun isEmptyList(): Boolean{
        return subscribes.value.isEmpty()
    }

    fun getNoticePage(subscribeId: Long?){
        contents = getNoticeListUseCase(subscribeId).cachedIn(viewModelScope)
        viewModelScope.launch {
            getSubscribesUseCase().collect { response ->
                when(response){
                    is Resource.Loading -> _eventFlow.emit(PEvent.LOADING)
                    is Resource.Success -> {
                        _subscribes.value = response.data ?: emptyList()
                        _eventFlow.emit(PEvent.SUCCESS)
                    }
                    is Resource.Error -> _eventFlow.emit(PEvent.ERROR(response.message))
                }
            }
        }
    }
    fun scrapEvent(isScraped: Boolean = false, contentId: Long = 0){
        if (isScraped) deleteScrap(contentId)
        else addScrap(contentId)
    }

    private fun addScrap(contentId: Long){
        viewModelScope.launch{
            addScrapUseCase(contentId).collect { response ->
                when(response){
                    is Resource.Loading -> _eventFlow.emit(PEvent.LOADING)
                    is Resource.Success -> _eventFlow.emit(PEvent.ADD)
                    is Resource.Error -> _eventFlow.emit(PEvent.ERROR(response.message))
                }
            }
        }
    }
    private fun deleteScrap(contentId: Long){
        viewModelScope.launch{
            deleteScrapUseCase(contentId).collect { response ->
                when(response){
                    is Resource.Loading -> _eventFlow.emit(PEvent.LOADING)
                    is Resource.Success -> _eventFlow.emit(PEvent.DELETE)
                    is Resource.Error -> _eventFlow.emit(PEvent.ERROR(response.message))
                }
            }
        }
    }
}