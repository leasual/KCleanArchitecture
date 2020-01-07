package com.geekdroid.kcleanarchitecture.core.base

import android.util.Log
import com.geekdroid.kcleanarchitecture.core.model.Failure
import com.geekdroid.kcleanarchitecture.core.util.Connectivity
import com.geekdroid.kcleanarchitecture.core.util.CoroutinesContextProvider
import com.geekdroid.kcleanarchitecture.core.util.Either
import com.geekdroid.kcleanarchitecture.data.mapper.DomainMapper
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 * Create by james.li on 2019/12/19
 * Description:
 */

/**
 * @param U Domain层的数据(UI需要的数据)
 * @param D Database层的数据
 */
abstract class BaseRepository<U : Any, in D : DomainMapper<U>> : KoinComponent {
    private val connectivity: Connectivity by inject()
    private val contextProvider: CoroutinesContextProvider by inject()


    /**
     * 从服务器中获取数据，并转换，缓存到数据库
     * @param apiDataProvider 接口操作抽象
     * @param dbDataProvider 数据库操作抽象
     */
    protected suspend fun fetchData(
        apiDataProvider: suspend () -> Either<Failure, U>,
        dbDataProvider: suspend () -> D?
    ): Either<Failure, U> {
        return if (connectivity.hasNetworkAccess()) {
            withContext(contextProvider.io) {
                apiDataProvider()
            }
        } else {
            withContext(contextProvider.io) {
                val dataResult = dbDataProvider()
                when {
                    dataResult != null -> {
                        Log.d("test", "fetchFromDBSuccess")
                        Either.Right(dataResult.mapToDomainModel())
                    }
                    else -> {
                        Log.d("test", "fetchFromDBError")
                        Either.Left(Failure.DatabaseError())
                    }
                }
            }
        }
    }

    /**
     * 直接从服务器中获取数据，并转换，不带缓存机制
     * @param apiDataProvider 接口操作抽象
     */
    protected suspend fun fetchData(apiDataProvider: suspend () -> Either<Failure, U>): Either<Failure, U> {
        return when {
            connectivity.hasNetworkAccess() -> withContext(contextProvider.io) {
                apiDataProvider()
            }
            else -> Either.Left(Failure.NoNetworkError())
        }
    }
}