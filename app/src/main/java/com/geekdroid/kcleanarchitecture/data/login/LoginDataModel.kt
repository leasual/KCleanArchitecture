package com.geekdroid.kcleanarchitecture.data.login

import com.geekdroid.kcleanarchitecture.data.database.model.UserEntity
import com.geekdroid.kcleanarchitecture.data.mapper.DatabaseMapper

/**
 * Create by james.li on 2019/12/19
 * Description: 网络数据转换为数据库数据储存
 */

data class LoginResponse(val userName: String, val password: String, val nickName: String) :
    DatabaseMapper<UserEntity> {

    override fun mapToDatabaseEntity(): UserEntity = UserEntity(userName, password, nickName)

}