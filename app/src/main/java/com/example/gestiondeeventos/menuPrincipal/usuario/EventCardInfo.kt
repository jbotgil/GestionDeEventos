package com.example.gestiondeeventos.menuPrincipal.usuario

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


@Composable
fun EventCardInfo(navController: NavController, context: Context) {
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
                    text = "EVENTOS",
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

            }
        }

        // Botones flotantes
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
                .padding(bottom = 20.dp)
                .padding(horizontal = 10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween // Distribuir los botones a los extremos
        ) {



            Button(
                onClick = {
                    Toast.makeText(context, "Saliendo...", Toast.LENGTH_SHORT).apply {
                        setGravity(android.view.Gravity.BOTTOM, 0, 180)
                    }.show()
                    navController.popBackStack()
                },
                modifier = Modifier
                    .height(50.dp)
                    .weight(1f)
                    .clip(RoundedCornerShape(10.dp)),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(android.graphics.Color.parseColor("#A12D4A"))
                )
            ) {
                Text(
                    "Volver",
                    style = TextStyle(color = Color.White)
                )
            }
        }
    }
}