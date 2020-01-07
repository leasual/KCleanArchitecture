package com.geekdroid.kcleanarchitecture.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

/**
 * Create by james.li on 2019/12/19
 * Description: BaseFragment
 */

abstract class BaseFragment : Fragment() {

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
        observeData()
    }

    open fun showError(@StringRes errorMessage: Int, rootView: View) {
        (activity as BaseActivity<*>).showError(errorMessage, rootView)
    }

    open fun showError(errorMessage: String?, rootView: View) {
        (activity as BaseActivity<*>).showError(errorMessage, rootView)
    }

    open fun showLoading(progressBar: ProgressBar) {
        (activity as BaseActivity<*>).showLoading(progressBar)
    }

    open fun hideLoading(progressBar: ProgressBar) {
        (activity as BaseActivity<*>).hideLoading(progressBar)
    }

    protected fun onBackPressed() = (activity as BaseActivity<*>).onBackPressed()

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun setupViews()

    abstract fun observeData()

}