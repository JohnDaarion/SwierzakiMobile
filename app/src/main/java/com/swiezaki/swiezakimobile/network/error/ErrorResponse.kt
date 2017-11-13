package com.swiezaki.swiezakimobile.network.error

const val UNKNOWN = -1

data class ErrorResponse(val code: Int, val developerMessage: String){

    companion object {
        val EMPTY = ErrorResponse(UNKNOWN, "")
    }
}