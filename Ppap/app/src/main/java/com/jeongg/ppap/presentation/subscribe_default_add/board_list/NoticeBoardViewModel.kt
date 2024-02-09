package com.jeongg.ppap.presentation.subscribe_default_add.board_list

import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeongg.ppap.data.dto.subscribe.SubscribeCreateRequestDTO
import com.jeongg.ppap.data.dto.univ.UnivNoticeBoardDTO
import com.jeongg.ppap.domain.usecase.subscribe.CreateSubscribe
import com.jeongg.ppap.domain.usecase.subscribe.GetUnivSubscribeList
import com.jeongg.ppap.presentation.util.PEvent
import com.jeongg.ppap.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoticeBoardViewModel @Inject constructor(
    private val createSubscribeUseCase: CreateSubscribe,
    private val getUnivSubscribeListUseCase: GetUnivSubscribeList,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    val title = mutableStateOf("")
    private val univId = mutableLongStateOf(-1L)
    private val selected = mutableStateListOf<Int>()

    private val _noticeBoard = mutableStateOf<List<UnivNoticeBoardDTO>>(emptyList())
    val noticeBoard = _noticeBoard

    private val _errorMessage = mutableStateOf("")
    val errorMessage = _errorMessage

    private val _eventFlow = MutableSharedFlow<PEvent>()
    val eventFlow = _eventFlow

    init {
        savedStateHandle.get<Long>("univId")?.let{
            univId.longValue = it
        }
        savedStateHandle.get<String>("title")?.let{
            title.value = it
        }
        getUnivSubscribeList()
    }

    fun addSubscribe(){
        viewModelScope.launch {
            if (selected.isEmpty()){
                _eventFlow.emit(PEvent.MakeToast("게시판이 추가되지 않았습니다."))
                return@launch
            }

            selected.forEach { num ->
                val title = _noticeBoard.value[num].title
                val link  = _noticeBoard.value[num].link
                createSubscribeUseCase(
                    SubscribeCreateRequestDTO(title, link, null)
                ).collect { response ->
                    if (response is Resource.Error) {
                        _eventFlow.emit(PEvent.MakeToast("$title\n${response.message}"))
                    }
                }
            }
            _eventFlow.emit(PEvent.Navigate)
        }
    }

    fun isSelected(index: Int): Boolean {
        return index in selected
    }

    fun noticeClick(index: Int) {
        if (isSelected(index)) selected.remove(index)
        else selected.add(index)
    }

    private fun getUnivSubscribeList(){
        viewModelScope.launch{
            getUnivSubscribeListUseCase(univId.longValue).collect { response ->
                val isError = (response is Resource.Error)
                val isSuccess = (response is Resource.Success)

                errorMessage.value = if (isError) response.message else ""
                _noticeBoard.value = if (isSuccess) response.data ?: emptyList() else emptyList()
            }
        }
    }

}