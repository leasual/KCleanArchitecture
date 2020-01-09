package com.geekdroid.kcleanarchitecture.util

import com.geekdroid.kcleanarchitecture.data.api.BaseResponse
import com.geekdroid.kcleanarchitecture.data.login.LoginResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody

/**
 * Create by james.li on 2020/1/9
 * Description:
 */

val phone = "18612345678"
val password = "123456"
val nickname = "test"

val successLoginResponse = BaseResponse(false, LoginResponse(phone, password, nickname))
val failureResponseBody = "network error".toResponseBody("text".toMediaTypeOrNull())