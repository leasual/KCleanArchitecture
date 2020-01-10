package com.geekdroid.kcleanarchitecture.core.util

import com.geekdroid.kcleanarchitecture.util.BaseKoinTest
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

/**
 * Create by james.li on 2020/1/10
 * Description:
 */

class ValidationErrorTest : BaseKoinTest() {

    private val phone = "1862345678"
    private val password = "123456"

    @Test
    fun `validation phone is valid`() {
        runBlocking {
            assertEquals(Either.Right(true), phoneValid(phone))
        }
    }

    @Test
    fun `validation phone is invalid case less than`() {
        runBlocking {
            assertEquals(Either.Left(ValidationError.InvalidPhoneNumber), phoneValid("123"))
        }
    }

    @Test
    fun `validation phone is invalid case null`() {
        runBlocking {
            assertEquals(Either.Left(ValidationError.InvalidPhoneNumber), phoneValid(null))
        }
    }

    @Test
    fun `validation password is valid`() {
        runBlocking {
            assertEquals(Either.Right(true), passwordValid(password))
        }
    }

    @Test
    fun `validation password is invalid`() {
        runBlocking {
            assertEquals(Either.Left(ValidationError.InvalidPassword), passwordValid("123"))
        }
    }

    @Test
    fun `validation password is invalid case less than`() {
        runBlocking {
            assertEquals(Either.Left(ValidationError.InvalidPassword), passwordValid("123"))
        }
    }

    @Test
    fun `validation password is invalid case more than`() {
        runBlocking {
            assertEquals(Either.Left(ValidationError.InvalidPassword), passwordValid("1234567890123456789"))
        }
    }

    @Test
    fun `validation password is invalid case null`() {
        runBlocking {
            assertEquals(Either.Left(ValidationError.InvalidPassword), passwordValid(null))
        }
    }

    @Test
    fun `validation phone password is valid`() {
        runBlocking {
            assertEquals(Either.Right(true), phonePasswordValid(phone, password))
        }
    }

    @Test
    fun `validation phone password is phone invalid`() {
        runBlocking {
            assertEquals(Either.Left(ValidationError.InvalidPhoneNumber), phonePasswordValid("12", password))
        }
    }

    @Test
    fun `validation phone password is password invalid`() {
        runBlocking {
            assertEquals(Either.Left(ValidationError.InvalidPassword), phonePasswordValid(phone, "123"))
        }
    }
}