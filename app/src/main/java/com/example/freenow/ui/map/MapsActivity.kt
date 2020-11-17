package com.example.freenow.ui.map

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.view.MenuItem
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
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
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_map.*


const val EXTRA_POI_VIEW_OBJECT = "POI_VIEW_OBJECT"
const val EXTRA_DEFAULT_BOUNDS_VIEW_OBJECT = "DEFAULT_BOUNDS"

@AndroidEntryPoint
class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private val initial_zoom_lvl = 17F

    private var targetPoi: PoiViewObj? = null
    private var defaultBounds: BoundsViewObj? = null
    private lateinit var mMap: GoogleMap
    private val viewModel: MapViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        setupActionbar()

        targetPoi = intent.getParcelableExtra(EXTRA_POI_VIEW_OBJECT)
        defaultBounds = intent.getParcelableExtra(EXTRA_DEFAULT_BOUNDS_VIEW_OBJECT)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        subscribeObservers()
    }

    private fun subscribeObservers() {
        viewModel.poiList.observe(this, {
            showPointsOnMap(it)
        })

        viewModel.errorMessage.observe(this, {
            Snackbar.make(llParent, it, Snackbar.LENGTH_SHORT).show()
        })

    }

    private fun showPointsOnMap(pois: List<PoiModel>?) {
         mMap.clear()
        pois?.forEach { poiModel ->
            val poiPoint = LatLng(poiModel.coordinate.latitude, poiModel.coordinate.longitude)
            val markerOptions = MarkerOptions().position(poiPoint).title(poiModel.fleetType.name)
                .rotation(poiModel.heading.toFloat()).alpha(0F)
            val carIconResourceId =
                if (poiModel.fleetType == EFleetType.POOLING) R.drawable.car_pooling else R.drawable.car_taxi
            val bitmapdraw = ContextCompat.getDrawable(this, carIconResourceId) as BitmapDrawable
            val b = bitmapdraw.bitmap
            val smallMarker = Bitmap.createScaledBitmap(b, 50, 100, false)
            val marker = mMap.addMarker(markerOptions)

            marker.setIcon(BitmapDescriptorFactory.fromBitmap(smallMarker))
            animateMarker(marker)
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
            val markerOptions =
                MarkerOptions().position(targetPoint).title(targetPoi!!.fleetType.name)
                    .rotation(targetPoi!!.heading.toFloat())
            val carIconResourceId =
                if (targetPoi!!.fleetType == EFleetType.POOLING) R.drawable.car_pooling else R.drawable.car_taxi
            val bitmapdraw = ContextCompat.getDrawable(this, carIconResourceId) as BitmapDrawable
            val b = bitmapdraw.bitmap
            val smallMarker = Bitmap.createScaledBitmap(b, 50, 100, false)
            val marker = mMap.addMarker(markerOptions)

            marker.setIcon(BitmapDescriptorFactory.fromBitmap(smallMarker))
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


    fun animateMarker(marker: Marker) {
        val duration: Long = 600
        val handler = Handler()
        val start = SystemClock.uptimeMillis();

        val interpolator: Interpolator = LinearInterpolator()
        handler.post(object : Runnable {
            override fun run() {
                val elapsed = SystemClock.uptimeMillis() - start
                val t = interpolator.getInterpolation(elapsed.toFloat() / duration)
                val target = 1 * t
                marker.alpha = target
                if (t < 1.0) {
                    // Post again 10ms later.
                    handler.postDelayed(this, 10)
                } else {
                    // animation ended
                }
            }
        })
    }

}