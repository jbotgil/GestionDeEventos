package com.example.gestiondeeventos.register

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.database.Usuarios
import com.example.gestiondeeventos.controlador.RegisterController
import com.example.gestiondeeventos.login.LoginTextField

@SuppressLint("UnrememberedMutableState")
@Composable
fun RegisterScreen(navController: NavHostController) {
    var email by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password1 by remember { mutableStateOf("") }
    var password2 by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    val botonHabilitado by derivedStateOf { email.isNotBlank() && username.isNotBlank() && password1.isNotBlank() && password2.isNotBlank() }
    val context = LocalContext.current
    val registerController = RegisterController(context)
    var usuario: Usuarios? = null;

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
                        colors = listOf(
                            Color.Black.copy(alpha = 0.8f),
                            Color.Black.copy(alpha = 0.6f)
                        )
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
                            checkedColor = Color(android.graphics.Color.parseColor("#A12D4A")),      // Color de fondo cuando está marcado
                            uncheckedColor = Color.White,    // Color del borde cuando está desmarcado
                            checkmarkColor = Color.White     // Color de la marca (✓) dentro del checkbox
                        )
                    )
                }

                // Botón de registro con estilo deshabilitado
                Button(
                    onClick = {
                        //TODO: LOGICA DEL REGISTRO
                        usuario = registerController.registrarUsuario(username, email, password1, password2, 0)
                        if (usuario != null){
                            Toast.makeText(context, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show()
                            navController.navigate("login")
                        }
                    },
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .fillMaxWidth(0.8f),
                    enabled = botonHabilitado,
                    colors = ButtonDefaults.buttonColors(
                        disabledContainerColor = Color.Gray.copy(alpha = 0.5f),
                        disabledContentColor = Color.White.copy(alpha = 0.5f),
                        containerColor = Color(android.graphics.Color.parseColor("#A12D4A")),
                        contentColor = Color.White
                    )
                ) {
                    Text("Register")
                }

                Text(
                    text = "Volver al Login",
                    textDecoration = TextDecoration.Underline,
                    color = Color.White,
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .clickable {
                            navController.navigate("login"){
                                popUpTo("login") { inclusive = false } //Linea para no poder retroceder hacia atrás
                                                                             //al pulsar el boton de retroceso en el movil
                            }
                        }
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
