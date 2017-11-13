package com.swiezaki.swiezakimobile.network.error

sealed class RetrofitException : RuntimeException()

data class Http(val response: ErrorResponse) : RetrofitException()

data class Network(val exception: Throwable) : RetrofitException()

data class Unexpected(val exception: Throwable) : RetrofitException()

