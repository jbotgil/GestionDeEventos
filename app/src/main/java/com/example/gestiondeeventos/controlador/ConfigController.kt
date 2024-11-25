package com.example.gestiondeeventos.controlador

import android.content.Context
import com.example.database.AppDatabase
import com.example.database.Usuarios
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

class ConfigController(private val context: Context) {
    private lateinit var database: AppDatabase

    fun iniciarSesion ( //Si devuelve null significa que no existe el usuario para logearse
        username: String,
        passwd: String
    ) : Usuarios?
    {
        val driver: SqlDriver = AndroidSqliteDriver(AppDatabase.Schema, context, "app.db")
        database = AppDatabase(driver)
        val bdQueries = database.bdQueries
        var usuario: Usuarios? = null

        return null
    }
}