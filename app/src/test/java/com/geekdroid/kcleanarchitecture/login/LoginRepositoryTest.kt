package com.geekdroid.kcleanarchitecture.login

import com.geekdroid.kcleanarchitecture.data.login.LoginRepositoryImpl
import com.geekdroid.kcleanarchitecture.domain.login.LoginRequest
import com.geekdroid.kcleanarchitecture.util.*
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Test
import retrofit2.Response

/**
 * Create by james.li on 2020/1/9
 * Description:
 */

class LoginRepositoryTest : BaseKoinTest() {

    private val loginRepository: LoginRepositoryImpl by lazy { LoginRepositoryImpl(coroutinesContext, connectivity, apiService) }

    @Test
    fun `test login calls api and saves data to db success`(){
        runBlocking {
            whenever(connectivity.hasNetworkAccess()).thenReturn(true)
            whenever(apiService.login(LoginRequest(phone, password))).thenReturn(
                Response.success(successLoginResponse))
            loginRepository.login(LoginRequest(phone, password))
            verify(apiService, times(1)).login(LoginRequest(phone, password))
        }
    }

    @Test
    fun `test login calls api and return cached data from db failure`() {
        runBlocking {
            whenever(connectivity.hasNetworkAccess()).thenReturn(true)
            whenever(apiService.login(LoginRequest(phone, password))).thenReturn(
                Response.error(400, failureResponseBody))
            loginRepository.login(LoginRequest(phone, password))
            verify(apiService, times(1)).login(LoginRequest(phone, password))
        }
    }

}