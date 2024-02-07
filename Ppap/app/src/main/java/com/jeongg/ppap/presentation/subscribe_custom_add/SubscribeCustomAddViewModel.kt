package com.jeongg.ppap.presentation.subscribe_custom_add

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeongg.ppap.data.dto.SubscribeCreateRequestDTO
import com.jeongg.ppap.domain.usecase.subscribe.CreateSubscribe
import com.jeongg.ppap.presentation.util.PEvent
import com.jeongg.ppap.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubscribeCustomAddViewModel @Inject constructor(
    private val createSubscribeUseCase: CreateSubscribe,
): ViewModel() {

    private val _subscribe = mutableStateOf(SubscribeCreateRequestDTO("", "", null))
    val subscribe = _subscribe

    private val _eventFlow = MutableSharedFlow<PEvent>()
    val eventFlow = _eventFlow

    fun onEvent(event: SubscribeCustomAddEvent){
        when (event){
            is SubscribeCustomAddEvent.EnteredTitle -> {
                _subscribe.value = _subscribe.value.copy(
                    title = event.title
                )
            }
            is SubscribeCustomAddEvent.EnteredRssLink -> {
                _subscribe.value = _subscribe.value.copy(
                    rssLink = event.rss
                )
            }
            is SubscribeCustomAddEvent.SaveSubscribe -> {
                saveSubscribe()
            }
        }
    }

    private fun saveSubscribe(){
        viewModelScope.launch {
            if (isInvalid()){
                _eventFlow.emit(PEvent.MakeToast("제목은 1~20자여야 하고\nRSS 링크는 비어있으면 안됩니다."))
                return@launch
            }
            createSubscribeUseCase(subscribe.value).collect { response ->
                when(response){
                    is Resource.Loading -> _eventFlow.emit(PEvent.Loading)
                    is Resource.Success -> _eventFlow.emit(PEvent.Navigate)
                    is Resource.Error -> _eventFlow.emit(PEvent.MakeToast(response.message))
                }
            }
        }
    }

    private fun isInvalid() = subscribe.value.title.isEmpty() || subscribe.value.rssLink.isEmpty()
            || subscribe.value.title.length > 20
}

