package com.geekdroid.kcleanarchitecture.core.base

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.geekdroid.kcleanarchitecture.core.extension.gone
import com.geekdroid.kcleanarchitecture.core.extension.snackbar
import com.geekdroid.kcleanarchitecture.core.extension.visible
import com.geekdroid.kcleanarchitecture.core.util.ValidationError

/**
 * Create by james.li on 2019/12/19
 * Description: BaseActivity
 */

abstract class BaseActivity<T: Any> : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        setupViews()
        observeData()
    }

    /**
     * 处理通用业务逻辑，像显示对话框，验证错误，没有网络，网络错误等
     */
    protected fun handleViewState(viewState: ViewState<T>, pbLoading: ProgressBar, rootView: View) {
        Log.d("test", "ViewState= $viewState")
        when (viewState) {
            is LoadingState -> {  showOrHideLoading(viewState.isLoading, pbLoading) }
            is SuccessState -> { handleSuccess(viewState) }
            is ServerErrorState -> { showError(viewState.error.message + viewState.code, rootView) }
            is ValidationState -> { viewState.validator.foldLeft(::handleValidError) }
            is NoNetworkState -> { showError("没有网络", rootView) }
            is DataErrorState -> { showError("数据读取错误", rootView)}
        }
    }

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun setupViews()

    abstract fun observeData()

    abstract fun handleValidError(error: ValidationError)

    abstract fun handleSuccess(viewState: SuccessState<T>)

    fun showError(@StringRes errorMessage: Int, rootView: View) = snackbar(errorMessage, rootView)

    fun showError(errorMessage: String?, rootView: View) = snackbar(errorMessage ?: "", rootView)

    fun showLoading(progressBar: ProgressBar) = progressBar.visible()

    fun hideLoading(progressBar: ProgressBar) = progressBar.gone()

    private fun showOrHideLoading(state: Boolean, progressBar: ProgressBar) =
        if (state) showLoading(progressBar) else hideLoading(progressBar)

}