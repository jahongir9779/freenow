package com.example.freenow.ui.map

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.freenow.R
import com.example.freenow.common.EFleetType
import com.example.freenow.domain.models.PoiModel
import com.example.freenow.ui.view_objects.BoundsViewObj
import com.example.freenow.ui.view_objects.PoiViewObj
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_map.*


@AndroidEntryPoint
class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    companion object {
        val EXTRA_POI_VIEW_OBJECT = "POI_VIEW_OBJECT"
        val EXTRA_DEFAULT_BOUNDS_VIEW_OBJECT = "DEFAULT_BOUNDS"
    }

    private val initial_zoom_lvl = 17F

    private var targetPoi: PoiViewObj? = null
    private var defaultBounds: BoundsViewObj? = null
    private lateinit var mMap: GoogleMap
    private val viewModel: MapViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        targetPoi = intent.getParcelableExtra(EXTRA_POI_VIEW_OBJECT)
        defaultBounds = intent.getParcelableExtra(EXTRA_DEFAULT_BOUNDS_VIEW_OBJECT)

        setupActionbar()
        setupMap()
        subscribeObservers()
    }

    private fun setupMap() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun subscribeObservers() {
        viewModel.poiList.observe(this, {
            showPointsOnMap(it)
        })

        viewModel.isLoading.observe(this, {
            horizontalProgress.visibility = if (it == true) View.VISIBLE else View.INVISIBLE
        })

        viewModel.errorMessage.observe(this, {
            Snackbar.make(llParent, it, Snackbar.LENGTH_SHORT).show()
        })
    }

    private fun showPointsOnMap(pois: List<PoiModel>?) {
        mMap.clear()
        pois?.forEach { poiModel ->
            addMarkerToMap(
                LatLng(poiModel.coordinate.latitude, poiModel.coordinate.longitude),
                poiModel.fleetType,
                poiModel.heading.toFloat()
            )
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        if (targetPoi != null) {
            val targetPoint =
                LatLng(targetPoi!!.coordinate.latitude, targetPoi!!.coordinate.longitude)
            addMarkerToMap(targetPoint, targetPoi!!.fleetType, targetPoi!!.heading.toFloat())
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(targetPoint, initial_zoom_lvl))
        } else if (defaultBounds != null) {
            val targetPoint = LatLng(defaultBounds!!.p1Latitude, defaultBounds!!.p1Longitude)
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(targetPoint, initial_zoom_lvl))

            mMap.setOnCameraIdleListener {
                viewModel.getPoiListInBounds(
                    mMap.projection.visibleRegion.farLeft.latitude,
                    mMap.projection.visibleRegion.farLeft.longitude,
                    mMap.projection.visibleRegion.nearRight.latitude,
                    mMap.projection.visibleRegion.nearRight.longitude
                )
            }
        }
    }

    private fun addMarkerToMap(targetPoint: LatLng, fleetType: EFleetType, heading: Float) {
        mMap.addMarker(
            MarkerOptions().apply {
                position(targetPoint)
                title(fleetType.name)
                rotation(heading)
                icon(BitmapDescriptorFactory.fromBitmap(getIconForFleetType(fleetType)))
            }
        )
    }

    private fun getIconForFleetType(fleetType: EFleetType): Bitmap? {
        val carIconResourceId =
            if (fleetType == EFleetType.POOLING) R.drawable.car_pooling else R.drawable.car_taxi
        val carIconBitmapDrawable =
            ContextCompat.getDrawable(this, carIconResourceId) as BitmapDrawable
        return Bitmap.createScaledBitmap(carIconBitmapDrawable.bitmap, 50, 100, false)
    }


    private fun setupActionbar() {
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

}