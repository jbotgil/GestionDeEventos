package com.example.gestiondeeventos.controlador

import android.content.Context
import com.example.database.AppDatabase
import com.example.database.Usuarios
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

class RegisterController(private val context: Context) {

    private lateinit var database: AppDatabase

    fun registrarUsuario (
        username: String,
        mail: String,
        passwd1: String,
        passwd2: String,
        esAdmin: Long
    ) : Usuarios?
    {
        //Abrir la base de datos SQLDelight, obtener el objeto "database"
        val driver: SqlDriver = AndroidSqliteDriver(AppDatabase.Schema, context, "app.db")
        database = AppDatabase(driver)
        val bdQueries = database.bdQueries
        var usuario: Usuarios? = null;

        //Si las contraseñas coinciden se creará el usuario, en caso contrario
        if (passwd1 == passwd2) {
            //Creamos una transaction de la db
            database.transaction {
                //Comprobamos si ya hay un usuario con esos datos en la base de datos
                usuario = bdQueries.GetUsuarioExistente(username,mail,passwd1).executeAsOneOrNull()
            }
        }
        return usuario;
    }
}