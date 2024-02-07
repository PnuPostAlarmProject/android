package com.jeongg.ppap.presentation.notice

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.jeongg.ppap.data.dto.NoticeDTO
import com.jeongg.ppap.domain.usecase.notice.GetNoticeList
import com.jeongg.ppap.domain.usecase.scrap.AddScrap
import com.jeongg.ppap.domain.usecase.scrap.DeleteScrap
import com.jeongg.ppap.domain.usecase.subscribe.GetSubscribes
import com.jeongg.ppap.presentation.mapper.NoticeItemMapper
import com.jeongg.ppap.presentation.state.NoticeItemState
import com.jeongg.ppap.presentation.util.PEvent
import com.jeongg.ppap.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
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

    private val _state = mutableStateOf(NoticeItemState())
    val state = _state

    private val _eventFlow = MutableSharedFlow<PEvent>()
    val eventFlow = _eventFlow

    init {
        getNoticePage(null)
        getSubscribes()
    }

    fun getNoticePage(subscribeId: Long?){
        _state.value = _state.value.copy(
            contents = NoticeItemMapper().noticeToNoticeItem(
                noticePagingData = getNoticeListUseCase(subscribeId).cachedIn(viewModelScope),
                scrapEvent = { isScraped, noticeDTO -> scrapEvent(isScraped, noticeDTO) }
            )
        )
    }

    private fun scrapEvent(isScraped: MutableState<Boolean>, noticeDTO: NoticeDTO){
        if (isScraped.value) deleteScrap(isScraped, noticeDTO)
        else addScrap(isScraped, noticeDTO)
    }

    private fun getSubscribes(){
        getSubscribesUseCase().onEach { response ->
            _state.value = _state.value.copy(
                isLoading = response is Resource.Loading,
                errorMessage = if (response is Resource.Error) response.message else "",
                subscribes = response.data ?: emptyList(),
            )
        }.launchIn(viewModelScope)
    }

    private fun addScrap(isScraped: MutableState<Boolean>, noticeDTO: NoticeDTO){
        viewModelScope.launch{
            addScrapUseCase(noticeDTO.contentId).collect { response ->
                when(response){
                    is Resource.Loading -> _eventFlow.emit(PEvent.Loading)
                    is Resource.Success -> {
                        isScraped.value = true
                        noticeDTO.isScraped = true
                    }
                    is Resource.Error -> _eventFlow.emit(PEvent.MakeToast(response.message))
                }
            }
        }
    }
    private fun deleteScrap(isScraped: MutableState<Boolean>, noticeDTO: NoticeDTO){
        viewModelScope.launch{
            deleteScrapUseCase(noticeDTO.contentId).collect { response ->
                when(response){
                    is Resource.Loading -> _eventFlow.emit(PEvent.Loading)
                    is Resource.Success -> {
                        isScraped.value = false
                        noticeDTO.isScraped = false
                    }
                    is Resource.Error -> _eventFlow.emit(PEvent.MakeToast(response.message))
                }
            }
        }
    }
}