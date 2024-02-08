package com.jeongg.ppap.presentation.subscribe_custom_update

import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeongg.ppap.data.dto.SubscribeUpdateRequestDTO
import com.jeongg.ppap.domain.usecase.subscribe.UpdateSubscribe
import com.jeongg.ppap.presentation.util.PEvent
import com.jeongg.ppap.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubscribeCustomUpdateViewModel @Inject constructor(
    private val updateSubscribeUseCase: UpdateSubscribe,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private var subscribeId = mutableLongStateOf(-1L)

    private val _subscribeName = mutableStateOf("")
    val subscribeName = _subscribeName

    private val _eventFlow = MutableSharedFlow<PEvent>()
    val eventFlow = _eventFlow

    init {
        savedStateHandle.get<String>("name")?.let {
            _subscribeName.value = it
        }
        savedStateHandle.get<Long>("subscribeId")?.let{
            subscribeId.longValue = it
        }
    }

    fun enterTitle(text: String){
        _subscribeName.value = text
    }

    fun updateSubscribe(){
        viewModelScope.launch {
            if (isNameInvalid()){
                _eventFlow.emit(PEvent.MakeToast("제목은 20자 이하여야 하고 비어있으면 안됩니다."))
                return@launch
            }
            updateSubscribeUseCase(
                subscribeId = subscribeId.longValue,
                requestDTO = SubscribeUpdateRequestDTO(
                    title = _subscribeName.value,
                    noticeLink = null
                )
            ).collect { response ->
                when(response){
                    is Resource.Loading -> _eventFlow.emit(PEvent.Loading)
                    is Resource.Success -> _eventFlow.emit(PEvent.Navigate)
                    is Resource.Error -> _eventFlow.emit(PEvent.MakeToast(response.message))
                }
            }
        }
    }

    private fun isNameInvalid() = _subscribeName.value.isEmpty() || _subscribeName.value.length > 20
}