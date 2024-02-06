package com.jeongg.ppap.presentation.notice

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import com.jeongg.ppap.data.dto.NoticeDTO
import com.jeongg.ppap.data.dto.NoticeItemDTO
import com.jeongg.ppap.data.dto.SubscribeGetResponseDTO
import com.jeongg.ppap.domain.usecase.notice.GetNoticeList
import com.jeongg.ppap.domain.usecase.scrap.AddScrap
import com.jeongg.ppap.domain.usecase.scrap.DeleteScrap
import com.jeongg.ppap.domain.usecase.subscribe.GetSubscribes
import com.jeongg.ppap.presentation.mapper.NoticeItemMapper
import com.jeongg.ppap.presentation.util.PEvent
import com.jeongg.ppap.presentation.util.PState
import com.jeongg.ppap.util.Resource
import com.jeongg.ppap.util.log
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.cache
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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

    private val _contents: Flow<PagingData<NoticeItemDTO>> = flow{}
    var contents = _contents

    private val _state = mutableStateOf(PState())
    val state = _state

    private val _eventFlow = MutableSharedFlow<PEvent>()
    val eventFlow = _eventFlow

    init {
        getNoticePage(null)
        getSubscribes()
    }

    fun isEmpty(): Boolean {
        return subscribes.value.isEmpty()
    }

    fun getNoticePage(subscribeId: Long?){
        contents = NoticeItemMapper().noticeToNoticeItem(
            noticePagingData = getNoticeListUseCase(subscribeId).cachedIn(viewModelScope),
            scrapEvent = { isScraped, noticeDTO -> scrapEvent(isScraped, noticeDTO) }
        )
    }

    private fun scrapEvent(isScraped: MutableState<Boolean>, noticeDTO: NoticeDTO){
        if (isScraped.value) deleteScrap(isScraped, noticeDTO)
        else addScrap(isScraped, noticeDTO)
    }

    private fun getSubscribes(){
        getSubscribesUseCase().onEach { response ->
            when (response){
                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true,
                        errorMessage = ""
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        errorMessage = response.message
                    )
                }
                is Resource.Success -> {
                    _subscribes.value = response.data ?: emptyList()
                    _state.value = _state.value.copy(
                        isLoading = false,
                        isSubscribeEmpty = _subscribes.value.isEmpty(),
                        errorMessage = ""
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun addScrap(isScraped: MutableState<Boolean>, noticeDTO: NoticeDTO){
        viewModelScope.launch{
            addScrapUseCase(noticeDTO.contentId).collect { response ->
                when(response){
                    is Resource.Loading -> _eventFlow.emit(PEvent.LOADING)
                    is Resource.Success -> {
                        isScraped.value = true
                        noticeDTO.isScraped = true
                        _eventFlow.emit(PEvent.SUCCESS)
                    }
                    is Resource.Error -> _eventFlow.emit(PEvent.TOAST(response.message))
                }
            }
        }
    }
    private fun deleteScrap(isScraped: MutableState<Boolean>, noticeDTO: NoticeDTO){
        viewModelScope.launch{
            deleteScrapUseCase(noticeDTO.contentId).collect { response ->
                when(response){
                    is Resource.Loading -> _eventFlow.emit(PEvent.LOADING)
                    is Resource.Success -> {
                        isScraped.value = false
                        noticeDTO.isScraped = false
                        _eventFlow.emit(PEvent.SUCCESS)
                    }
                    is Resource.Error -> _eventFlow.emit(PEvent.TOAST(response.message))
                }
            }
        }
    }
}