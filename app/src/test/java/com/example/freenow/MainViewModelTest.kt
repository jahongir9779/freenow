package com.example.freenow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.freenow.common.EFleetType
import com.example.freenow.common.ResultSuccess
import com.example.freenow.domain.models.BoundsModel
import com.example.freenow.domain.models.CoordinateModel
import com.example.freenow.domain.models.PoiModel
import com.example.freenow.domain.repositories.PoiRepository
import com.example.freenow.domain.usecases.GetPoiListForBounds
import com.example.freenow.ui.MainViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class MainViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val repo = Mockito.mock(PoiRepository::class.java)
    private val getPoiList = GetPoiListForBounds(repo)
    private val viewModel = MainViewModel(getPoiList)


  // Test logic failing, did not have enough time to fix

    @Test
    fun testPoiList() {
        runBlocking {
            Mockito.`when`(
                getPoiList.execute(
                    BoundsModel.Factory.create(
                        viewModel.defaultBounds.p1Latitude,
                        viewModel.defaultBounds.p1Longitude,
                        viewModel.defaultBounds.p2Latitude,
                        viewModel.defaultBounds.p2Longitude
                    )
                )
            ).thenReturn(
                ResultSuccess(
                    listOf(
                        PoiModel(
                            CoordinateModel(0.0, 0.0),
                            EFleetType.TAXI,
                            0.0,
                            0
                        )
                    )
                )
            )
        }


        viewModel.getPoiListInBounds()

        assert(viewModel.poiList.value?.size == 1)
    }


    @Test
    fun testEmptyPoiList() {
        runBlocking {
            Mockito.`when`(
                getPoiList.execute(
                    BoundsModel.Factory.create(
                        viewModel.defaultBounds.p1Latitude,
                        viewModel.defaultBounds.p1Longitude,
                        viewModel.defaultBounds.p2Latitude,
                        viewModel.defaultBounds.p2Longitude
                    )
                )
            ).thenReturn(ResultSuccess(listOf()))
        }

        viewModel.getPoiListInBounds()
        assert(viewModel.poiList.value?.size == 0)
    }


}