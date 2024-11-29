package com.example.gestiondeeventos.controlador

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class LocationController(private val context: Context) {

    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    fun obtenerUbicacionActual(onLocationObtained: (latitude: String, longitude: String) -> Unit) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    onLocationObtained(it.latitude.toString(), it.longitude.toString())
                } ?: run {
                    Toast.makeText(context, "No se pudo obtener la ubicación.", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(context, "Permiso de ubicación no concedido.", Toast.LENGTH_SHORT).show()
        }
    }
}
