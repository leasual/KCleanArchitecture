package com.geekdroid.kcleanarchitecture.domain.login

import com.geekdroid.kcleanarchitecture.domain.login.LoginRepository
import com.geekdroid.kcleanarchitecture.domain.login.LoginRequest
import com.geekdroid.kcleanarchitecture.domain.login.LoginUseCaseImpl
import com.geekdroid.kcleanarchitecture.util.BaseKoinTest
import com.geekdroid.kcleanarchitecture.util.password
import com.geekdroid.kcleanarchitecture.util.phone
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Test

/**
 * Create by james.li on 2020/1/9
 * Description:
 */

class LoginUseCaseTest : BaseKoinTest() {

    private val loginRepository: LoginRepository = mock()
    private val login by lazy { LoginUseCaseImpl(loginRepository) }

    @Test
    fun `test login calls loginRepository`() {
        runBlocking {
            login(LoginRequest(phone, password))
            verify(loginRepository, times(1)).login(LoginRequest(phone, password))
        }
    }
}