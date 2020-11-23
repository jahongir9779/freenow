package com.example.freenow.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.freenow.common.ResultError
import com.example.freenow.domain.models.BoundsModel
import com.example.freenow.domain.usecases.GetPoiListForBounds
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()
    private val mockGetPoiListForBounds = mockk<GetPoiListForBounds>()
    private val testSubject = MainViewModel(mockGetPoiListForBounds)

    @Before
    fun setup() {
        // Sets the given [dispatcher] as an underlying dispatcher of [Dispatchers.Main].
        // All consecutive usages of [Dispatchers.Main] will use given [dispatcher] under the hood.
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun test_get_poi_list_in_bounds() = runBlocking {
        mockkObject(BoundsModel.Factory)
        val mockBoundsModel = mockk<BoundsModel>()
        val mockResult = mockk<ResultError>()
        every { mockResult.message } returns "error"
        every {
            BoundsModel.Factory.create(
                testSubject.defaultBounds.p1Latitude,
                testSubject.defaultBounds.p1Longitude,
                testSubject.defaultBounds.p2Latitude,
                testSubject.defaultBounds.p2Longitude
            )
        } returns mockBoundsModel

        every {
            runBlocking {
                mockGetPoiListForBounds.execute(mockBoundsModel)
            }
        }  returns mockResult

        testSubject.getPoiListInBounds()

        verify(exactly = 1) { mockResult.message }
        verify(exactly = 1) {
            BoundsModel.Factory.create(
                testSubject.defaultBounds.p1Latitude,
                testSubject.defaultBounds.p1Longitude,
                testSubject.defaultBounds.p2Latitude,
                testSubject.defaultBounds.p2Longitude
            )
        }
        verify(exactly = 1) {
            runBlocking {
                mockGetPoiListForBounds.execute(mockBoundsModel)
            }
        }

        assertEquals(testSubject.errorMessage.value, "error")
    }

}