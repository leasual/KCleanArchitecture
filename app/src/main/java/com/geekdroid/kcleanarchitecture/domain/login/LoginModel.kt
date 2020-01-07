package com.geekdroid.kcleanarchitecture.domain.login

/**
 * Create by james.li on 2019/12/19
 * Description:
 */

data class UserInfo(val userName: String, val userPassword: String, val nickName: String)

data class LoginRequest(val userName: String, val userPassword: String)