package com.swiezaki.swiezakimobile.app

import com.swiezaki.swiezakimobile.network.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, NetworkModule::class))
interface AppComponent{

    fun inject(app: App)
}