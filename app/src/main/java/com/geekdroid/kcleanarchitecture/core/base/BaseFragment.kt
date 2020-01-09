package com.geekdroid.kcleanarchitecture.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.geekdroid.kcleanarchitecture.core.extension.subscribe
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.reflect.ParameterizedType
import kotlin.reflect.KClass

/**
 * Create by james.li on 2019/12/19
 * Description: BaseFragment
 */

abstract class BaseFragment<VM: BaseViewModel<*>> : Fragment() {

    private var progressBar: ProgressBar? = null

    protected val viewModel: VM by viewModel(viewModelClass())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        viewModel.loadingState.subscribe(this){ handleLoadingState(it) }
        observeData()
    }

    /**
     * 处理对话框逻辑
     */
    private fun handleLoadingState(viewState: LoadingState<Any>) {
        (activity as BaseActivity<*, *>).showOrHideLoading(viewState.isLoading, progressBar)
    }

    protected fun setupLoadingProgressBar(progressBar: ProgressBar) {
        this.progressBar = progressBar
    }

    open fun showError(@StringRes errorMessage: Int, rootView: View) {
        (activity as BaseActivity<*, *>).showError(errorMessage, rootView)
    }

    open fun showError(errorMessage: String?, rootView: View) {
        (activity as BaseActivity<*, *>).showError(errorMessage, rootView)
    }

    open fun showLoading(progressBar: ProgressBar) {
        (activity as BaseActivity<*, *>).showLoading(progressBar)
    }

    open fun hideLoading(progressBar: ProgressBar) {
        (activity as BaseActivity<*, *>).hideLoading(progressBar)
    }

    protected fun onBackPressed() = (activity as BaseActivity<*, *>).onBackPressed()

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun setupViews()

    abstract fun observeData()

    @Suppress("UNCHECKED_CAST")
    private fun viewModelClass(): KClass<VM> {
        // dirty hack to get generic type https://stackoverflow.com/a/1901275/719212
        return ((javaClass.genericSuperclass as ParameterizedType)
            .actualTypeArguments[0] as Class<VM>).kotlin
    }

}