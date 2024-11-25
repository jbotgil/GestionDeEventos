package com.example.gestiondeeventos.controlador

import android.content.Context
import com.example.database.AppDatabase
import com.example.database.Usuarios
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

class ConfigController(private val context: Context) {
    private lateinit var database: AppDatabase

    private fun cargarDataBase(): AppDatabase {
        val driver: SqlDriver = AndroidSqliteDriver(AppDatabase.Schema, context, "app.db")
        return AppDatabase(driver)
    }

    fun actualizarCorreo(usuario: Usuarios): Usuarios? {
        database = cargarDataBase()
        val bdQueries = database.bdQueries

        database.transaction {
            bdQueries.UpdateMail(usuario.mail, usuario.id_usuario)
        }

        return bdQueries.GetUsuarioPorId(usuario.id_usuario).executeAsOneOrNull()
    }

    fun actualizarUsername(usuario: Usuarios): Usuarios? {
        database = cargarDataBase()
        val bdQueries = database.bdQueries

        database.transaction {
            bdQueries.UpdateUser(usuario.username, usuario.id_usuario)
        }

        return bdQueries.GetUsuarioPorId(usuario.id_usuario).executeAsOneOrNull()
    }

    fun actualizarPasswd(usuario: Usuarios): Usuarios? {
        database = cargarDataBase()
        val bdQueries = database.bdQueries

        database.transaction {
            bdQueries.UpdatePassword(usuario.passwd, usuario.id_usuario)
        }

        return bdQueries.GetUsuarioPorId(usuario.id_usuario).executeAsOneOrNull()
    }

    fun eliminarCuenta(usuario: Usuarios) {
        database = cargarDataBase()
        val bdQueries = database.bdQueries

        database.transaction {
            bdQueries.EliminarUsuario(usuario.id_usuario)
        }

        //val usuarioEliminado = bdQueries.GetUsuarioPorId(usuario.id_usuario).executeAsOneOrNull()
    }
}



