package com.example.gestiondeeventos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gestiondeeventos.ui.theme.GestionDeEventosTheme
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import com.example.database.AppDatabase

class MainActivity : ComponentActivity() {

    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        // Inicializa la base de datos
        val driver: SqlDriver = AndroidSqliteDriver(AppDatabase.Schema, this, "app.db")
        database = AppDatabase(driver)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Habilita modo edge-to-edge para pantallas modernas
        setContent {
            GestionDeEventosTheme {
                AppNavigation()
            }
        }
    }
}

// Sistema de navegación con fondo persistente
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    Box(modifier = Modifier.fillMaxSize()) {
        // Imagen de fondo persistente
        Image(
            painter = painterResource(id = R.drawable.fiestabackground),
            contentDescription = "Fondo",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Navegación principal
        NavHost(navController = navController, startDestination = "login") {
            composable("login") {
                InicioSesionBox(navController)
            }
            composable("register") {
                RegistroBox(navController)
            }
        }
    }
}

// Pantalla de inicio de sesión
@Composable
fun InicioSesionBox(navController: NavHostController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        // Caja difuminada para el contenido
        Box(
            modifier = Modifier
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.8f),
                            Color.Black.copy(alpha = 0.6f)
                        )
                    ),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(24.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "LOGIN",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineMedium,
                    fontSize = 24.sp
                )
                //UsernameInput
                CustomTextField(
                    name= "Username",
                    value = username,
                    icon = Icons.Default.Person,
                    onValueChange = { username = it }
                )
                //PasswordInput
                PasswordTextField(
                    value = password,
                    onValueChange = { password = it }
                )

                Button(
                    onClick = { /* Lógica de inicio de sesión */ },
                    modifier = Modifier.padding(top = 24.dp)
                ) {
                    Text("Login")
                }
                // Navega a la pantalla de registro
                Text(
                    text = "Create Account",
                    color = Color.White,
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .clickable { navController.navigate("register") }
                )
            }
        }
    }
}

// Pantalla de registro
@Composable
fun RegistroBox(navController: NavHostController) {
    var email by remember { mutableStateOf("") }
    var password1 by remember { mutableStateOf("") }
    var password2 by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var passwordCoincide by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        // Caja difuminada para el contenido
        Box(
            modifier = Modifier
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.8f),
                            Color.Black.copy(alpha = 0.6f)
                        )
                    ),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(24.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "REGISTER",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineMedium,
                    fontSize = 24.sp
                )
                //EmailInput
                CustomTextField(
                    name = "Mail",
                    icon = Icons.Default.MailOutline,
                    value = email,
                    onValueChange = { email = it }
                )
                //UsernameInput
                CustomTextField(
                    name = "Username",
                    icon = Icons.Default.Person,
                    value = username,
                    onValueChange = { username = it }
                )
                //PasswdInput 1
                PasswordTextField(
                    value = password1,
                    onValueChange = { password1 = it}
                )
                //PasswdInput 2
                PasswordTextField(
                    value = password2,
                    onValueChange = { password2 = it}
                )

                Button(
                    onClick = { /* Lógica de registro */ },
                    modifier = Modifier.padding(top = 24.dp)
                ) {
                    Text("Register")
                }
                // Navega de vuelta a la pantalla de inicio de sesión
                Text(
                    text = "Back to Login",
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
fun CustomTextField(
    name: String,
    icon: ImageVector,
    value: String,
    onValueChange: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(0.8f)
            .background(
                color = Color.LightGray,
                shape = RoundedCornerShape(50.dp) // Bordes redondeados
            )
            .padding(horizontal = 16.dp, vertical = 8.dp) // Espaciado interno
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                contentDescription = icon.toString(),
                tint = Color.Black,
                modifier = Modifier.padding(end = 8.dp)
            )

            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp // Tamaño del texto
                ),
                decorationBox = { innerTextField ->
                    if (value.isEmpty()) {
                        Text(
                            text = name, // Placeholder
                            color = Color.Gray,
                            fontSize = 16.sp
                        )
                    }
                    innerTextField()
                },
                modifier = Modifier.fillMaxWidth() // Ajusta el ancho restante
            )
        }
    }
}

@Composable
fun PasswordTextField(
    value: String,
    onValueChange: (String) -> Unit
) {
    var isPasswordVisible by remember { mutableStateOf(false) } // Estado para mostrar/ocultar la contraseña

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(0.8f)
    ) {
        // BasicTextField para la contraseña
        Box(
            modifier = Modifier
                .background(
                    color = Color.LightGray,
                    shape = RoundedCornerShape(50.dp) // Bordes redondeados
                )
                .padding(horizontal = 16.dp, vertical = 8.dp) // Espaciado interno
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Icono de la contraseña
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Lock Icon",
                    tint = Color.Black,
                    modifier = Modifier.padding(end = 8.dp)
                )

                // BasicTextField para la contraseña
                BasicTextField(
                    value = value, // Muestra la contraseña real
                    onValueChange = onValueChange, // Mantener la lógica de cambio
                    singleLine = true,
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    textStyle = TextStyle(
                        color = Color.Black,
                        fontSize = 16.sp // Tamaño del texto
                    ),
                    decorationBox = { innerTextField ->
                        if (value.isEmpty()) {
                            Text(
                                text = "Password", // Placeholder
                                color = Color.Gray,
                                fontSize = 16.sp
                            )
                        }
                        innerTextField() // Aquí es donde se dibuja el texto del usuario
                    },
                    modifier = Modifier.fillMaxWidth() // Ajusta el ancho restante
                )
            }
        }

        // Opciones para mostrar la contraseña
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(0.8f) // Ancho del Row al 80%
        ) {
            Text("Mostrar contraseña", color = Color.White)
            Checkbox(
                checked = isPasswordVisible,
                onCheckedChange = { isPasswordVisible = it },
                modifier = Modifier.padding(end = 8.dp).padding(start = 70.dp)
            )
        }
    }
}






