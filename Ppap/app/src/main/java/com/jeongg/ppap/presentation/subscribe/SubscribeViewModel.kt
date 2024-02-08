package com.jeongg.ppap.presentation.subscribe

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeongg.ppap.data.dto.SubscribeGetResponseDTO
import com.jeongg.ppap.domain.usecase.subscribe.DeleteSubscribe
import com.jeongg.ppap.domain.usecase.subscribe.GetSubscribes
import com.jeongg.ppap.domain.usecase.subscribe.UpdateActive
import com.jeongg.ppap.presentation.util.PEvent
import com.jeongg.ppap.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubscribeViewModel @Inject constructor(
    private val updateActiveUseCase: UpdateActive,
    private val deleteSubscribeUseCase: DeleteSubscribe,
    private val getSubscribesUseCase: GetSubscribes,
): ViewModel()  {

    private val _customSubscribes = mutableStateOf<List<SubscribeGetResponseDTO>>(emptyList())
    val customSubscribes = _customSubscribes

    private val _isLoading = mutableStateOf(true)
    val isLoading = _isLoading

    private val _eventFlow = MutableSharedFlow<PEvent>()
    val eventFlow = _eventFlow

    init {
        getSubscribes()
    }

    fun updateActive(subscribe: SubscribeGetResponseDTO, isActive: MutableState<Boolean>){
        viewModelScope.launch{
            updateActiveUseCase(subscribe.subscribeId).collect { response ->
                when(response){
                    is Resource.Loading -> _eventFlow.emit(PEvent.Loading)
                    is Resource.Success -> {
                        subscribe.isActive = subscribe.isActive.not()
                        isActive.value = isActive.value.not()
                    }
                    is Resource.Error -> _eventFlow.emit(PEvent.MakeToast(response.message))
                }
            }
        }
    }
    fun deleteSubscribe(subscribeId: Long){
        viewModelScope.launch{
            deleteSubscribeUseCase(subscribeId).collect { response ->
                when(response){
                    is Resource.Loading -> _eventFlow.emit(PEvent.Loading)
                    is Resource.Success -> {
                        _customSubscribes.value = _customSubscribes.value.filter {
                            it.subscribeId != subscribeId
                        }
                    }
                    is Resource.Error -> _eventFlow.emit(PEvent.MakeToast(response.message))
                }
            }
        }
    }
    private fun getSubscribes(){
        viewModelScope.launch {
            getSubscribesUseCase().collect { response ->
                when(response){
                    is Resource.Loading -> _eventFlow.emit(PEvent.Loading)
                    is Resource.Success -> _customSubscribes.value = response.data ?: emptyList()
                    is Resource.Error -> _eventFlow.emit(PEvent.MakeToast(response.message))
                }
                _isLoading.value = response is Resource.Loading
            }
        }
    }
}