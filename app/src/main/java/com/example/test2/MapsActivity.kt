package com.example.test2
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.SphericalUtil

class MapsActivity : FragmentActivity(), OnMapReadyCallback {
    private lateinit var currentLocation: Location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val permissionCode = 101
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(this@MapsActivity)
        fetchLocation()

    }
    private fun fetchLocation() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), permissionCode
            )
            return
        }

        val task = fusedLocationProviderClient.lastLocation
        task.addOnSuccessListener { location ->
            if (location != null) {
                currentLocation = location
                val supportMapFragment = (supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?)!!
                supportMapFragment.getMapAsync(this@MapsActivity)
            }
        }

    }
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            permissionCode -> if (grantResults.isNotEmpty() && grantResults[0] ==
                PackageManager.PERMISSION_GRANTED
            ) { fetchLocation() }
            else
            {
                Toast.makeText(this, "Turn on Location", Toast.LENGTH_SHORT).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val myLocation = LatLng(currentLocation.latitude, currentLocation.longitude)
        val barber = LatLng(32.082026462744025, 34.78029297866811)
        val markerOptions = MarkerOptions().position(myLocation).title("I am here!")
        val markerOptions2 = MarkerOptions().position(barber).title("Barbershop")
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(myLocation))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 10.5f))
        googleMap.addMarker(markerOptions)
        googleMap.addMarker(markerOptions2)
        googleMap.addPolyline(
            PolylineOptions()
                .add(myLocation, barber))
        val distance = SphericalUtil.computeDistanceBetween(myLocation, barber)
        Toast.makeText(this, "Distance between My Location and the barbershop is : \n " + String.format("%.2f", distance / 1000) + "km", Toast.LENGTH_LONG).show();
    }
}
