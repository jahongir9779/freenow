package com.example.freenow.domain.usecases

interface UseCase<R> {
   suspend fun execute(): R
}