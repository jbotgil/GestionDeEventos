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

   fun getEventos(): List<Eventos> {
       database = cargarDataBase()
       val bdQueries = database.bdQueries
       var listaEventos: List<Eventos> = emptyList()

       // Recuperamos los eventos con la consulta SQL ordenada
       database.transaction {
           listaEventos = bdQueries.GetEventosOrdenadosPorFecha().executeAsList()
       }

       // Aseguramos que la lista esté ordenada por año, mes y día
       listaEventos = listaEventos.sortedWith { evento1, evento2 ->
           val partesFecha1 = evento1.fecha.split("/")
           val partesFecha2 = evento2.fecha.split("/")

           val dia1 = partesFecha1[0].toInt()
           val mes1 = partesFecha1[1].toInt()
           val anio1 = partesFecha1[2].toInt()

           val dia2 = partesFecha2[0].toInt()
           val mes2 = partesFecha2[1].toInt()
           val anio2 = partesFecha2[2].toInt()

           // Comparar primero por año, luego por mes, luego por día
           when {
               anio1 != anio2 -> anio1.compareTo(anio2)
               mes1 != mes2 -> mes1.compareTo(mes2)
               else -> dia1.compareTo(dia2)
           }
       }

       return listaEventos
   }




    fun deleteEvento(idEvento: Long) {
        database = cargarDataBase()
        val bdQueries = database.bdQueries

        database.transaction {
            bdQueries.EliminarEvento(idEvento)
        }
    }
}