package com.example.freenow.domain.usecases

interface UseCaseWithParams<in Params, out R> {

    suspend fun execute(params: Params): R
}