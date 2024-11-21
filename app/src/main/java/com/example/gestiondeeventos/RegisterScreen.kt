package com.example.gestiondeeventos

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun RegisterScreen(navController: NavHostController) {
    var email by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password1 by remember { mutableStateOf("") }
    var password2 by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

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
                Text("REGISTER", color = Color.White, style = MaterialTheme.typography.headlineMedium, fontSize = 24.sp)
                RegisterTextField("Correo electronico", Icons.Default.MailOutline, email) { email = it }
                RegisterTextField("Nombre de usuario", Icons.Default.Person, username) { username = it }

                // Primer campo de contraseña
                RegisterPasswordField("Contraseña", password1, isPasswordVisible) { password1 = it }

                // Segundo campo de contraseña (con "Repeat Password")
                RegisterPasswordField("Repetir contraseña", password2, isPasswordVisible) { password2 = it }

                // Checkbox para mostrar contraseñas
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text("Mostrar contraseñas", color = Color.White)
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

                Button(onClick = { /* Lógica de registro */ }, modifier = Modifier.padding(top = 24.dp)) {
                    Text("Register")
                }
                Text(
                    text = "Volver al Login",
                    color = Color.White,
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .clickable { navController.navigate("login") }
                )
            }
        }
    }
}

@Composable
fun RegisterTextField(label: String, icon: ImageVector, value: String, onValueChange: (String) -> Unit) {
    LoginTextField(label, icon, value, onValueChange)
}

@Composable
fun RegisterPasswordField(label: String, value: String, isPasswordVisible: Boolean, onValueChange: (String) -> Unit) {
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
                textStyle = LocalTextStyle.current.copy(color = Color.Black),
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                decorationBox = { innerTextField ->
                    if (value.isEmpty()) Text(label, color = Color.Gray) // Usar el valor de 'label'
                    innerTextField()
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}