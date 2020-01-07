package com.geekdroid.kcleanarchitecture.domain.main

/**
 * Create by james.li on 2020/1/3
 * Description:
 */

data class ArticleInfo(var desc: String)

data class MainInfo(var articleInfoList: List<ArticleInfo>?)