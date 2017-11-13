package com.swiezaki.swiezakimobile.app

import android.app.Application
import com.swiezaki.swiezakimobile.BuildConfig
import com.swiezaki.swiezakimobile.network.NetworkModule

class App : Application() {

    companion object {
        lateinit var component: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        initComponent()
    }

    private fun initComponent(){
        component = DaggerAppComponent.builder()
                .appModule(AppModule(this, createAppConfig()))
                .networkModule(NetworkModule())
                .build()
        component.inject(this)
    }

    private fun createAppConfig(): AppConfig = AppConfig(BuildConfig.BASE_URL)
}