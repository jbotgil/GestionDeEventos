package com.example.gestiondeeventos.menuPrincipal.usuario

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.database.Eventos
import com.example.gestiondeeventos.controlador.EventsController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState

@Composable
fun EventCardInfo(navController: NavController, context: Context) {
    val eventoController = EventsController(context)
    val sharedPreferences = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)

    val evento: Eventos? = eventoController.getEventoPorId(
        sharedPreferences.getLong("selectedEventId", -1L)
    )

    val tituloEvento by remember { mutableStateOf(evento?.titulo ?: "Sin título") }
    val direccionEvento by remember { mutableStateOf(evento?.direccion ?: "Sin dirección") }
    val fechaEvento by remember { mutableStateOf(evento?.fecha ?: "Sin fecha") }
    val latitudEvento by remember { mutableDoubleStateOf(evento?.latitud ?: 0.0) }
    val longitudEvento by remember { mutableDoubleStateOf(evento?.longitud ?: 0.0) }

    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.6f))
                )
            )
    ) {
        // Ícono de compartir en la esquina superior derecha
        IconButton(
            onClick = {
                val shareText = """
                    Evento: $tituloEvento
                    Fecha: $fechaEvento
                    Dirección: $direccionEvento
                    Ubicación: https://maps.google.com/?q=$latitudEvento,$longitudEvento
                """.trimIndent()

                val intent = android.content.Intent(android.content.Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(android.content.Intent.EXTRA_TEXT, shareText)
                }
                context.startActivity(android.content.Intent.createChooser(intent, "Compartir evento"))
            },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Share,
                contentDescription = "Compartir",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = tituloEvento,
                color = Color.White,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Text(
                text = fechaEvento,
                color = Color.White,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            Text(
                text = direccionEvento,
                color = Color.White,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(vertical = 4.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(screenHeight * 0.5f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Gray)
            ) {
                MapaCompose(latitudEvento, longitudEvento)
            }

            Spacer(modifier = Modifier.height(screenHeight * 0.2f))

            Button(
                onClick = {
                    Toast.makeText(context, "Saliendo...", Toast.LENGTH_SHORT).apply {
                        setGravity(android.view.Gravity.BOTTOM, 0, 180)
                    }.show()
                    navController.popBackStack()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clip(RoundedCornerShape(10.dp)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(android.graphics.Color.parseColor("#A12D4A"))
                )
            ) {
                Text("Volver", style = TextStyle(color = Color.White))
            }
        }
    }
}

@Composable
fun MapaCompose(latitud: Double, longitud: Double) {
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = CameraPositionState(
            CameraPosition.fromLatLngZoom(
                LatLng(latitud,longitud),
                17f // Nivel de zoom inicial
            )
        ),
        //properties = MapProperties(mapType = MapType.SATELLITE)
    ) {
        // Marcador en una ubicación específica
        Marker(
            state = MarkerState(position = LatLng(latitud, longitud)),
            title = "Encuentro",
            snippet = "Marcador en la torre el punto de encuentro"
        )
    }
}
