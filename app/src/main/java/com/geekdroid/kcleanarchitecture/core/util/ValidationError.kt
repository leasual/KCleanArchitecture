package com.geekdroid.kcleanarchitecture.core.util

/**
 * Create by james.li on 2020/1/7
 * Description: 数据验证
 */

sealed class ValidationError {
    //手机号验证
    object InvalidPhoneNumber: ValidationError()
    //密码验证
    object InvalidPassword: ValidationError()
}

/**
 * 手机号验证
 */
fun phoneValid(account: String?): Either<ValidationError, Boolean> =
    if (account?.isNotEmpty() == true && account.length >= 6) Either.Right(true)
    else Either.Left(ValidationError.InvalidPhoneNumber)

/**
 * 密码验证
 */
fun passwordValid(password: String?): Either<ValidationError, Boolean> =
    if (password?.isNotEmpty() == true && password.length >= 6 && password.length <= 18) Either.Right(true)
    else Either.Left(ValidationError.InvalidPassword)

/**
 * 手机号和密码验证
 */
fun phonePasswordValid(account: String, password: String): Either<ValidationError, Boolean> {
    if (phoneValid(account).isLeft) {
        return phoneValid(account)
    }
    if (passwordValid(password).isLeft) {
        return passwordValid(password)
    }
    return Either.Right(true)
}