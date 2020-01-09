package com.geekdroid.kcleanarchitecture.di

import com.geekdroid.kcleanarchitecture.BuildConfig
import com.geekdroid.kcleanarchitecture.core.util.Connectivity
import com.geekdroid.kcleanarchitecture.core.util.ConnectivityImpl
import com.geekdroid.kcleanarchitecture.core.util.CoroutinesContextProvider
import com.geekdroid.kcleanarchitecture.core.util.TestCoroutinesContextProvider
import com.geekdroid.kcleanarchitecture.data.api.KCleanService
import com.geekdroid.kcleanarchitecture.data.login.LoginRepositoryImpl
import com.geekdroid.kcleanarchitecture.data.main.MainRepositoryImpl
import com.geekdroid.kcleanarchitecture.domain.login.LoginRepository
import com.geekdroid.kcleanarchitecture.domain.login.LoginUseCase
import com.geekdroid.kcleanarchitecture.domain.login.LoginUseCaseImpl
import com.geekdroid.kcleanarchitecture.domain.main.MainRepository
import com.geekdroid.kcleanarchitecture.domain.main.MainUseCase
import com.geekdroid.kcleanarchitecture.domain.main.MainUseCaseImpl
import com.geekdroid.kcleanarchitecture.feature.login.LoginViewModel
import com.geekdroid.kcleanarchitecture.feature.main.MainViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Create by james.li on 2019/12/23
 * Description: 注入模块
 */


/**
 * 网络模块
 */
val networkingModule = module {
    single { GsonConverterFactory.create() as Converter.Factory }
    single { HttpLoggingInterceptor().apply { level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE } }
    single {
        OkHttpClient.Builder().apply {
            addInterceptor(get<HttpLoggingInterceptor>()) }.build()
    }
    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(get())
            .addConverterFactory(get())
            .build()
    }
    single { get<Retrofit>().create(KCleanService::class.java) }
}

/**
 * 通用模块
 */
val commonModule = module {
    single { CoroutinesContextProvider() }
    factory<Connectivity> { ConnectivityImpl(androidContext()) }
}


/**
 * 登录模块
 */
val loginModule = module {
    factory<LoginUseCase> { LoginUseCaseImpl(get()) }
    factory<LoginRepository> { LoginRepositoryImpl(get(), get(), get()) }
    viewModel { LoginViewModel(get(), get(), get()) }
}

/**
 * 主页模块
 */
val mainModule = module {
    factory<MainUseCase> { MainUseCaseImpl(get()) }
    factory<MainRepository> { MainRepositoryImpl(get(), get(), get()) }
    viewModel { MainViewModel(get(), get(), get()) }
}

/**
 * App模块
 */
val appModule = listOf(networkingModule, commonModule, loginModule, mainModule)