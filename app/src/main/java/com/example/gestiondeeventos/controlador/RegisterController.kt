package com.example.gestiondeeventos.controlador

import android.content.Context
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

        //TODO: VALIDAR LOS DATOS INTRODUCIDOS

        // Verificar que las contraseñas coincidan
        if (passwd1 == passwd2) {
            database.transaction {
                // Verificar si el usuario ya existe
                usuario = bdQueries.GetUsuarioExistente(username, mail).executeAsOneOrNull()

                if (usuario == null) {
                    // Si no existe, registrar al usuario
                    bdQueries.RegistrarUsuario(username, mail, passwd1, esAdmin)
                    // Recuperar al usuario recién registrado
                    usuario = bdQueries.GetUsuarioPorUsername(username).executeAsOneOrNull()
                }
            }
        }
        return usuario
    }
}