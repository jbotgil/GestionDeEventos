package com.example.gestiondeeventos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun UserEventScreen(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color.Black.copy(alpha = 0.8f), Color.Black.copy(alpha = 0.6f))
                )
            )
    ) {
        // Contenido principal
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Sección del título
            Box(
                modifier = Modifier
                    .weight(1.2f) // Proporción del espacio vertical
                    .fillMaxSize(),
                contentAlignment = Alignment.Center // Centra el texto vertical y horizontalmente
            ) {
                Text(
                    text = "EVENTOS",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineLarge // Estilo del texto
                )
            }

            // Lista de eventos con espacio reservado para botones
            Box(
                modifier = Modifier
                    .weight(6f) // Ajusta el peso restante para la lista de eventos
                    .padding(bottom = 80.dp) // Espacio reservado para los botones
            ) {
                EventListScreen()
            }
        }

        // Botones flotantes
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = { /* Acción configuración */ },
                modifier = Modifier.weight(1f)
            ) {
                Text("Config")
            }
            Button(
                onClick = { /* Acción información */ },
                modifier = Modifier.weight(1f)
            ) {
                Text("Info")
            }
        }
    }
}
