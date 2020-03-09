package com.geekdroid.kcleanarchitecture.data.login

import com.geekdroid.kcleanarchitecture.core.base.BaseRepository
import com.geekdroid.kcleanarchitecture.core.model.Failure
import com.geekdroid.kcleanarchitecture.core.util.Connectivity
import com.geekdroid.kcleanarchitecture.core.util.CoroutinesContextProvider
import com.geekdroid.kcleanarchitecture.core.util.Either
import com.geekdroid.kcleanarchitecture.data.api.KCleanService
import com.geekdroid.kcleanarchitecture.data.database.model.UserEntity
import com.geekdroid.kcleanarchitecture.data.mapper.getData
import com.geekdroid.kcleanarchitecture.domain.login.LoginRepository
import com.geekdroid.kcleanarchitecture.domain.login.LoginRequest
import com.geekdroid.kcleanarchitecture.domain.login.UserInfo

/**
 * Create by james.li on 2019/12/19
 * Description: 从数据库读取数据转换为UI层数据
 */

class LoginRepositoryImpl(private val coroutinesContext: CoroutinesContextProvider, private val connectivity: Connectivity,
                          private val apiService: KCleanService) :
    BaseRepository(coroutinesContext, connectivity), LoginRepository {


    override suspend fun login(request: LoginRequest): Either<Failure, UserInfo> {
       /* return fetchData {
            apiService.login(request).getData()
        }*/
        return fetchData(
            apiDataProvider = {
                apiService.login(request).getData(
                    cacheAction = {
                        //写入数据库
                    },
                    fetchFromCacheAction = {
                        //网络请求失败，从数据库读取
                        UserEntity("user001", "123456", "网络请求失败，从数据库读取的数据测试")
                    }
                )
            },
            dbDataProvider = {
                //没有网络，直接数据库读取
                UserEntity("user001", "123456", "NoNetworkFromDB")
            }
        )
    }


}