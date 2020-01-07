package com.geekdroid.kcleanarchitecture.domain.main

import com.geekdroid.kcleanarchitecture.core.model.Failure
import com.geekdroid.kcleanarchitecture.core.util.Either

/**
 * Create by james.li on 2019/12/19
 * Description:
 */

interface MainRepository {

    suspend fun getToday(): Either<Failure, MainInfo>
}