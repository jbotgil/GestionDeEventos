package com.example.gestiondeeventos.menuPrincipal.usuario

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.platform.LocalConfiguration
import androidx.navigation.NavController
import com.example.database.Eventos
import com.example.gestiondeeventos.controlador.EventsController

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

    // Obtenemos el tamaño de la pantalla
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Título en la parte superior
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

            Spacer(modifier = Modifier.height(16.dp)) // Espaciador fijo

            // Mapa que ocupa el 50% de la pantalla
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(screenHeight * 0.5f) // 50% de la altura de la pantalla
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Gray) // Color placeholder para el mapa
            ) {
                Text(
                    text = "Mapa aquí",
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.height(screenHeight * 0.2f)) // Espaciador fijo debajo del mapa

            // Botón "Volver"
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
