package com.geekdroid.kcleanarchitecture.login

import com.geekdroid.kcleanarchitecture.core.base.NoNetworkState
import com.geekdroid.kcleanarchitecture.core.base.ServerErrorState
import com.geekdroid.kcleanarchitecture.core.base.SuccessState
import com.geekdroid.kcleanarchitecture.core.base.ValidationState
import com.geekdroid.kcleanarchitecture.core.model.Failure
import com.geekdroid.kcleanarchitecture.core.util.Either
import com.geekdroid.kcleanarchitecture.core.util.ValidationError
import com.geekdroid.kcleanarchitecture.domain.login.LoginRequest
import com.geekdroid.kcleanarchitecture.domain.login.LoginUseCase
import com.geekdroid.kcleanarchitecture.domain.login.UserInfo
import com.geekdroid.kcleanarchitecture.feature.login.LoginViewModel
import com.geekdroid.kcleanarchitecture.util.BaseKoinTest
import com.geekdroid.kcleanarchitecture.util.nickname
import com.geekdroid.kcleanarchitecture.util.password
import com.geekdroid.kcleanarchitecture.util.phone
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Create by james.li on 2020/1/8
 * Description: BaseTest
 */

class LoginViewModelTest: BaseKoinTest()  {
    private val loginUseCase: LoginUseCase = mock()

    private val loginViewModel by lazy { LoginViewModel(coroutinesContext, connectivity, loginUseCase) }

    @Test
    fun `login success`() = runBlocking {
        whenever(loginUseCase(LoginRequest(phone, password))).thenReturn(Either.Right(UserInfo(phone, password, nickname)))
        loginViewModel.login(phone, password)
        assertEquals(false, loginViewModel.loadingState.value?.isLoading)
        assertEquals(UserInfo(phone, password, "test"), (loginViewModel.viewState.value as SuccessState).uiModel.data)
    }

    @Test
    fun `login fail 404`() = runBlocking {
        whenever(loginUseCase(LoginRequest(phone, password))).thenReturn(Either.Left(Failure.ServerError(Throwable(), 404)))
        loginViewModel.login(phone, password)
        assertEquals(false, loginViewModel.loadingState.value?.isLoading)
        assertEquals(404, (loginViewModel.viewState.value as ServerErrorState).code)
    }

    @Test
    fun `login fail no network`() = runBlocking {
        whenever(loginUseCase(LoginRequest(phone, password))).thenReturn(Either.Left(Failure.NoNetworkError()))
        loginViewModel.login(phone, password)
        assertEquals(false, loginViewModel.loadingState.value?.isLoading)
        assertEquals(true, loginViewModel.viewState.value is NoNetworkState)
    }

    @Test
    fun `login fail phone is not correct`() = runBlocking {
        loginViewModel.login("123", password)
        verify(loginUseCase, never()).invoke(LoginRequest("", password))
        assertEquals(Either.Left(ValidationError.InvalidPhoneNumber), (loginViewModel.viewState.value as ValidationState).validator)
    }

    @Test
    fun `login fail phone is empty`() = runBlocking {
        loginViewModel.login("", password)
        verify(loginUseCase, never()).invoke(LoginRequest("", password))
        assertEquals(Either.Left(ValidationError.InvalidPhoneNumber), (loginViewModel.viewState.value as ValidationState).validator)
    }

    @Test
    fun `login fail password is not correct`() = runBlocking {
        loginViewModel.login(phone, "123")
        verify(loginUseCase, never()).invoke(LoginRequest(phone, "123"))
        assertEquals(Either.Left(ValidationError.InvalidPassword), (loginViewModel.viewState.value as ValidationState).validator)
    }

    @Test
    fun `login fail password is empty`() = runBlocking {
        loginViewModel.login(phone, "")
        verify(loginUseCase, never()).invoke(LoginRequest(phone, ""))
        assertEquals(Either.Left(ValidationError.InvalidPassword), (loginViewModel.viewState.value as ValidationState).validator)
    }
}