package com.example.gestiondeeventos.menuPrincipal.administrador

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun AdminEventScreen(navController: NavController) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.6f))
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
                    .weight(1.3f) // Proporción del espacio vertical
                    .fillMaxSize(),
                contentAlignment = Alignment.Center // Centra el texto vertical y horizontalmente
            ) {
                Text(
                    text = "ADMIN PANEL",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineLarge // Estilo del texto
                )
            }

            // Lista de eventos con espacio reservado para botones
            Box(
                modifier = Modifier
                    .weight(6f) // Ajusta el peso restante para la lista de eventos
                    .padding(bottom = 50.dp) // Espacio reservado para los botones
            ) {
                AdminEventListScreen()
            }
        }

        // Botones flotantes
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
                .padding(bottom = 20.dp)
                .padding(horizontal = 10.dp)
                .fillMaxWidth() ,
            horizontalArrangement = Arrangement.SpaceBetween, // Distribuir los botones a los extremos
        ) {
            Button(
                onClick = {
                    navController.navigate("config")
                },
                modifier = Modifier
                    .size(80.dp) // Tamaño fijo para que sea redondo
                    .clip(CircleShape),
                contentPadding = PaddingValues(0.dp), // Sin padding para ajustarse al círculo
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(android.graphics.Color.parseColor("#A12D4A")),
                )
            ) {
                Icon(
                    Icons.Default.Settings,
                    contentDescription = "Configuración",
                    modifier = Modifier
                        .size(70.dp)
                )
            }
            Button(
                onClick = {
                   navController.navigate("createEventScreen")
                },
                modifier = Modifier
                    .size(80.dp) // Tamaño fijo para que sea redondo
                    .clip(CircleShape),
                contentPadding = PaddingValues(0.dp), // Sin padding para ajustarse al círculo
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(android.graphics.Color.parseColor("#A12D4A")),
                )
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Crear Evento",
                    modifier = Modifier
                        .size(70.dp)
                )
            }
        }
    }
}