package com.geekdroid.kcleanarchitecture.domain.main

import com.geekdroid.kcleanarchitecture.core.base.None
import com.geekdroid.kcleanarchitecture.core.model.Failure
import com.geekdroid.kcleanarchitecture.core.util.Either

/**
 * Create by james.li on 2019/12/19
 * Description:
 */

class MainUseCaseImpl(private val mainRepository: MainRepository) :
    MainUseCase {

    override suspend fun invoke(param: None): Either<Failure, MainInfo> = mainRepository.getToday()

}