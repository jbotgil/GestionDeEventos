package com.example.gestiondeeventos.login

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.database.Usuarios
import com.example.gestiondeeventos.UserPreference
import com.example.gestiondeeventos.controlador.LoginController

@SuppressLint("UnrememberedMutableState", "RememberReturnType")
@Composable
fun LoginScreen(navController: NavHostController, sharedPreferences: SharedPreferences) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var recordarDatos by remember { mutableStateOf(false) }

    // Cargar datos una sola vez al inicio
    remember {
        val userPreferences = loadPersonFromPreferences(sharedPreferences)
        if (userPreferences.recordarDatos) {
            username = userPreferences.name
            password = userPreferences.passwd
            recordarDatos = userPreferences.recordarDatos
        }
    }

    val botonHabilitado by derivedStateOf { username.isNotBlank() && password.isNotBlank() }
    val context = LocalContext.current
    val loginController = LoginController(context)
    var usuario: Usuarios? = null

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
                Text(
                    "LOGIN",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineMedium,
                    fontSize = 24.sp
                )
                LoginTextField("Usuario", Icons.Default.Person, username) { username = it }
                LoginPasswordField(password, { password = it }, recordarDatos) { isChecked ->
                    recordarDatos = isChecked

                    // Limpiar campos y preferencias si "Recordar datos" se desactiva
                    if (!recordarDatos) {
                        clearPreferences(sharedPreferences)
                    }
                }

                Button(
                    onClick = {
                        usuario = loginController.iniciarSesion(username, password)

                        if (usuario == null) { // No existe el usuario
                            Toast.makeText(context, "Error en las credenciales o usuario inexistente.", Toast.LENGTH_SHORT).apply {
                                setGravity(android.view.Gravity.BOTTOM, 0, 180)
                            }.show()
                        } else {
                            // Guardar datos en preferencias si el usuario seleccionó "Recordar datos"
                            savePersonToPreferences(
                                UserPreference(username, password, recordarDatos),
                                sharedPreferences
                            )

                            if (usuario?.esAdmin?.toInt() == 1) {
                                navController.navigate("adminEvents")
                            } else {
                                navController.navigate("userEvents")
                            }
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
                    Text("Login")
                }

                Text(
                    text = "Crear cuenta",
                    textDecoration = TextDecoration.Underline,
                    color = Color.White,
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .clickable { navController.navigate("register") }
                )
            }
        }
    }
}

fun clearPreferences(sharedPreferences: SharedPreferences) {
    val editor = sharedPreferences.edit()
    editor.clear()
    editor.apply()
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
            Spacer(modifier = Modifier.padding(5.dp))
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
fun LoginPasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    recordarDatos: Boolean,
    onRecordarDatosChange: (Boolean) -> Unit
) {
    var isPasswordVisible by remember { mutableStateOf(false) }

    Column {
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
                Spacer(modifier = Modifier.padding(5.dp))
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
                    checkedColor = Color(android.graphics.Color.parseColor("#A12D4A")),
                    uncheckedColor = Color.White,
                    checkmarkColor = Color.White
                )
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(start = 16.dp, top = 4.dp)
                .fillMaxWidth(0.8f)
        ) {
            Text("Recordar datos", color = Color.White)
            Switch(
                checked = recordarDatos,
                onCheckedChange = onRecordarDatosChange,
                modifier = Modifier.padding(start = 70.dp),
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    uncheckedThumbColor = Color.Gray,
                    checkedTrackColor = Color(android.graphics.Color.parseColor("#A12D4A")),
                    uncheckedTrackColor = Color.LightGray
                )
            )
        }
    }
}


fun loadPersonFromPreferences(sharedPreferences: SharedPreferences): UserPreference {
    //Borrar esta línea, leer datos de las prefs y devolver el objeto Person adecuado
    val username = sharedPreferences.getString("username", "defaultName") ?: "defaultName"
    val passwd = sharedPreferences.getString("passwd", "defpasswd")?: "defpasswd" //Esto no es nada seguro pero es un entorno controlado
    val recordarDatos = sharedPreferences.getBoolean("recordarDatos", false)
    return UserPreference(name = username, passwd = passwd, recordarDatos = recordarDatos)
}

fun savePersonToPreferences(user: UserPreference, sharedPreferences: SharedPreferences) {
    //Guardar los datos de person en las prefs
    val editor = sharedPreferences.edit()
    editor.putString("username",user.name)
    editor.putString("passwd",user.passwd)
    editor.putBoolean("recordarDatos", user.recordarDatos)
    editor.apply()
}