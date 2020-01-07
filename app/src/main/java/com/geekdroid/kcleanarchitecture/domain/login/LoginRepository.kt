package com.geekdroid.kcleanarchitecture.domain.login

import com.geekdroid.kcleanarchitecture.core.model.Failure
import com.geekdroid.kcleanarchitecture.core.util.Either

/**
 * Create by james.li on 2019/12/19
 * Description:
 */

interface LoginRepository {

    suspend fun login(request: LoginRequest): Either<Failure, UserInfo>
}