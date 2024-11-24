package com.example.gestiondeeventos.controlador

import android.content.Context
import android.widget.Toast
import com.example.database.AppDatabase
import com.example.database.Usuarios
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

class RegisterController(private val context: Context) {

    private lateinit var database: AppDatabase

    fun registrarUsuario(
        username: String,
        mail: String,
        passwd1: String,
        passwd2: String,
        esAdmin: Long
    ): Usuarios? {
        val driver: SqlDriver = AndroidSqliteDriver(AppDatabase.Schema, context, "app.db")
        database = AppDatabase(driver)
        val bdQueries = database.bdQueries
        var usuario: Usuarios? = null

        // Validar el nombre de usuario
        if (username.isBlank()) {
            Toast.makeText(context, "El nombre de usuario no puede estar vacío", Toast.LENGTH_SHORT).show()
            return null
        }

        if (username.contains(" ")) {
            Toast.makeText(context, "El nombre de usuario no puede contener espacios", Toast.LENGTH_SHORT).show()
            return null
        }

        // Validar el correo electrónico con una expresión regular
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
        if (!mail.matches(emailRegex)) {
            Toast.makeText(context, "Correo electrónico no válido", Toast.LENGTH_SHORT).show()
            return null
        }

        // Validar las contraseñas
        if (passwd1.isBlank() || passwd2.isBlank()) {
            Toast.makeText(context, "Las contraseñas no pueden estar vacías", Toast.LENGTH_SHORT).show()
            return null
        }

        if (passwd1 != passwd2) {
            Toast.makeText(context, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            return null
        }

        database.transaction {
            // Verificar si el usuario ya existe
            usuario = bdQueries.GetUsuarioExistente(username, mail).executeAsOneOrNull()

            if (usuario == null) {
                // Si no existe, registrar al usuario
                bdQueries.RegistrarUsuario(username, mail, passwd1, esAdmin)
                // Recuperar al usuario recién registrado
                usuario = bdQueries.GetUsuarioPorUsername(username).executeAsOneOrNull()
            } else {
                Toast.makeText(context, "El usuario ya existe", Toast.LENGTH_SHORT).show()
            }
        }
        return usuario
    }
}
