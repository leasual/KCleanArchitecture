package com.geekdroid.kcleanarchitecture.feature.main

import android.widget.Toast
import com.geekdroid.kcleanarchitecture.R
import com.geekdroid.kcleanarchitecture.core.base.*
import com.geekdroid.kcleanarchitecture.core.extension.subscribe
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Create by james.li on 2019/12/19
 * Description:
 */

class MainActivity : BaseActivity<MainViewModel.MainUiModel>() {

    private val viewModel: MainViewModel by viewModel()

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun setupViews() {
        viewModel.getToday()
    }

    override fun observeData() {
        viewModel.viewState.subscribe(this) { handleViewState(it, pbLoading, clContainer) }
    }

    override fun handleSuccess(viewState: SuccessState<MainViewModel.MainUiModel>) {
        viewState.apply {
            uiModel.data?.let { Toast.makeText(this@MainActivity, if(it.articleInfoList.isNullOrEmpty()) "no uiModel" else it.articleInfoList!![0].desc,Toast.LENGTH_SHORT).show() }
        }
    }

}
