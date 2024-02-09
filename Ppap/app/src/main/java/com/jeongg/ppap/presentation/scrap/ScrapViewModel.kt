package com.jeongg.ppap.presentation.scrap

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.jeongg.ppap.data.dto.scrap.ScrapDTO
import com.jeongg.ppap.domain.usecase.scrap.AddScrap
import com.jeongg.ppap.domain.usecase.scrap.DeleteScrap
import com.jeongg.ppap.domain.usecase.scrap.GetScrapList
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
class ScrapViewModel @Inject constructor(
    private val getScrapListUseCase: GetScrapList,
    private val getSubscribesUseCase: GetSubscribes,
    private val addScrapUseCase: AddScrap,
    private val deleteScrapUseCase: DeleteScrap,
): ViewModel(){

    private val _state = mutableStateOf(NoticeItemState())
    val state = _state

    private val _eventFlow = MutableSharedFlow<PEvent>()
    val eventFlow = _eventFlow

    init {
        getScrapPage(null)
        getSubscribes()
    }

    fun getScrapPage(subscribeId: Long?){
        _state.value = _state.value.copy(
            contents = NoticeItemMapper().scrapToNoticeItem(
                scrapPagingData = getScrapListUseCase(subscribeId).cachedIn(viewModelScope),
                scrapEvent = { isScraped, scrapDTO -> scrapEvent(isScraped, scrapDTO) }
            )
        )
    }

    private fun scrapEvent(isScraped: MutableState<Boolean>, scrapDTO: ScrapDTO){
        if (isScraped.value) deleteScrap(isScraped, scrapDTO)
        else addScrap(isScraped, scrapDTO)
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

    private fun addScrap(isScraped: MutableState<Boolean>, scrapDTO: ScrapDTO){
        viewModelScope.launch{
            addScrapUseCase(scrapDTO.contentId).collect { response ->
                when(response){
                    is Resource.Loading -> _eventFlow.emit(PEvent.Loading)
                    is Resource.Success -> {
                        isScraped.value = true
                        scrapDTO.isScrap = true
                    }
                    is Resource.Error -> _eventFlow.emit(PEvent.MakeToast(response.message))
                }
            }
        }
    }

    private fun deleteScrap(isScraped: MutableState<Boolean>, scrapDTO: ScrapDTO){
        viewModelScope.launch{
            deleteScrapUseCase(scrapDTO.contentId).collect { response ->
                when(response){
                    is Resource.Loading -> _eventFlow.emit(PEvent.Loading)
                    is Resource.Success -> {
                        isScraped.value = false
                        scrapDTO.isScrap = false
                    }
                    is Resource.Error -> _eventFlow.emit(PEvent.MakeToast(response.message))
                }
            }
        }
    }
}