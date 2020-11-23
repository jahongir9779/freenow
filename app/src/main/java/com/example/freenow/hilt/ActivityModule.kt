package com.example.freenow.hilt

import com.example.freenow.data.PoiRepositoryImpl
import com.example.freenow.data.mappers.PoiDataToDomainMapper
import com.example.freenow.data.repository.PoiRemote
import com.example.freenow.data.source.PoiRemoteDataStore
import com.example.freenow.remote.ApiService
import com.example.freenow.remote.PoiRemoteImpl
import com.example.freenow.domain.repositories.PoiRepository
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
    fun provideGetPoiListUseCase(repository: PoiRepository): GetPoiListForBounds {
        return GetPoiListForBounds(repository)
    }

    @Provides
    fun provideGetAvailablePoiListUseCase(repository: PoiRepository): GetAvailablePoiListForBounds {
        return GetAvailablePoiListForBounds(repository)
    }

    @Provides
    fun providePoiRepositoryImpl(poiRemoteDataStore: PoiRemoteDataStore): PoiRepository {
        return PoiRepositoryImpl(poiRemoteDataStore )
    }


    @Provides
    fun providePoiRemoteDataStore(poiRemote: PoiRemote): PoiRemoteDataStore {
        return PoiRemoteDataStore( poiRemote)
    }

    @Provides
    fun provideRemoteRepoImpl(apiService: ApiService, mapper: PoiDataToDomainMapper): PoiRemote {
        return PoiRemoteImpl(apiService, mapper)
    }

    @Provides
    fun providePoiMapper() = PoiDataToDomainMapper()


}




