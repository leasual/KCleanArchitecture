package com.geekdroid.kcleanarchitecture.core.util

import android.content.Context
import android.net.ConnectivityManager

/**
 * Create by james.li on 2019/12/19
 * Description: ConnectivityImpl
 */

class ConnectivityImpl(private val context: Context) : Connectivity {

    override fun hasNetworkAccess(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = connectivityManager.activeNetworkInfo
        return info != null && info.isConnected
    }
}