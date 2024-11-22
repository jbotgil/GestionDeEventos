package com.example.gestiondeeventos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun EventList(eventCards: List<EventData>, onEdit: (Int) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp) // Espaciado entre tarjetas
    ) {
        items(eventCards.size) { index ->
            val event = eventCards[index]
            EventCard(
                title = event.title,
                date = event.date,
                location = event.location,
                onEdit = { onEdit(index) } // Pasa el índice del evento a la acción
            )
        }
    }
}

@Composable
fun EventCard(title: String, date: String, location: String, onEdit: () -> Unit) {
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
                .background(Color.LightGray)
                .padding(16.dp)
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
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            Text("Edit") // Reemplaza con ícono
        }
    }
}

data class EventData(val title: String, val date: String, val location: String)

@Composable
fun EventListScreen() {
    val events = mutableListOf<EventData>()

    for (i in 1..10) {
        events.add(EventData("Evento $i", "20/11/2024", "Dirección $i"))
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
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
                    EventCard(
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