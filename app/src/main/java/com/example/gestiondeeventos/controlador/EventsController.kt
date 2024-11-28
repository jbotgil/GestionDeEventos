package com.example.gestiondeeventos.controlador

import android.content.Context
import android.widget.Toast
import androidx.navigation.NavController
import com.example.database.AppDatabase
import com.example.database.Eventos
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

class EventsController(private val context: Context) {
    private lateinit var database: AppDatabase

    private fun cargarDataBase(): AppDatabase {
        val driver: SqlDriver = AndroidSqliteDriver(AppDatabase.Schema, context, "app.db")
        return AppDatabase(driver)
    }

    fun registrarEvento(
        titulo: String,
        fecha: String,
        direccion: String,
        latitud: Double,
        longitud: Double,
        navController: NavController
    ) {
        database = cargarDataBase()
        val bdQueries = database.bdQueries
        var validaciones = false
        //Validacion de que hayan datos introducidos
        when {
            titulo.isBlank() -> {
                mostrarToast("El título no puede estar vacío.")
                return
            }

            fecha.isBlank() -> {
                mostrarToast("La fecha no puede estar vacía.")
                return
            }

            direccion.isBlank() -> {
                mostrarToast("La dirección no puede estar vacía.")
                return
            }

            latitud !in -90.0..90.0 -> {
                mostrarToast("La latitud debe ser un valor entre -90 y 90.")
                return
            }

            longitud !in -180.0..180.0 -> {
                mostrarToast("La longitud debe ser un valor entre -180 y 180.")
                return
            }
        }

        //En caso de que todas las validaciones sean correctas volvemos aquí
        database.transaction {
            bdQueries.RegistrarEvento(titulo, fecha, direccion, latitud, longitud)
        }

        mostrarToast("Evento resgistrado exitosamente")
        navController.popBackStack()
    }

    private fun mostrarToast(mensaje: String) {
        Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show()
    }


    fun getEventoPorId(
        idEvento: Long
    ): Eventos? {
        database = cargarDataBase()
        val bdQueries = database.bdQueries
        var evento: Eventos? = null

        database.transaction {
            evento = bdQueries.GetEventoPorId(idEvento).executeAsOneOrNull()
        }
        return evento
    }

    fun getEventos(): Collection<Eventos>? {
        database = cargarDataBase()
        val bdQueries = database.bdQueries
        var listaEventos: Collection<Eventos>? = null

        database.transaction {
            listaEventos = bdQueries.GetEventosOrdenadosPorFecha().executeAsList()
        }
        return listaEventos
    }
}