package com.jeongg.ppap.presentation.subscribe

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeongg.ppap.data.subscribe.SubscribeRepository
import com.jeongg.ppap.data.subscribe.dto.SubscribeGetResponseDTO
import com.jeongg.ppap.presentation.util.PEvent
import com.jeongg.ppap.util.log
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubscribeViewModel @Inject constructor(
    private val subscribeRepository: SubscribeRepository,
): ViewModel()  {

    private var _customSubscribes = mutableStateOf<List<SubscribeGetResponseDTO>>(emptyList())
    val customSubscribes = _customSubscribes

    private val _eventFlow = MutableSharedFlow<PEvent>()
    val eventFlow = _eventFlow

    fun updateActive(subscribeId: Long){
        viewModelScope.launch {
            _eventFlow.emit(PEvent.LOADING)
            val response = subscribeRepository.updateActive(subscribeId)
            if (response.success){
                "구독 활성화 성공".log()
                _eventFlow.emit(PEvent.SUCCESS)
            } else {
                "구독 활성화 실패 ${response.error?.status}".log()
                _eventFlow.emit(PEvent.ERROR(response.error?.message ?: "구독 활성화 실패하였습니다."))
            }
        }
    }
    fun deleteSubscribe(subscribeId: Long){
        viewModelScope.launch {
            _eventFlow.emit(PEvent.LOADING)
            val response = subscribeRepository.deleteSubscribe(subscribeId)
            if (response.success){
                _eventFlow.emit(PEvent.DELETE)
            } else {
                "구독 삭제 실패 ${response.error?.status}".log()
                _eventFlow.emit(PEvent.ERROR(response.error?.message ?: "구독 삭제 실패하였습니다."))
            }
        }
    }
    fun getSubscribes(){
        viewModelScope.launch {
            _eventFlow.emit(PEvent.LOADING)
            val response = subscribeRepository.getSubscribes()
            if (response.success){
                _customSubscribes.value = response.response ?: emptyList()
                _eventFlow.emit(PEvent.GET)
            } else {
                "구독 목록 불러오기 실패 ${response.error?.status}".log()
                _eventFlow.emit(PEvent.ERROR(response.error?.message ?: "구독 목록 조회를 실패했습니다."))
            }
        }
    }
}