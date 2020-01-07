package com.geekdroid.kcleanarchitecture.feature.login

import android.util.Log
import com.geekdroid.kcleanarchitecture.core.base.BaseViewModel
import com.geekdroid.kcleanarchitecture.core.util.phonePasswordValid
import com.geekdroid.kcleanarchitecture.domain.login.LoginRequest
import com.geekdroid.kcleanarchitecture.domain.login.LoginUseCase
import com.geekdroid.kcleanarchitecture.domain.login.UserInfo

/**
 * Create by james.li on 2019/12/23
 * Description: 用户登录
 */

class LoginViewModel(private val loginUseCase: LoginUseCase) :
    BaseViewModel<LoginViewModel.LoginUiModel>() {

    fun login(account: String, password: String) {

        validAction(phonePasswordValid(account, password)) {
            executeUseCase(isShowLoading = true) {
                loginUseCase(LoginRequest(account, password)).fold(::handleError) {
                    handleSuccess(LoginUiModel(it)) {
                        //处理成功后的业务逻辑
                        Log.d("test", "login success")
                    }
                }
            }
        }
    }


    data class LoginUiModel(
        val data: UserInfo? = null
    )
}