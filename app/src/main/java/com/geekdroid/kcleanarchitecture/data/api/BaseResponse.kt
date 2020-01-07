package com.geekdroid.kcleanarchitecture.data.api

import com.google.gson.annotations.SerializedName

/**
 * Create by james.li on 2020/1/3
 * Description: 服务数据通用结构
 */

data class BaseResponse<T>(
    @SerializedName("error") var code: Boolean,
    @SerializedName("results") var data: T
)