package com.example.gestiondeeventos.menuPrincipal.administrador

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AdminEventCard(title: String, date: String, location: String, onEdit: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Color(android.graphics.Color.parseColor("#A12D4A")),
                shape = MaterialTheme.shapes.medium
            )
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .fillMaxHeight() // Esto asegura que el Box ocupe todo el alto disponible
                .background(Color.LightGray)
                .padding(25.dp),
            contentAlignment = Alignment.Center // Centra el contenido tanto vertical como horizontalmente
        ) {
            Text("IMG") // Reemplaza con tu imagen
        }

        Column(
            modifier = Modifier
                .weight(2f)
                .padding(start = 16.dp)
        ) {
            Text(title, color = Color.White)
            Text(date, color = Color.White.copy(alpha = 0.8f))
            Text(location, color = Color.White.copy(alpha = 0.6f))
        }

        Button(
            onClick = onEdit,
            modifier = Modifier
                .size(40.dp) // Tamaño fijo para que sea redondo
                .clip(CircleShape)
                .align(Alignment.CenterVertically)
                .border(1.dp, Color.Transparent, CircleShape), // Quitar el fondo
            contentPadding = PaddingValues(0.dp), // Sin padding para ajustarse al círculo
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent) // Fondo transparente
        ) {
            Icon(imageVector = Icons.Default.Create, contentDescription = null, tint = Color.White)
        }
    }
}



data class AdminEventData(val title: String, val date: String, val location: String)

@Composable
fun AdminEventListScreen() {
    val events = mutableListOf<AdminEventData>()

    for (i in 1..10) {
        events.add(AdminEventData("Evento $i", "20/11/2024", "Dirección $i"))
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Lista de eventos
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp) // Espacio reservado para los botones
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(events.size) { index ->
                    val event = events[index]
                    AdminEventCard(
                        title = event.title,
                        date = event.date,
                        location = event.location,
                        onEdit = { /* Acción para editar */ }
                    )
                }
            }
        }
    }
}