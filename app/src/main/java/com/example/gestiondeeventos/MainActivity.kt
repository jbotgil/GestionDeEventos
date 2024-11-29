package com.example.gestiondeeventos

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gestiondeeventos.configuracion.ConfigUserScreen
import com.example.gestiondeeventos.login.LoginScreen
import com.example.gestiondeeventos.menuPrincipal.administrador.AdminEventScreen
import com.example.gestiondeeventos.menuPrincipal.administrador.CreateEventScreen
import com.example.gestiondeeventos.menuPrincipal.usuario.EventCardInfo
import com.example.gestiondeeventos.menuPrincipal.usuario.UserEventScreen
import com.example.gestiondeeventos.register.RegisterScreen
import com.example.gestiondeeventos.ui.theme.GestionDeEventosTheme

data class UserPreference(
    val name: String,
    val passwd: String,
    val recordarDatos: Boolean,
    val idUsuario: Long
) //En esta clase guardaremos las preferencias del usuario

class MainActivity : ComponentActivity() {

    //Aqui almacenaremos las preferencias de el usuario
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {

        //Inicializar SharedPreferences
        sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE)

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            GestionDeEventosTheme {
                AppNavigation(sharedPreferences)
            }
        }
    }
}

@Composable
fun AppNavigation(sharedPreferences: SharedPreferences) {
    val navController = rememberNavController()
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.fiestabackground),
            contentDescription = "Fondo",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        //Panel de navegacion
        NavHost(navController = navController, startDestination = "login") {
            composable("login") {
                LoginScreen(navController, sharedPreferences)
            }
            composable("register") {
                RegisterScreen(navController)
            }
            composable("userEvents") {
                UserEventScreen(navController)
            }
            composable("adminEvents") {
                AdminEventScreen(navController)
            }
            composable("config") {
                ConfigUserScreen(navController)
            }
            composable("createEventScreen") {
                CreateEventScreen(navController)
            }
            composable("eventCardInfoScreen") {
                EventCardInfo(navController, context)
            }
        }
    }
}