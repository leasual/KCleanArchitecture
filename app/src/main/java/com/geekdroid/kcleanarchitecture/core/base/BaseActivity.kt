package com.geekdroid.kcleanarchitecture.core.base

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.geekdroid.kcleanarchitecture.core.extension.gone
import com.geekdroid.kcleanarchitecture.core.extension.snackbar
import com.geekdroid.kcleanarchitecture.core.extension.subscribe
import com.geekdroid.kcleanarchitecture.core.extension.visible
import com.geekdroid.kcleanarchitecture.core.util.ValidationError
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.reflect.ParameterizedType
import kotlin.reflect.KClass

/**
 * Create by james.li on 2019/12/19
 * Description: BaseActivity
 */

abstract class BaseActivity<VM: BaseViewModel<*>, T: Any> : AppCompatActivity() {

    private var progressBar: ProgressBar? = null

    protected val viewModel: VM by viewModel(viewModelClass())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        setupViews()
        viewModel.loadingState.subscribe(this){ handleLoadingState(it) }
        observeData()
    }

    /**
     * 处理对话框逻辑
     */
    private fun handleLoadingState(viewState: LoadingState<Any>) {
        showOrHideLoading(viewState.isLoading, progressBar)
    }

    /**
     * 处理通用业务逻辑，验证错误，没有网络，网络错误等
     */
    protected fun handleViewState(viewState: ViewState<T>, rootView: View) {
        //Log.d("test", "ViewState= $viewState")
        when (viewState) {
//            is LoadingState -> {  showOrHideLoading(viewState.isLoading, pbLoading) }
            is SuccessState -> { handleSuccess(viewState) }
            is ServerErrorState -> { showError(viewState.error.message + viewState.code, rootView) }
            is ValidationState -> { viewState.validator.foldLeft(::handleValidError) }
            is NoNetworkState -> { showError("没有网络", rootView) }
            is DataErrorState -> { showError("数据读取错误", rootView)}
        }
    }

    protected fun setupLoadingProgressBar(progressBar: ProgressBar) {
        this.progressBar = progressBar
    }

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun setupViews()

    abstract fun observeData()

    abstract fun handleValidError(error: ValidationError)

    abstract fun handleSuccess(viewState: SuccessState<T>)

    fun showError(@StringRes errorMessage: Int, rootView: View) = snackbar(errorMessage, rootView)

    fun showError(errorMessage: String?, rootView: View) = snackbar(errorMessage ?: "", rootView)

    fun showLoading(progressBar: ProgressBar?) = progressBar?.visible()

    fun hideLoading(progressBar: ProgressBar?) = progressBar?.gone()

    fun showOrHideLoading(state: Boolean, progressBar: ProgressBar?) =
        if (state) showLoading(progressBar) else hideLoading(progressBar)


    @Suppress("UNCHECKED_CAST")
    private fun viewModelClass(): KClass<VM> {
        // dirty hack to get generic type https://stackoverflow.com/a/1901275/719212
        return ((javaClass.genericSuperclass as ParameterizedType)
            .actualTypeArguments[0] as Class<VM>).kotlin
    }

}