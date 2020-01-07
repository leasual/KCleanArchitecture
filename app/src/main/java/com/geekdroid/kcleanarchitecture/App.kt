package com.geekdroid.kcleanarchitecture

import android.app.Application
import com.geekdroid.kcleanarchitecture.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

/**
 * Create by james.li on 2019/12/23
 * Description:
 */

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this

        //Koin
        startKoin {
            androidContext(this@App)
            if (BuildConfig.DEBUG) androidLogger(Level.DEBUG)
            modules(appModule)
        }

    }

    companion object {
        lateinit var instance: Application
            private set
    }
}