package com.jeongg.ppap.presentation.scrap

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.jeongg.ppap.data.dto.NoticeItemDTO
import com.jeongg.ppap.data.dto.SubscribeGetResponseDTO
import com.jeongg.ppap.domain.usecase.scrap.AddScrap
import com.jeongg.ppap.domain.usecase.scrap.DeleteScrap
import com.jeongg.ppap.domain.usecase.scrap.GetScrapList
import com.jeongg.ppap.domain.usecase.subscribe.GetSubscribes
import com.jeongg.ppap.presentation.mapper.NoticeItemMapper
import com.jeongg.ppap.presentation.util.PEvent
import com.jeongg.ppap.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScrapViewModel @Inject constructor(
    private val getScrapListUseCase: GetScrapList,
    private val getSubscribesUseCase: GetSubscribes,
    private val addScrapUseCase: AddScrap,
    private val deleteScrapUseCase: DeleteScrap,
): ViewModel(){
    private val _subscribes = mutableStateOf<List<SubscribeGetResponseDTO>>(emptyList())
    val subscribes = _subscribes

    private val _contents: Flow<PagingData<NoticeItemDTO>> = flow{}
    var contents = _contents

    private val _eventFlow = MutableSharedFlow<PEvent>()
    val eventFlow = _eventFlow

    init {
        getScrapPage(null)
        getSubscribes()
    }

    fun isSubscribeListEmpty(): Boolean {
        return subscribes.value.isEmpty()
    }

    fun getScrapPage(subscribeId: Long?){
        contents = NoticeItemMapper().scrapToNoticeItem(
            scrapPagingData = getScrapListUseCase(subscribeId).cachedIn(viewModelScope),
            scrapEvent = { isScraped, contentId -> scrapEvent(isScraped, contentId) }
        )
    }

    private fun scrapEvent(isScraped: MutableState<Boolean>, contentId: Long ){
        if (isScraped.value) deleteScrap(isScraped, contentId)
        else addScrap(isScraped, contentId)
    }

    private fun getSubscribes(){
        viewModelScope.launch {
            getSubscribesUseCase().collect { response ->
                when(response){
                    is Resource.Loading -> _eventFlow.emit(PEvent.LOADING)
                    is Resource.Success -> {
                        _subscribes.value = response.data ?: emptyList()
                        _eventFlow.emit(PEvent.SUCCESS)
                    }
                    is Resource.Error -> _eventFlow.emit(PEvent.TOAST(response.message))
                }
            }
        }
    }

    private fun addScrap(isScraped: MutableState<Boolean>, contentId: Long){
        viewModelScope.launch{
            addScrapUseCase(contentId).collect { response ->
                when(response){
                    is Resource.Loading -> _eventFlow.emit(PEvent.LOADING)
                    is Resource.Success -> {
                        isScraped.value = true
                        _eventFlow.emit(PEvent.SUCCESS)
                    }
                    is Resource.Error -> _eventFlow.emit(PEvent.TOAST(response.message))
                }
            }
        }
    }

    private fun deleteScrap(isScraped: MutableState<Boolean>, contentId: Long){
        viewModelScope.launch{
            deleteScrapUseCase(contentId).collect { response ->
                when(response){
                    is Resource.Loading -> _eventFlow.emit(PEvent.LOADING)
                    is Resource.Success -> {
                        isScraped.value = false
                        _eventFlow.emit(PEvent.SUCCESS)
                    }
                    is Resource.Error -> _eventFlow.emit(PEvent.TOAST(response.message))
                }
            }
        }
    }
}