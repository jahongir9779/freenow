package com.example.freenow.hilt

import com.example.freenow.data.mappers.PoiMapper
import com.example.freenow.data.remote.ApiService
import com.example.freenow.data.repository.RemoteRepoImpl
import com.example.freenow.domain.repositories.RemoteRepo
import com.example.freenow.domain.usecases.GetAvailablePoiListForBounds
import com.example.freenow.domain.usecases.GetPoiListForBounds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {


    @Provides
    fun provideGetPoiListUseCase(repository: RemoteRepo): GetPoiListForBounds {
        return GetPoiListForBounds(repository)
    }

    @Provides
    fun provideGetAvailablePoiListUseCase(repository: RemoteRepo): GetAvailablePoiListForBounds {
        return GetAvailablePoiListForBounds(repository)
    }

    @Provides
    fun provideRemoteRepoImpl(apiService: ApiService, mapper: PoiMapper): RemoteRepo {
        return RemoteRepoImpl(apiService, mapper)
    }

    @Provides
    fun providePoiMapper() = PoiMapper()


}




