package com.example.freenow.ui

import androidx.lifecycle.LiveData
import com.example.freenow.common.ResultSuccess
import com.example.freenow.data.PoiRepositoryImpl
import com.example.freenow.domain.models.BoundsModel
import com.example.freenow.domain.models.PoiModel
import com.example.freenow.domain.repositories.PoiRepository
import com.example.freenow.domain.usecases.GetPoiListForBounds
import com.example.freenow.remote.PoiRemoteImpl
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test

class MainViewModelTest {

    private lateinit var viewModel: MainViewModel

    private lateinit var isLoadingLiveData: LiveData<Boolean>

    private lateinit var isErrorLiveData: LiveData<String>


    @Before
    fun setUp() {
//        `when`(repo.teams).thenReturn(teamListLiveData)
        viewModel = MainViewModel(GetPoiListForBounds(mockk<PoiRepositoryImpl>()))
        repository = mockk<PoiRepository>()
        isLoadingLiveData = viewModel.isLoading
        isErrorLiveData = viewModel.errorMessage
    }

    @Test
    fun `get poi list with empty bounds, returns error`() {
        viewModel.getPoiListInBounds()
    }

    private lateinit var repository: PoiRepository

    @Test
    fun testGetDataSuccess() {
        val successResult = ResultSuccess<List<PoiModel>>(listOf())
        runBlockingTest {
            (repository.getPoiListInBounds(
                BoundsModel(
                    .694865,
                    9.757589,
                    53.394655,
                    10.099891
                )
            ))
            (successResult)

            val result = viewModel.getPoiListInBounds()

            // verify loading
            assert(isLoadingLiveData.value == true)

//            result.observeForTesting {
//                testDispatcher.resumeDispatcher()
//                assertThat(result.value).isEqualTo(successResult)
//            }
        }
    }

    @Test
    fun givenServiceMock_whenCallingMockedMethod_thenCorrectlyVerified() {
        // given
        val service = mockk<PoiRemoteImpl>()
        every {
            runBlocking {
                service.getPoiListInBounds(
                    BoundsModel(
                        .694865,
                        9.757589,
                        53.394655,
                        10.099891
                    )
                )
            }
        } returns ResultSuccess(listOf())

        // when
        val result = runBlocking {
            service.getPoiListInBounds(
                BoundsModel(
                    .694865,
                    9.757589,
                    53.394655,
                    10.099891
                )
            )
        }

        // then
        verify {
            runBlocking {
                service.getPoiListInBounds(
                    BoundsModel(
                        .694865,
                        9.757589,
                        53.394655,
                        10.099891
                    )
                )
            }
        }
        assertEquals(ResultSuccess<List<PoiModel>>(listOf()), result)
    }
}