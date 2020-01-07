package com.geekdroid.kcleanarchitecture.core.base

import com.geekdroid.kcleanarchitecture.core.util.Either
import com.geekdroid.kcleanarchitecture.core.util.ValidationError

/**
 * Create by james.li on 2019/12/19
 * Description: ViewState Ui层界面变化状态
 */

sealed class ViewState<out T : Any>

class LoadingState<out T : Any>(val isLoading: Boolean) : ViewState<T>()
class SuccessState<out T : Any>(val uiModel: T) : ViewState<T>()
class ValidationState<out T: Any>(val validator: Either<ValidationError, Boolean>) : ViewState<T>()
class ServerErrorState<out T : Any>(val error: Throwable, val code: Int) : ViewState<T>()
class NoNetworkState<T : Any> : ViewState<T>()
class NetworkErrorState<T : Any> : ViewState<T>()
class UnknownErrorState<T : Any> : ViewState<T>()
class DataErrorState<T : Any> : ViewState<T>()
class NavigationToState<T : Any> : ViewState<T>()