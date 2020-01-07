package com.geekdroid.kcleanarchitecture.data.api

import com.geekdroid.kcleanarchitecture.data.login.LoginResponse
import com.geekdroid.kcleanarchitecture.data.main.MainResponse
import com.geekdroid.kcleanarchitecture.domain.login.LoginRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Create by james.li on 2019/12/19
 * Description:
 */

interface KCleanService {

    @POST("today")
    suspend fun login(@Body loginRequest: LoginRequest): Response<BaseResponse<LoginResponse>>

    @GET("today")
    suspend fun getToday(): Response<BaseResponse<MainResponse>>
}