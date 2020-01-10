package com.geekdroid.kcleanarchitecture.core.base

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geekdroid.kcleanarchitecture.core.model.Failure
import com.geekdroid.kcleanarchitecture.core.util.*
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 * Create by james.li on 2019/12/19
 * Description: BaseViewModel
 */

abstract class BaseViewModel<T : Any>(private val coroutinesContext: CoroutinesContextProvider, private val connectivity: Connectivity)
    : ViewModel(), KoinComponent {

//    protected val coroutinesContext: CoroutinesContextProvider by inject()
//    private val connectivity: Connectivity by inject()

    /**
     * 处理页面数据,无网络，出错等
     * 界面数据可以通过 uiModel class封装，例如
     * uiModel class LoginUiModel(
     *  val showLoading: Boolean,
     *  var isLogin: Boolean
     *  )
     */
    protected var _viewState: MutableLiveData<ViewState<T>> = MutableLiveData()
    val viewState: MutableLiveData<ViewState<T>>
        get() = _viewState

    protected var _loadingState: MutableLiveData<LoadingState<T>> = MutableLiveData()
    val loadingState: MutableLiveData<LoadingState<T>>
        get() = _loadingState

    /**
     * 针对于需要使用网络的业务逻辑
     * @param isShowLoading 是否显示loading框
     * @param action 具体的业务逻辑
     * @param noInternetAction 没有网络处理
     */
    protected fun executeUseCase(
        isShowLoading: Boolean = false,
        action: suspend () -> Unit,
        noInternetAction: () -> Unit
    ) {
        when {
            connectivity.hasNetworkAccess() -> viewModelScope.launch(coroutinesContext.main) {
                if (isShowLoading) _loadingState.value = LoadingState(true)
                action()
                if (isShowLoading) _loadingState.value = LoadingState(false)
            }
            else -> {
                if (isShowLoading) _loadingState.value = LoadingState(false)
                noInternetAction()
            }
        }
    }

    /**
     * 针对于不需要使用网络的业务逻辑
     * @param action 具体的业务逻辑
     */
    protected fun executeUseCase(isShowLoading: Boolean = false, action: suspend () -> Unit) {
        if (isShowLoading) _loadingState.value = LoadingState(true)
        viewModelScope.launch(coroutinesContext.main) {
            action()
            if (isShowLoading) _loadingState.value = LoadingState(false)
        }
    }

    /**
     * 处理错误的逻辑
     * @param failure 错误的类型
     */
    protected fun handleError(failure: Failure) {
        when (failure) {
            is Failure.ServerError -> {
                _viewState.value = ServerErrorState(failure.throwable, failure.errorCode)
            }
            is Failure.NoNetworkError -> {
                _viewState.value = NoNetworkState()
            }
            is Failure.NetworkError -> {
                _viewState.value = NetworkErrorState()
            }
            is Failure.DatabaseError -> {
                _viewState.value = DataErrorState()
            }
            is Failure.UnKnownError -> {
                _viewState.value = UnknownErrorState()
            }
        }
    }

    /**
     * 处理验证逻辑
     * @param validResult 验证结果
     * @param action 验证成功后，处理业务逻辑
     */
    protected fun validAction(validResult: Either<ValidationError, Boolean>, action: () -> Unit) {
        if (validResult.isLeft) {
            _viewState.value = ValidationState(validResult)
            return
        }
        action.invoke()
    }

    /**
     * 处理数据请求成功后的业务逻辑
     * @param data 网络请求数据
     * @param action 在发送成功信号前，处理业务逻辑
     */
    protected fun handleSuccess(data: T, action: () -> Unit) {
        action.invoke()
        _viewState.value = SuccessState(data)
    }

}