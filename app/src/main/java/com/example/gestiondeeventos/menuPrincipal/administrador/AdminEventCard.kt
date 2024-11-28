package com.example.gestiondeeventos.menuPrincipal.administrador

import android.content.Context
import android.widget.Toast
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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.database.Eventos
import com.example.gestiondeeventos.controlador.EventsController

@Composable
fun AdminEventCard(
    context: Context,
    title: String,
    date: String,
    location: String,
    onDeleteConfirmed: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Color(android.graphics.Color.parseColor("#A12D4A")),
                shape = MaterialTheme.shapes.medium
            )
            .padding(16.dp)
            /*.clickable { onSelect() } // Selecciona el evento al hacer clic*/
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .fillMaxHeight()
                .background(Color.LightGray)
                .padding(25.dp),
            contentAlignment = Alignment.Center
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
            onClick = { showDialog = true },
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .align(Alignment.CenterVertically)
                .border(1.dp, Color.Transparent, CircleShape),
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
        ) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = null, tint = Color.White)
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Confirmar eliminación") },
                text = { Text("¿Estás seguro de que deseas eliminar este evento?") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showDialog = false
                            onDeleteConfirmed() // Acción para eliminar
                        }
                    ) {
                        Text("Aceptar")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            showDialog = false
                            Toast.makeText(
                                context,
                                "Acción cancelada",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    ) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}





@Composable
fun AdminEventListScreen(context: Context) {
    val eventController = EventsController(context)
    val events = remember { mutableStateListOf<Eventos>() }

    // Cargar eventos al iniciar
    LaunchedEffect(Unit) {
        events.addAll(eventController.getEventos() ?: emptyList())
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(events.size) { index ->
                    val event = events[index]
                    AdminEventCard(
                        context = context,
                        title = event.titulo,
                        date = event.fecha,
                        location = event.direccion,
                        onDeleteConfirmed = {
                            // Eliminar el evento seleccionado
                            eventController.deleteEvento(event.id_evento)
                            events.remove(event)
                            Toast.makeText(context, "Evento eliminado", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    }
}
