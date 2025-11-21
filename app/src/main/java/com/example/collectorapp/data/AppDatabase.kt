package com.example.collectorapp.data


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.collectorapp.data.dao.ColeccionDao
import com.example.collectorapp.data.dao.ItemDao
import com.example.collectorapp.models.Coleccion
import com.example.collectorapp.models.Item



@Database(
    entities = [ Coleccion::class, Item::class ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun coleccionDao(): ColeccionDao
    abstract fun itemDao(): ItemDao

    companion object {
        @Volatile //asegura visibilidad de campos entre hilos
        private var INSTANCE: AppDatabase? = null

        //obtiene la isntancia de la base de datos
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder( //sincro para que solo un hilo se ejecute
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }


    }


}