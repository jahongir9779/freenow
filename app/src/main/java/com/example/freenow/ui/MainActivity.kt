package com.example.freenow.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.freenow.R
import com.example.freenow.domain.models.PoiModel
import com.example.freenow.ui.map.EXTRA_DEFAULT_BOUNDS_VIEW_OBJECT
import com.example.freenow.ui.map.EXTRA_POI_VIEW_OBJECT
import com.example.freenow.ui.map.MapsActivity
import com.example.freenow.ui.view_objects.BoundsViewObj
import com.example.freenow.ui.view_objects.PoiViewObjMapper
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var poiListAdapter: PoiListAdapter
    private val viewModel: MainViewModel by viewModels()

    val defaultBounds = BoundsViewObj(53.694865, 9.757589, 53.394655, 10.099891)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        poiListAdapter = PoiListAdapter(listOf(), object : OnPoiItemClickListener {
            override fun onItemClicked(poi: PoiModel, adapterPosition: Int) {
                startActivity(Intent(this@MainActivity, MapsActivity::class.java).apply {
                    putExtra(EXTRA_POI_VIEW_OBJECT, PoiViewObjMapper.toPoiViewObject(poi))
                })
            }
        })
        rvPoiList.setHasFixedSize(true)
        rvPoiList.adapter = poiListAdapter
        attachListeners()
        subscribeObservers()
        viewModel.getPoiListInBounds(
            defaultBounds.p1Latitude,
            defaultBounds.p1Longitude,
            defaultBounds.p2Latitude,
            defaultBounds.p2Longitude
        )
    }

    private fun attachListeners() {
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.getPoiListInBounds(
                defaultBounds.p1Latitude,
                defaultBounds.p1Longitude,
                defaultBounds.p2Latitude,
                defaultBounds.p2Longitude
            )
        }

        btnShowAvailable.setOnClickListener {
            startActivity(Intent(this@MainActivity, MapsActivity::class.java).apply {
                putExtra(EXTRA_DEFAULT_BOUNDS_VIEW_OBJECT, defaultBounds)
            })
        }
    }

    private fun subscribeObservers() {
        viewModel.poiList.observe(this, {
            poiListAdapter.postNewDataSet(it)
        })

        viewModel.isLoading.observe(this, { isLoading ->
            swipeRefreshLayout.isRefreshing = isLoading
        })


        viewModel.errorMessage.observe(this, { message ->
            Snackbar.make(parentLayout, message, Snackbar.LENGTH_SHORT).show()
        })

    }


}
