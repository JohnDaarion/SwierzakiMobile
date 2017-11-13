package com.swiezaki.swiezakimobile.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.swiezaki.swiezakimobile.BuildConfig
import com.swiezaki.swiezakimobile.app.AppConfig
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    fun gson(): Gson = GsonBuilder()
            .create()

    @Provides
    fun httpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BASIC else HttpLoggingInterceptor.Level.NONE

        return OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
    }

    @Provides
    @Singleton
    fun retrofit(gson: Gson, httpClient: OkHttpClient, appConfig: AppConfig): Retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory(RxJava2CallAdapterFactory.create()))
            .baseUrl(appConfig.baseUrl)
            .client(httpClient)
            .build()
}