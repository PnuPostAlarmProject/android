package com.jeongg.ppap.domain.usecase.user

import com.jeongg.ppap.data.util.ACCESS_TOKEN_KEY
import com.jeongg.ppap.data.util.ApiUtils
import com.jeongg.ppap.data.util.PDataStore
import com.jeongg.ppap.data.util.REFRESH_TOKEN_KEY
import com.jeongg.ppap.data.util.getErrorMessage
import com.jeongg.ppap.domain.repository.UserRepository
import com.jeongg.ppap.util.Resource
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class Withdrawal @Inject constructor(
    private val dataStore: PDataStore,
    private val userRepository: UserRepository
){
    operator fun invoke(): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())
            val response = userRepository.withdraw()
            val body = response.body<ApiUtils.ApiResult<String>>()
            val successMessage = body.response ?: "성공적으로 회원탈퇴했습니다."
            val errorMessage = body.error?.message ?: "회원탈퇴에 실패하였습니다."

            if (response.status == HttpStatusCode.OK && body.success) {
                dataStore.setData(ACCESS_TOKEN_KEY, "")
                dataStore.setData(REFRESH_TOKEN_KEY, "")
                emit(Resource.Success(successMessage))
            }
            else {
                emit(Resource.Error(errorMessage))
            }
        } catch(error: Exception){
            emit(Resource.Error(getErrorMessage(error)))
        }
    }
}