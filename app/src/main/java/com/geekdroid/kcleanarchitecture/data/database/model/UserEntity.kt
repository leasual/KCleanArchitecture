package com.geekdroid.kcleanarchitecture.data.database.model

import com.geekdroid.kcleanarchitecture.data.mapper.DomainMapper
import com.geekdroid.kcleanarchitecture.domain.login.UserInfo

/**
 * Create by james.li on 2019/12/23
 * Description: 数据库数据转换为UI数据
 */

data class UserEntity(var name: String, var password: String, var nickName: String) :
    DomainMapper<UserInfo> {

    override fun mapToDomainModel(): UserInfo = UserInfo(name, password, nickName)

}