package com.example.freenow.hilt

import com.example.freenow.BuildConfig
import com.example.freenow.data.remote.ApiService
import com.example.freenow.data.remote.ApiServiceFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideApiService(): ApiService {
        return ApiServiceFactory.makeApiService(BuildConfig.DEBUG)
    }



}


