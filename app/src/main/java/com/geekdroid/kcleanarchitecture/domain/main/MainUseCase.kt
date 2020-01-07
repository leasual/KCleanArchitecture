package com.geekdroid.kcleanarchitecture.domain.main

import com.geekdroid.kcleanarchitecture.core.base.BaseUseCase
import com.geekdroid.kcleanarchitecture.core.base.None
import com.geekdroid.kcleanarchitecture.core.model.Failure
import com.geekdroid.kcleanarchitecture.core.util.Either

/**
 * Create by james.li on 2019/12/19
 * Description:
 */

interface MainUseCase : BaseUseCase<MainInfo, None> {

    override suspend fun invoke(param: None): Either<Failure, MainInfo>
}