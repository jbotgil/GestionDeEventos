package com.example.gestiondeeventos.configuracion

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver


private lateinit var database: AppDatabase

@Composable
fun ConfigUserScreen(navController: NavController) {
    val context = LocalContext.current
    val driver: SqlDriver = AndroidSqliteDriver(AppDatabase.Schema, context, "app.db")
    val database = AppDatabase(driver)
    val bdQueries = database.bdQueries
    val sharedPreferences = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
    val idUsu = sharedPreferences.getLong("idUsuario", -1)

    var usuario: Usuarios? = null
    database.transaction {
        usuario = bdQueries.GetUsuarioPorId(idUsu).executeAsOneOrNull()
        Log.d(TAG, "ConfigUserScreen: $usuario")
    }

    // Variables para almacenar valores de entrada
    val email = remember { mutableStateOf(usuario!!.mail) }
    val username = remember { mutableStateOf(usuario!!.username) }
    val currentPassword = remember { mutableStateOf(usuario!!.passwd) }
    val newPassword = remember { mutableStateOf("") }

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
            modifier = Modifier.fillMaxSize()
        ) {
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
                    // Texto "Eliminar cuenta" con acción
                    Text(
                        text = "Eliminar cuenta",
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            textDecoration = TextDecoration.Underline
                        ),
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally) // Alinea el texto al centro horizontal
                            .padding(top = 24.dp)
                            .clickable {
                                /* TODO: Mostrar alerta para confirmar/cancelar eliminación */
                            }
                    )
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
                    Toast.makeText(context, "Configuración cancelada", Toast.LENGTH_SHORT).apply {
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
                    // Guardar datos y mostrar un Toast de confirmación
                    /*TODO: LANZAR LOGICA DE GUARDADO DE DATOS DEL USUARIO*/
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
