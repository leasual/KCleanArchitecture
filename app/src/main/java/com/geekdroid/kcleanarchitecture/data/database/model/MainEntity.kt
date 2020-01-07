package com.geekdroid.kcleanarchitecture.data.database.model

import com.geekdroid.kcleanarchitecture.data.mapper.DomainMapper
import com.geekdroid.kcleanarchitecture.domain.main.ArticleInfo
import com.geekdroid.kcleanarchitecture.domain.main.MainInfo

/**
 * Create by james.li on 2020/1/3
 * Description:
 */


data class MainEntity(val articleList: List<ArticleInfo>) : DomainMapper<MainInfo> {

    override fun mapToDomainModel(): MainInfo =
        MainInfo(articleList)
}