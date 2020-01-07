package com.geekdroid.kcleanarchitecture.domain.login

import com.geekdroid.kcleanarchitecture.core.model.Failure
import com.geekdroid.kcleanarchitecture.core.util.Either


/**
 * Create by james.li on 2019/12/19
 * Description:
 */

class LoginUseCaseImpl(private val loginRepository: LoginRepository) : LoginUseCase {

    override suspend fun invoke(param: LoginRequest): Either<Failure, UserInfo> =
        loginRepository.login(param)
}