package com.swiezaki.swiezakimobile.network

import com.swiezaki.swiezakimobile.network.error.*
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.io.IOException
import java.lang.reflect.Type

class RxErrorHandlingCallAdapterFactory(private val original: RxJava2CallAdapterFactory) : CallAdapter.Factory() {

    override fun get(returnType: Type, annotations: Array<out Annotation>, retrofit: Retrofit): CallAdapter<*, Observable<*>> {
        return RxCallAdapter(retrofit, original.get(returnType, annotations, retrofit))
    }
}

class RxCallAdapter<R>(private val retrofit: Retrofit, private val original: CallAdapter<R, *>?) : CallAdapter<R, Observable<*>> {

    override fun adapt(call: Call<R>): Observable<*> {
        return (original?.adapt(call) as Observable<*>)
                .onErrorResumeNext { throwable: Throwable -> Observable.error(asRetrofitException(throwable)) }
    }

    override fun responseType(): Type? = original?.responseType()

    private fun asRetrofitException(exception: Throwable): RetrofitException =
        when (exception){
            is HttpException -> Http(convertResponse(exception.response()?.errorBody()))
            is IOException -> Network(exception)
            else -> Unexpected(exception)
        }

    private fun convertResponse(errorBody: ResponseBody?): ErrorResponse {
        val errorResponse: ErrorResponse
        errorResponse = if (errorBody == null){
            ErrorResponse.EMPTY
        } else {
            val converter = retrofit.responseBodyConverter<ErrorResponse>(ErrorResponse::class.java, arrayOf())
            converter.convert(errorBody)
        }
        return errorResponse
    }
}