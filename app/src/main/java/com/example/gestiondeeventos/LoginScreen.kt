package com.example.gestiondeeventos

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun LoginScreen(navController: NavHostController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Black.copy(alpha = 0.8f), Color.Black.copy(alpha = 0.6f))
                    ),
                    shape = MaterialTheme.shapes.medium
                )
                .padding(24.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("LOGIN", color = Color.White, style = MaterialTheme.typography.headlineMedium, fontSize = 24.sp)
                LoginTextField("Usuario", Icons.Default.Person, username) { username = it }
                LoginPasswordField(password) { password = it }
                Button(onClick = { /* Lógica de inicio de sesión */ }, modifier = Modifier.padding(top = 24.dp)) {
                    Text("Login")
                }
                Text(
                    text = "Crear cuenta",
                    color = Color.White,
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .clickable { navController.navigate("register") }
                )
            }
        }
    }
}

@Composable
fun LoginTextField(label: String, icon: ImageVector, value: String, onValueChange: (String) -> Unit) {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(0.8f)
            .background(color = Color.LightGray, shape = MaterialTheme.shapes.medium)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = icon, contentDescription = null, tint = Color.Black)
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
fun LoginPasswordField(value: String, onValueChange: (String) -> Unit) {
    var isPasswordVisible by remember { mutableStateOf(false) }

    Column {
        // Campo de entrada de contraseña
        Box(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(0.8f)
                .background(
                    color = Color.LightGray,
                    shape = MaterialTheme.shapes.medium
                )
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = null,
                    tint = Color.Black
                )
                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    singleLine = true,
                    textStyle = TextStyle(color = Color.Black),
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    decorationBox = { innerTextField ->
                        if (value.isEmpty()) Text("Contraseña", color = Color.Gray)
                        innerTextField()
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        // Fila para el texto y el checkbox
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(start = 16.dp, top = 4.dp)
                .fillMaxWidth(0.8f)
        ) {
            Text("Mostrar contraseña", color = Color.White, modifier = Modifier.weight(1f))
            Checkbox(
                checked = isPasswordVisible,
                onCheckedChange = { isPasswordVisible = it },
                colors = CheckboxDefaults.colors(
                    checkedColor = Color.White,      // Color de fondo cuando está marcado
                    uncheckedColor = Color.White,    // Color del borde cuando está desmarcado
                    checkmarkColor = Color.Black     // Color de la marca (✓) dentro del checkbox
                )
            )
        }
    }
}
