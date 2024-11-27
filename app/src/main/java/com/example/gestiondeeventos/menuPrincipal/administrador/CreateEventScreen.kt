package com.example.gestiondeeventos.menuPrincipal.administrador

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.database.AppDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import java.util.Calendar

lateinit var database: AppDatabase

@Composable
fun CreateEventScreen(navController: NavController) {
    val context = LocalContext.current
    val driver: SqlDriver = AndroidSqliteDriver(AppDatabase.Schema, context, "app.db")
    database = AppDatabase(driver)

    var tituloEvento by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var latitud by remember { mutableStateOf("") }
    var longitud by remember { mutableStateOf("") }

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
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "CREAR EVENTO",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineLarge
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Text("Título", color = Color.White)
                InputField(
                    label = "Título del evento",
                    icon = Icons.Default.KeyboardArrowUp,
                    value = tituloEvento,
                    onValueChange = { tituloEvento = it }
                )

                Text("Fecha", color = Color.White)
                SeleccionarFecha(
                    label = "Selecciona la fecha del evento",
                    icon = Icons.Default.DateRange,
                    selectedDate = fecha,
                    onDateSelected = { fecha = it },
                    context = context
                )

                Text("Dirección", color = Color.White)
                InputField(
                    label = "Dirección",
                    icon = Icons.Default.LocationOn,
                    value = direccion,
                    onValueChange = { direccion = it }
                )

                Text("Latitud", color = Color.White)
                InputField(
                    label = "Latitud del lugar",
                    icon = Icons.Default.KeyboardArrowUp,
                    value = latitud,
                    onValueChange = { latitud = it }
                )

                Text("Longitud", color = Color.White)
                InputField(
                    label = "Longitud del lugar",
                    icon = Icons.Default.KeyboardArrowUp,
                    value = longitud,
                    onValueChange = { longitud = it }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { navController.popBackStack() },
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
                    "Cancelar",
                    style = TextStyle(color = Color.White)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = {
                    // Lógica para guardar el evento en la base de datos
                    // database.insertEvent(tituloEvento, fecha, direccion, latitud.toDoubleOrNull(), longitud.toDoubleOrNull())
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
                    "Guardar",
                    style = TextStyle(color = Color.White)
                )
            }
        }
    }
}

@Composable
fun InputField(label: String, icon: ImageVector, value: String, onValueChange: (String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(8.dp))
            .padding(8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = icon, contentDescription = null, tint = Color.Black)
            Spacer(modifier = Modifier.width(8.dp))
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                textStyle = TextStyle(color = Color.Black),
                decorationBox = { innerTextField ->
                    if (value.isEmpty()) Text(label, color = Color.Gray)
                    innerTextField()
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun SeleccionarFecha(
    label: String,
    icon: ImageVector,
    selectedDate: String,
    onDateSelected: (String) -> Unit,
    context: Context
) {
    val calendario = Calendar.getInstance()
    val year = calendario.get(Calendar.YEAR)
    val month = calendario.get(Calendar.MONTH)
    val day = calendario.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = android.app.DatePickerDialog(
        context,
        { _, anio, mes, dia ->
            val formattedDate = "$dia/${mes + 1}/$anio"
            onDateSelected(formattedDate)
        },
        year,
        month,
        day
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(8.dp))
            .padding(8.dp)
            .clickable { datePickerDialog.show() } // Abre el diálogo al hacer clic
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = Color.Black)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = if (selectedDate.isEmpty()) label else selectedDate,
                style = TextStyle(color = if (selectedDate.isEmpty()) Color.Gray else Color.Black)
            )
        }
    }
}