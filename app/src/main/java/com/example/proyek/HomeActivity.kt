package com.example.proyek

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // ===============================
        // Inisialisasi Google Maps
        // ===============================
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // ===============================
        // Bottom Navigation
        // ===============================
        bottomNav = findViewById(R.id.bottomNav)

        // Default menu aktif = Home / Maps
        bottomNav.selectedItemId = R.id.menu_home

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {

                R.id.menu_home -> {
                    // Sudah di Home, tidak perlu pindah
                    true
                }

//                R.id.menu_search -> {
//                    startActivity(Intent(this, SearchActivity::class.java))
//                    finish()
//                    true
//                }
//
//                R.id.menu_profile -> {
//                    startActivity(Intent(this, ProfileActivity::class.java))
//                    finish()
//                    true
//                }

                else -> false
            }
        }
    }

    // ===============================
    // Google Maps Ready
    // ===============================
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Contoh lokasi (sementara)
        val sanFrancisco = LatLng(37.7749, -122.4194)

        mMap.addMarker(
            MarkerOptions()
                .position(sanFrancisco)
                .title("PetFinder Area")
        )

        mMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(sanFrancisco, 12f)
        )

        // UI Maps seperti di gambar
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isMyLocationButtonEnabled = true
        mMap.uiSettings.isCompassEnabled = true
    }
}
