package com.geekdroid.kcleanarchitecture.core.extension

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geekdroid.kcleanarchitecture.core.util.CoroutinesContextProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 * Create by james.li on 2019/12/19
 * Description:
 */

inline fun ViewModel.launch(
    coroutinesContext: CoroutineContext = CoroutinesContextProvider().main,
    crossinline block: suspend CoroutineScope.() -> Unit
): Job {
    return viewModelScope.launch(coroutinesContext) { block() }
}