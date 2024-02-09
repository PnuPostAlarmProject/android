package com.jeongg.ppap.presentation.subscribe_default_add.univ_list

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeongg.ppap.data.dto.univ.UnivListDTO
import com.jeongg.ppap.domain.usecase.subscribe.GetUnivList
import com.jeongg.ppap.presentation.util.PEvent
import com.jeongg.ppap.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UnivViewModel @Inject constructor(
    private val getUnivListUseCase: GetUnivList
): ViewModel() {

    private val _selected = mutableStateOf(UnivListDTO())
    val selected = _selected

    private val _univList = mutableStateOf<List<UnivListDTO>>(emptyList())
    val univList = _univList

    private val _errorMessage = mutableStateOf("")
    val errorMessage = _errorMessage

    private val _eventFlow = MutableSharedFlow<PEvent>()
    val eventFlow = _eventFlow

    init {
        getUnivList()
    }

    fun selectSubscribe(index: Int){
        viewModelScope.launch {
            if (index >= _univList.value.size) {
                _eventFlow.emit(PEvent.MakeToast("index 범위를 초과하였습니다."))
            }
            else {
                _selected.value = _univList.value[index]
                _eventFlow.emit(PEvent.Navigate)
            }
        }
    }

    private fun getUnivList() {
        viewModelScope.launch{
            getUnivListUseCase().collect { response ->
                val isError = (response is Resource.Error)
                val isSuccess = (response is Resource.Success)

                errorMessage.value = if (isError) response.message else ""
                _univList.value = if (isSuccess) response.data ?: emptyList() else emptyList()
            }
        }
    }
}