package com.yomi.doggo

import android.app.Application
import com.yomi.doggo.di.appComponent
import com.yomi.doggo.util.InternetUtil
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Created by Yomi Joseph on 2020-07-19.
 */
class App: Application() {

    override fun onCreate() {
        super.onCreate()
        InternetUtil.init(this)
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@App)
            modules(provideDependency())
        }
    }

    open fun provideDependency() = appComponent
}