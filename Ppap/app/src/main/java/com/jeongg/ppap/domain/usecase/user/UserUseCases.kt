package com.jeongg.ppap.domain.usecase.user

import javax.inject.Inject

data class UserUseCases @Inject constructor(
    val kakaoLogin: KakaoLoginUsecase,
    val reissue: KakaoReissueUsecase,
    val logout: LogoutUsecase
)