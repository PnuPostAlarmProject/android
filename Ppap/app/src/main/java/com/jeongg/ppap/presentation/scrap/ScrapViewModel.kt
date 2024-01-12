package com.jeongg.ppap.presentation.scrap

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.jeongg.ppap.data.dto.ScrapDTO
import com.jeongg.ppap.data.dto.SubscribeDTO
import com.jeongg.ppap.domain.usecase.scrap.AddScrap
import com.jeongg.ppap.domain.usecase.scrap.DeleteScrap
import com.jeongg.ppap.domain.usecase.scrap.GetScrapList
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
    private val addScrapUseCase: AddScrap,
    private val deleteScrapUseCase: DeleteScrap,
): ViewModel(){
    private val _subscribes = mutableStateOf<List<SubscribeDTO>>(emptyList())
    val subscribes = _subscribes

    private val _contents: Flow<PagingData<ScrapDTO>> = flow{}
    var contents = _contents

    private val _eventFlow = MutableSharedFlow<PEvent>()
    val eventFlow = _eventFlow

    init {
        getScrapList(null)
    }

    fun isEmptyList(): Boolean{
        return subscribes.value.isEmpty()
    }

    fun scrapEvent(isScraped: Boolean = false, contentId: Long = 0){
        if (isScraped) deleteScrap(contentId)
        else addScrap(contentId)
    }

    fun getScrapList(subscribeId: Long?) {
        // todo: subscribes 내용 불러오기
        _subscribes.value = listOf(SubscribeDTO(1, "더미"))
        contents = getScrapListUseCase(subscribeId).cachedIn(viewModelScope)
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