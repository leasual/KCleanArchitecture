package com.geekdroid.kcleanarchitecture.util

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.geekdroid.kcleanarchitecture.core.util.Connectivity
import com.geekdroid.kcleanarchitecture.core.util.TestCoroutinesContextProvider
import com.geekdroid.kcleanarchitecture.data.api.KCleanService
import com.geekdroid.kcleanarchitecture.di.appModule
import com.nhaarman.mockitokotlin2.mock
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.inject
import org.koin.test.KoinTest
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

/**
 * Create by james.li on 2020/1/9
 * Description:
 */
 
abstract class BaseKoinTest : KoinTest {
    protected val apiService: KCleanService = mock()
    protected var connectivity: Connectivity = mock()
    protected val coroutinesContext = TestCoroutinesContextProvider()

    //it is needed to test code with LiveData
    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()
    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    lateinit var context: Application

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)
        startKoin {
            androidContext(context)
            modules(appModule)
        }
    }

    @After
    fun after() {
        stopKoin()
    }
}