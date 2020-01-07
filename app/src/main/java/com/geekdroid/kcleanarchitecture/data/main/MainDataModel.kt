package com.geekdroid.kcleanarchitecture.data.main

import com.geekdroid.kcleanarchitecture.data.database.model.MainEntity
import com.geekdroid.kcleanarchitecture.data.mapper.DatabaseMapper
import com.geekdroid.kcleanarchitecture.domain.main.ArticleInfo
import com.google.gson.annotations.SerializedName

/**
 * Create by james.li on 2020/1/3
 * Description: 服务器数据转为数据库数据
 */


data class MainResponse(
    @SerializedName("Android") var android: List<ArticleData>
) : DatabaseMapper<MainEntity> {

    override fun mapToDatabaseEntity(): MainEntity {
        val destList = arrayListOf<ArticleInfo>()
        android.mapTo(destList, { articleData ->
            ArticleInfo(
                articleData.desc
            )
        })
        return MainEntity(destList)
    }
}

data class ArticleData(
    @SerializedName("desc") var desc: String
)