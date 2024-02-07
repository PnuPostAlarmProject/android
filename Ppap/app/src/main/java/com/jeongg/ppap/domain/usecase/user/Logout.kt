package com.jeongg.ppap.domain.usecase.user

import com.jeongg.ppap.data._const.DataStoreKey
import com.jeongg.ppap.data.util.ApiUtils
import com.jeongg.ppap.data.util.PDataStore
import com.jeongg.ppap.data.util.getErrorMessage
import com.jeongg.ppap.domain.repository.UserRepository
import com.jeongg.ppap.util.Resource
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class Logout @Inject constructor(
    private val dataStore: PDataStore,
    private val userRepository: UserRepository
){
    operator fun invoke(): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())
            val response = userRepository.logout()
            val body = response.body<ApiUtils.ApiResult<String>>()
            val successMessage = body.response ?: "성공적으로 로그아웃했습니다."
            val errorMessage = body.error?.message ?: "로그아웃에 실패하였습니다."

            if (response.status == HttpStatusCode.OK && body.success) {
                dataStore.setData(DataStoreKey.ACCESS_TOKEN_KEY.name, "")
                dataStore.setData(DataStoreKey.REFRESH_TOKEN_KEY.name, "")
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