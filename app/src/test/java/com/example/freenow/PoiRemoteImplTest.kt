package com.example.freenow

import com.example.freenow.remote.ApiService
import com.example.freenow.remote.ApiServiceFactory
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class PoiRemoteImplTest {

    @Mock
    lateinit var service: ApiService

    @Before
    internal fun setUp() {
        service = ApiServiceFactory.makeApiService(BuildConfig.DEBUG)
    }

    @Test
    internal fun get_poi_list_with_valid_params() {
        val actualRepos = runBlocking {
            service.getPoiListInBounds(53.694865, 9.757589, 53.394655, 10.099891)
        }
        assert(actualRepos.poiList != null)
        actualRepos.poiList!!.forEach {
            assert(!it.anyUnset())
        }
    }


}