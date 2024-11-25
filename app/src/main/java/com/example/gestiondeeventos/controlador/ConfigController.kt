package com.example.gestiondeeventos.controlador

import android.content.Context
import android.widget.Toast
import androidx.navigation.NavController
import com.example.database.AppDatabase
import com.example.database.Usuarios
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

class ConfigController(private val context: Context) {
   // private lateinit var database: AppDatabase

    private fun cargarDataBase(): AppDatabase {
        val driver: SqlDriver = AndroidSqliteDriver(AppDatabase.Schema, context, "app.db")
        return AppDatabase(driver)
    }

    fun actualizarCorreo(
        usuario: Usuarios
    ): Usuarios? {
        val bd = cargarDataBase()
        val bdQueries = bd.bdQueries

        //Hacemos un update del mail del usuario utilizando
        bd.transaction {
            bdQueries.UpdateMail(usuario.mail, usuario.id_usuario)
        }
        //Devolvemos el usuario con los datos cambiados
        return bdQueries.GetUsuarioPorId(usuario.id_usuario).executeAsOneOrNull()
    }

    fun actualizarUsername(
        usuario: Usuarios
    ): Usuarios? {
        val bd = cargarDataBase()
        val bdQueries = bd.bdQueries

        //Hacemos un update del username del usuario utilizando
        bd.transaction {
            bdQueries.UpdateUser(usuario.username,usuario.id_usuario)
        }
        //Devolvemos el usuario con los datos cambiados
        return bdQueries.GetUsuarioPorId(usuario.id_usuario).executeAsOneOrNull()
    }

    fun actualizarPasswd(
        usuario: Usuarios
    ): Usuarios? {
        val bd = cargarDataBase()
        val bdQueries = bd.bdQueries

        //Hacemos un update del username del usuario utilizando
        bd.transaction {
            bdQueries.UpdateUser(usuario.username,usuario.id_usuario)
        }
        //Devolvemos el usuario con los datos cambiados
        return bdQueries.GetUsuarioPorId(usuario.id_usuario).executeAsOneOrNull()
    }

    fun eliminarCuenta(
        usuario: Usuarios,
        navController: NavController
    ) {
        val bd = cargarDataBase()
        val bdQueries = bd.bdQueries
        var usuarioEliminado: Usuarios? = null

        bd.transaction {
            bdQueries.EliminarUsuario(usuario.id_usuario)
            usuarioEliminado = bdQueries.GetUsuarioPorId(usuario.id_usuario).executeAsOneOrNull()
        }

        if (usuarioEliminado == null){
            Toast.makeText(context, "Usuario eliminado exitosamente", Toast.LENGTH_SHORT).apply {
                setGravity(android.view.Gravity.BOTTOM, 0, 180) // Mueve hacia arriba
            }.show()
            navController.navigate("login") {
                popUpTo("login") { inclusive = false } //Evita volver atras, para que el usuario que se haya eliminado pueda iniciar
                                                             //como un usuario fantasma y ocasione problemas a la app
            }
        }
    }

}