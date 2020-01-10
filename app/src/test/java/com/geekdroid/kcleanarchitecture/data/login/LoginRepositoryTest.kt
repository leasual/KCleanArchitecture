package com.geekdroid.kcleanarchitecture.data.login

import com.geekdroid.kcleanarchitecture.core.model.Failure
import com.geekdroid.kcleanarchitecture.core.util.Either
import com.geekdroid.kcleanarchitecture.data.database.model.UserEntity
import com.geekdroid.kcleanarchitecture.data.login.LoginRepositoryImpl
import com.geekdroid.kcleanarchitecture.data.mapper.getData
import com.geekdroid.kcleanarchitecture.domain.login.LoginRequest
import com.geekdroid.kcleanarchitecture.domain.login.UserInfo
import com.geekdroid.kcleanarchitecture.util.*
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.Response

/**
 * Create by james.li on 2020/1/9
 * Description:
 */

class LoginRepositoryTest : BaseKoinTest() {

    private val loginRepository: LoginRepositoryImpl by lazy { LoginRepositoryImpl(coroutinesContext, connectivity, apiService) }
    private val userEntity: UserEntity = mock()

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

    @Test
    fun `test login calls api server error failure`() {
        runBlocking {
            val request = LoginRequest(phone, password)
            val response = Failure.ServerError(Throwable(),501)
            whenever(connectivity.hasNetworkAccess()).thenReturn(true)
            whenever(apiService.login(request)).thenReturn(
                Response.error(400, failureResponseBody))
            val result = loginRepository.fetchData(
                apiDataProvider = {
                    Either.Left(response)
                },
                dbDataProvider = {
                    userEntity
                })
            assertEquals(Either.Left(response), result)
        }
    }

    @Test
    fun `test login calls api no network then return cache data from db success`() {
        runBlocking {
            val userInfo = UserInfo(phone, password, nickname)
            whenever(connectivity.hasNetworkAccess()).thenReturn(false)
            whenever(userEntity.mapToDomainModel()).thenReturn(userInfo)
            val result = loginRepository.fetchData(
                apiDataProvider = {
                    Either.Left(Failure.NoNetworkError())
                },
                dbDataProvider = {
                    userEntity
                })
            assertEquals(Either.Right(userInfo), result)
        }
    }

    @Test
    fun `test login calls api no network then return cache data from db failure`() {
        runBlocking {
            val userInfo = UserInfo(phone, password, nickname)
            whenever(connectivity.hasNetworkAccess()).thenReturn(false)
            whenever(userEntity.mapToDomainModel()).thenReturn(userInfo)
            val result = loginRepository.fetchData(
                apiDataProvider = {
                    Either.Left(Failure.NoNetworkError())
                },
                dbDataProvider = {
                    null
                })
            assertEquals(Either.Left(Failure.DatabaseError()), result)
        }
    }

}