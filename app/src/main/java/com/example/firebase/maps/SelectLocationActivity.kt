package com.example.firebase.maps

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Rect
import android.graphics.RectF
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.firebase.R
import com.example.firebase.databinding.ActivitySelectLocationBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class SelectLocationActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap

    private val selectLocationActivity by lazy {
        ActivitySelectLocationBinding.inflate(
            layoutInflater
        )
    }
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(selectLocationActivity.root)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        selectLocationActivity.backArrow.setOnClickListener {
            onBackPressed()
        }
        selectLocationActivity.imgCurrentLocationMap.setOnClickListener {

        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            window.statusBarColor = resources.getColor(R.color.black, this.theme)
        } else
            window.statusBarColor = resources.getColor(R.color.black, this.theme)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val locations = listOf(
            LatLng(13.0475, 80.2824),
            LatLng(13.0821, 80.2754),
            LatLng(13.0331, 80.2691),
            LatLng(13.0798, 80.2870),
            LatLng(13.0067, 80.2344),
            LatLng(13.0004, 80.2664),
            LatLng(13.0528, 80.2400),
            LatLng(12.9908, 80.2182),
            LatLng(13.0500, 80.2505),
            LatLng(13.0106, 80.1918),
            LatLng(13.0695, 80.1914),
            LatLng(13.0878, 80.2103),
        )
        // Adding markers for each location with custom icon
        for (location in locations) {
            mMap.addMarker(
                MarkerOptions()
                    .position(location)
                    .title("Marker in ${location.latitude}, ${location.longitude}")
                    .icon(BitmapDescriptorFactory.fromBitmap(createCustomMarker()))
            )
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locations[0], 12.0f))
        mMap = googleMap

    }

    private fun createCustomMarker(): Bitmap {
        val drawable = ContextCompat.getDrawable(this, R.drawable.custom_marker)!!
        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)

        val output = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        val canvasOutput = Canvas(output)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        val rect = Rect(0, 0, bitmap.width, bitmap.height)
        val rectF = RectF(rect)
        val path = Path()

        path.addCircle(
            rectF.centerX(),
            rectF.centerY(),
            Math.min(rectF.width() / 2.0f, rectF.height() / 2.0f),
            Path.Direction.CCW
        )

        canvasOutput.clipPath(path)
        canvasOutput.drawBitmap(bitmap, rect, rect, paint)

        return output
    }
}

