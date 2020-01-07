package com.geekdroid.kcleanarchitecture.data.mapper

import com.geekdroid.kcleanarchitecture.core.model.Failure
import com.geekdroid.kcleanarchitecture.core.util.Either
import com.geekdroid.kcleanarchitecture.data.api.BaseResponse
import retrofit2.Response
import java.io.IOException

/**
 * Create by james.li on 2019/12/19
 * Description:
 */

/**
 * 数据转换为domain层数据
 */
interface DomainMapper<T : Any> {
    fun mapToDomainModel(): T
}

/**
 * 数据转换为data层数据
 */
interface DatabaseMapper<out T : Any> {
    fun mapToDatabaseEntity(): T
}

inline fun <T : Any> Response<T>.onSuccess(action: (T) -> Unit): Response<T> {
    if (isSuccessful) body()?.run(action)
    return this
}

inline fun <T : Any> Response<T>.onFailure(action: (Failure) -> Unit) {
    if (!isSuccessful) errorBody()?.run { action(Failure.ServerError(Throwable(), code())) }
}

/**
 * 从网络获取数据，带缓存，先缓存，再从缓存读取
 * @param cacheAction 缓存数据
 * @param fetchFromCacheAction 从缓存中读取数据
 */

inline fun <T : DatabaseMapper<R>, R : DomainMapper<U>, U : Any> Response<BaseResponse<T>>.getData(
    cacheAction: (R) -> Unit,
    fetchFromCacheAction: () -> R?
): Either<Failure, U> {
    try {
        onSuccess {
            return when (it.code) {
                false -> {
                    val databaseEntity = it.data.mapToDatabaseEntity()
                    cacheAction(databaseEntity)
                    Either.Right(databaseEntity.mapToDomainModel())
                }
                else -> {
                    Either.Left(Failure.ServerError(Throwable(), code()))
                }
            }
        }
        onFailure {
            val cachedModel = fetchFromCacheAction()
            return if (cachedModel != null) Either.Right(cachedModel.mapToDomainModel()) else Either.Left(it)
        }
        return Either.Left(Failure.UnKnownError())
    } catch (e: IOException) {
        return Either.Left(Failure.UnKnownError())
    }
}

/**
 * 从网络获取数据，不带缓存
 */
fun <T : DatabaseMapper<R>, R : DomainMapper<U>, U : Any> Response<BaseResponse<T>>.getData(): Either<Failure, U> {
    try {
        onSuccess {
            return when (it.code) {
                false -> {
                    val databaseEntity = it.data.mapToDatabaseEntity()
                    Either.Right(databaseEntity.mapToDomainModel())
                }
                else -> {
                    Either.Left(Failure.ServerError(Throwable(), code()))
                }
            }
        }
        onFailure { return Either.Left(it) }
        return Either.Left(Failure.UnKnownError())
    } catch (e: IOException) {
        return Either.Left(Failure.UnKnownError())
    }
}

/**
 * 直接由服务器数据转换为Domain层数据，没有数据库缓存
 */
fun <T : DomainMapper<R>, R : Any> Response<BaseResponse<T>>.getDataNoDatabase(): Either<Failure, R> {
    try {
        onSuccess {
            return when (it.code) {
                false -> {
                    Either.Right(it.data.mapToDomainModel())
                }
                else -> {
                    Either.Left(Failure.ServerError(Throwable(), code()))
                }
            }
        }
        onFailure { return Either.Left(it) }
        return Either.Left(Failure.UnKnownError())
    } catch (e: IOException) {
        return Either.Left(Failure.UnKnownError())
    }
}