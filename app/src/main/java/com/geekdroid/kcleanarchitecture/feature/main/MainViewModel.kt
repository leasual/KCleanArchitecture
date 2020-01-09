package com.geekdroid.kcleanarchitecture.feature.main

import android.util.Log
import com.geekdroid.kcleanarchitecture.core.base.BaseViewModel
import com.geekdroid.kcleanarchitecture.core.base.None
import com.geekdroid.kcleanarchitecture.core.base.SuccessState
import com.geekdroid.kcleanarchitecture.core.util.Connectivity
import com.geekdroid.kcleanarchitecture.core.util.CoroutinesContextProvider
import com.geekdroid.kcleanarchitecture.domain.main.MainInfo
import com.geekdroid.kcleanarchitecture.domain.main.MainUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

/**
 * Create by james.li on 2020/1/3
 * Description:
 */

class MainViewModel(private val coroutinesContext: CoroutinesContextProvider, private val connectivity: Connectivity,
                    private val mainUseCase: MainUseCase) :
    BaseViewModel<MainViewModel.MainUiModel>(coroutinesContext, connectivity) {

    fun getToday() = executeUseCase(isShowLoading = true) {
        //Log.d("test", "before executeUseCase" + Thread.currentThread().name)
        mainUseCase(None()).fold(::handleError, ::handleSuccess)
        loadingDB()
        //Log.d("test", "after executeUseCase" + Thread.currentThread().name)
    }

    private fun handleSuccess(mainInfo: MainInfo) {
        _viewState.value = SuccessState(MainUiModel(mainInfo))
    }

    private suspend fun loadingDB() = withContext(coroutinesContext.io) {
        //延时不能使用Thread.sleep，无法取消
        delay(5000)
        //Log.d("test", "loadingDB " + Thread.currentThread().name)
    }

    data class MainUiModel(
        val data: MainInfo? = null
    )
}