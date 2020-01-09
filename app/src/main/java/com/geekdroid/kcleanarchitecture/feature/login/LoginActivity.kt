package com.geekdroid.kcleanarchitecture.feature.login

import android.content.Intent
import android.widget.Toast
import com.geekdroid.kcleanarchitecture.R
import com.geekdroid.kcleanarchitecture.core.base.BaseActivity
import com.geekdroid.kcleanarchitecture.core.base.SuccessState
import com.geekdroid.kcleanarchitecture.core.extension.subscribe
import com.geekdroid.kcleanarchitecture.core.util.ValidationError
import com.geekdroid.kcleanarchitecture.feature.main.MainActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity<LoginViewModel, LoginViewModel.LoginUiModel>() {

//    private val viewModel: LoginViewModel by viewModel()

    override fun getLayoutId(): Int = R.layout.activity_login

    override fun setupViews() {
        setupLoadingProgressBar(pbLoading)
        btnLogin.setOnClickListener { viewModel.login(etAccount.text.toString(), etPassword.text.toString()) }
    }

    override fun observeData() {
        viewModel.viewState.subscribe(this) { handleViewState(it, clContainer) }
    }

    /**
     * 处理各种验证错误
     */
    override fun handleValidError(error: ValidationError) {
        //Log.d("test", "ValidationError= $error")
        when(error) {
            is ValidationError.InvalidPhoneNumber -> { Toast.makeText(this, "用户名不正确", Toast.LENGTH_SHORT).show() }
            is ValidationError.InvalidPassword -> { Toast.makeText(this, "密码不正确", Toast.LENGTH_SHORT).show() }
        }
    }

    /**
     * 处理成功数据
     */
    override fun handleSuccess(viewState: SuccessState<LoginViewModel.LoginUiModel>) {
        viewState.apply {
            //Log.d("test", "uiModel= ${uiModel.data}")
            uiModel.data?.let { goToMain() }
        }
    }

    private fun goToMain() {
        startActivity(Intent(this, MainActivity::class.java))
    }

}
