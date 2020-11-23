package com.example.freenow.ui

import androidx.lifecycle.LiveData
import com.example.freenow.domain.usecases.GetPoiListForBounds
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class MainViewModelTest {

    private lateinit var viewModel: MainViewModel

    private lateinit var isLoadingLiveData: LiveData<Boolean>

    private lateinit var isErrorLiveData: LiveData<String>


    @Before
    fun setUp() {
//        `when`(repo.teams).thenReturn(teamListLiveData)
//        viewModel = MainViewModel(repo)

        isLoadingLiveData = viewModel.isLoading
        isErrorLiveData = viewModel.errorMessage



    }

    @Test
    fun `get poi list with empty bounds, returns error`() {
        viewModel.getPoiListInBounds()

//val resposne = viewModel.get
    }


}