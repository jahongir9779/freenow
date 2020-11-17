package com.example.freenow.common

sealed class ResultWrapper<out V> {
//    override fun toString(): String {
//        return when (this) {
//            is ResultSuccess<*> -> "Success [data = ${this.value}]"
//            is ResultError -> "Error [exception=${this.message}]"
//        }.exhaustive
//    }
}

data class ResultError(val message: String? = null, val code: Int? = null) :
    ResultWrapper<Nothing>()

data class ResultSuccess<out V>(val value: V) : ResultWrapper<V>()



val <T> T.exhaustive: T
    get() = this

