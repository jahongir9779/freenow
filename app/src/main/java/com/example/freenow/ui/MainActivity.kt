package com.example.freenow.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.freenow.R
import com.example.freenow.domain.models.PoiModel
import com.example.freenow.ui.map.MapsActivity
import com.example.freenow.ui.map.MapsActivity.Companion.EXTRA_DEFAULT_BOUNDS_VIEW_OBJECT
import com.example.freenow.ui.map.MapsActivity.Companion.EXTRA_POI_VIEW_OBJECT
import com.example.freenow.ui.view_objects.PoiViewObjMapper
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var poiListAdapter: PoiListAdapter
    private val viewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViews()
        attachListeners()
        subscribeObservers()
        viewModel.getPoiListInBounds()
    }

    private fun setupViews() {
        poiListAdapter = PoiListAdapter(listOf(), object : OnPoiItemClickListener {
            override fun onItemClicked(poi: PoiModel, adapterPosition: Int) {
                startActivity(Intent(this@MainActivity, MapsActivity::class.java).apply {
                    putExtra(EXTRA_POI_VIEW_OBJECT, PoiViewObjMapper.toPoiViewObject(poi))
                })
            }
        })
        rvPoiList.setHasFixedSize(true)
        rvPoiList.adapter = poiListAdapter
    }

    private fun attachListeners() {
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.getPoiListInBounds()
        }

        btnShowAvailable.setOnClickListener {
            startActivity(Intent(this@MainActivity, MapsActivity::class.java).apply {
                putExtra(EXTRA_DEFAULT_BOUNDS_VIEW_OBJECT, viewModel.defaultBounds)
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
