package com.geekdroid.kcleanarchitecture.data.main

import com.geekdroid.kcleanarchitecture.core.base.BaseRepository
import com.geekdroid.kcleanarchitecture.core.model.Failure
import com.geekdroid.kcleanarchitecture.core.util.Either
import com.geekdroid.kcleanarchitecture.data.api.KCleanService
import com.geekdroid.kcleanarchitecture.data.database.model.MainEntity
import com.geekdroid.kcleanarchitecture.data.mapper.getData
import com.geekdroid.kcleanarchitecture.domain.main.MainInfo
import com.geekdroid.kcleanarchitecture.domain.main.MainRepository

/**
 * Create by james.li on 2019/12/19
 * Description: 从数据库读取数据转换为UI层数据
 */

class MainRepositoryImpl(private val apiService: KCleanService) :
    BaseRepository<MainInfo, MainEntity>(),
    MainRepository {


    override suspend fun getToday(): Either<Failure, MainInfo> {
        return fetchData {
            apiService.getToday().getData()
        }
       /* return fetchData(
            apiDataProvider = {
                apiService.getToday().getUiModel(
                    cacheAction = {
                        //写入数据库
                        Log.d("test", "uiModel= $it")
                    },
                    fetchFromCacheAction = {
                        //网络失败的情况，从数据库读取
                        //UserEntity("user001", "123456", "fetchNetworkErrorAfterFromDB")
                        val articlesList = arrayListOf<ArticleInfo>()
                        articlesList.add(ArticleInfo("这只是一个从数据读取数据的测试"))
                        MainEntity(articlesList)
                    }
                )
            },
            dbDataProvider = {
                //数据库读取
                null//UserEntity("user001", "123456", "NoNetworkFromDB")
            }
        )*/
    }


}