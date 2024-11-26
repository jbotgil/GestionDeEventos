package com.example.gestiondeeventos.configuracion

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.database.AppDatabase
import com.example.database.Usuarios
import com.example.gestiondeeventos.controlador.ConfigController
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver


lateinit var database: AppDatabase

@Composable
fun ConfigUserScreen(navController: NavController) {
    val context = LocalContext.current
    val driver: SqlDriver = AndroidSqliteDriver(AppDatabase.Schema, context, "app.db")
    database = AppDatabase(driver)
    val bdQueries = database.bdQueries
    val sharedPreferences = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
    val idUsu = sharedPreferences.getLong("idUsuario", -1)
    var showDeleteDialog by remember { mutableStateOf(false) }

    var usuario: Usuarios? = null
    database.transaction {
        usuario = bdQueries.GetUsuarioPorId(idUsu).executeAsOneOrNull()
    }

    if (usuario == null) {
        Toast.makeText(context, "Error al cargar usuario", Toast.LENGTH_SHORT).show()
        return
    }

    // Variables para almacenar valores de entrada
    val email = remember { mutableStateOf(usuario!!.mail) }
    val username = remember { mutableStateOf(usuario!!.username) }
    val currentPassword = remember { mutableStateOf("") }
    val newPassword = remember { mutableStateOf("") }
    val configController = ConfigController(context)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.6f))
                )
            )
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .weight(1.3f)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "CONFIGURACIÓN",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineLarge
                )
            }

            Box(
                modifier = Modifier
                    .weight(6f)
                    .padding(bottom = 50.dp)
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    Text(
                        text = "Modificar datos de usuario",
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier
                            .padding(top = 16.dp, bottom = 24.dp)
                            .align(Alignment.CenterHorizontally)
                    )

                    // Campos de entrada
                    Text("Correo electrónico", color = Color.White)
                    InputField(
                        label = "Correo electrónico actual",
                        icon = Icons.Default.Email,
                        value = email.value,
                        onValueChange = { email.value = it }
                    )

                    Text("Usuario", color = Color.White)
                    InputField(
                        label = "Usuario actual",
                        icon = Icons.Default.Person,
                        value = username.value,
                        onValueChange = { username.value = it }
                    )

                    Text("Contraseña actual", color = Color.White)
                    PasswordField(
                        label = "Contraseña actual",
                        value = currentPassword.value,
                        onValueChange = { currentPassword.value = it }
                    )

                    Text("Contraseña nueva", color = Color.White)
                    PasswordField(
                        label = "Contraseña nueva",
                        value = newPassword.value,
                        onValueChange = { newPassword.value = it }
                    )

                    Text(
                        text = "Eliminar cuenta",
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            textDecoration = TextDecoration.Underline
                        ),
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 24.dp)
                            .clickable {
                                showDeleteDialog = true
                            }
                    )

                    if (showDeleteDialog) {
                        AlertDialog(
                            onDismissRequest = { showDeleteDialog = false },
                            title = {
                                Text(text = "Confirmar eliminación")
                            },
                            text = {
                                Text("¿Estás seguro de que deseas eliminar tu cuenta? Esta acción es irreversible.")
                            },
                            confirmButton = {
                                TextButton(
                                    onClick = {
                                        showDeleteDialog = false
                                        // Lógica para eliminar la cuenta del usuario
                                        configController.eliminarCuenta(usuario!!)
                                        usuario = null
                                        email.value = ""
                                        username.value = ""
                                        currentPassword.value = ""
                                        newPassword.value = ""
                                        Toast.makeText(context, "Cuenta eliminada exitosamente", Toast.LENGTH_SHORT).apply {
                                            setGravity(android.view.Gravity.BOTTOM, 0, 180)
                                        }.show()
                                        navController.navigate("login") {
                                            popUpTo("login") { inclusive = true }
                                        }
                                    }
                                ) {
                                    Text("Eliminar", color = Color.Red)
                                }
                            },
                            dismissButton = {
                                TextButton(
                                    onClick = { showDeleteDialog = false }
                                ) {
                                    Text("Cancelar")
                                }
                            }
                        )
                    }

                }
            }
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
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
                    "Cancelar",
                    style = TextStyle(color = Color.White)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = {
                    /* Actualización de mail */
                    if (usuario!!.mail != email.value) {
                        usuario = usuario!!.copy(mail = email.value) // Actualizamos localmente
                        usuario = configController.actualizarCorreo(usuario!!)
                        Toast.makeText(context, "Correo actualizado exitosamente", Toast.LENGTH_SHORT).apply {
                            setGravity(android.view.Gravity.BOTTOM, 0, 180)
                        }.show()
                        navController.popBackStack()
                    }
                    /* Actualización de usuario */
                    if(usuario!!.username != username.value) {
                      usuario = usuario!!.copy(username = username.value) // Actualizamos localmente
                      usuario = configController.actualizarUsername(usuario!!)
                      Toast.makeText(context, "Usuario actualizado exitosamente", Toast.LENGTH_SHORT).apply {
                          setGravity(android.view.Gravity.BOTTOM, 0, 180)
                      }.show()
                      navController.popBackStack()
                    }
                    /* Actualización de contraseña */

                    if(currentPassword.value.isNotBlank() && newPassword.value.isNotBlank()) {
                        if (usuario!!.passwd == currentPassword.value ){
                            if(usuario!!.passwd != newPassword.value) {
                                usuario = usuario!!.copy(passwd = newPassword.value) // Actualizamos localmente
                                usuario = configController.actualizarPasswd(usuario!!)
                                Toast.makeText(context, "Contraseña actualizada exitosamente", Toast.LENGTH_SHORT).apply {
                                    setGravity(android.view.Gravity.BOTTOM, 0, 180)
                                }.show()
                                navController.popBackStack()
                            }
                        } else {
                            Toast.makeText(context, "Contraseña incorrecta", Toast.LENGTH_SHORT).apply {
                                setGravity(android.view.Gravity.BOTTOM, 0, 180)
                            }.show()
                        }
                    }
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
            .background(Color.LightGray, RoundedCornerShape(8.dp))
            .padding(8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = icon, contentDescription = null, tint = Color.Black)
            Spacer(modifier = Modifier.width(8.dp))
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                textStyle = LocalTextStyle.current.copy(color = Color.Black),
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
fun PasswordField(label: String, value: String, onValueChange: (String) -> Unit) {
    InputField(label = label, icon = Icons.Default.Lock, value = value, onValueChange = onValueChange)
}