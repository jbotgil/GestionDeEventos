package com.example.gestiondeeventos.menuPrincipal.administrador

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.database.AppDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

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

    val scrollState = rememberScrollState()

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
                .verticalScroll(scrollState) // Hacer que el contenido sea desplazable
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
                    icon = Icons.Default.Email,
                    value = tituloEvento,
                    onValueChange = { tituloEvento = it }
                )

                Text("Fecha", color = Color.White)
                InputField(
                    label = "DD/MM/YYYY",
                    icon = Icons.Default.Person,
                    value = fecha,
                    onValueChange = { fecha = it }
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

                Text("Imagen", color = Color.White)
                ImageInputField(
                    label = "Seleccionar una imagen",
                    icon = Icons.Default.Search
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
                onClick = { /* TODO: Implementar guardar evento */ },
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
fun ImageInputField(label: String, icon: ImageVector) {
    val context = LocalContext.current
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            selectedImageUri = uri
            Toast.makeText(context, "Imagen seleccionada: $uri", Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { launcher.launch("image/*") },
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Campo para seleccionar la imagen
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(8.dp))
                .padding(8.dp)
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = Color.Black)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = label, color = Color.Gray)
        }

        // Mostrar la imagen seleccionada
        selectedImageUri?.let { uri ->
            Image(
                painter = rememberAsyncImagePainter(model = uri),
                contentDescription = "Vista previa de la imagen seleccionada",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Gray)
            )
        }
    }
}

