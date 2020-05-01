package com.example.ubiquitousdroid.network

data class Resource<out T>(val status: status, val data: T?, val message: String?) {

    companion object {

        fun <T> success(data: T?): Resource<T> {
            return Resource(status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(status.ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(status.LOADING, data, null)
        }

    }

}