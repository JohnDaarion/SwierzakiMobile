package com.swiezaki.swiezakimobile.app

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: App, private val appConfig: AppConfig){

    @Provides
    @Singleton
    fun getAppContext(): App = application

    @Provides
    @Singleton
    fun getAppConfig(): AppConfig = appConfig

}