package com.geekdroid.kcleanarchitecture.domain.login

import com.geekdroid.kcleanarchitecture.core.base.BaseUseCase
import com.geekdroid.kcleanarchitecture.core.model.Failure
import com.geekdroid.kcleanarchitecture.core.util.Either

/**
 * Create by james.li on 2019/12/19
 * Description:
 */

interface LoginUseCase : BaseUseCase<UserInfo, LoginRequest> {

    override suspend fun invoke(param: LoginRequest): Either<Failure, UserInfo>
}