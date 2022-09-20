package com.example.empresas.data.data_local

import android.content.Context
import androidx.room.Database
import androidx.room.Room

object DatabaseConfiguration {

    private val databaseInstance: EmpresasDatabase? = null

    fun getDatabaseInstance(context: Context) = databaseInstance ?: createDatabase(context)

    private fun createDatabase(context: Context): EmpresasDatabase {
        synchronized(this) {
            var instance: EmpresasDatabase? = databaseInstance
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context,
                    EmpresasDatabase::class.java,
                    "empresas_database"
                ).build()
            }
            return instance
        }
    }

}