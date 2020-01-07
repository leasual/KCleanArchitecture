package com.geekdroid.kcleanarchitecture.core.base

import com.geekdroid.kcleanarchitecture.core.model.Failure
import com.geekdroid.kcleanarchitecture.core.util.Either


/**
 * Create by james.li on 2019/12/19
 * Description:
 */

interface BaseUseCase<out Type, in Params> where Type : Any {

    suspend operator fun invoke(param: Params): Either<Failure, Type>


}

class None
