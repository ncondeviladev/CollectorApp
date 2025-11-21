package com.example.collectorapp.data


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.collectorapp.data.dao.ColeccionDao
import com.example.collectorapp.data.dao.ItemDao
import com.example.collectorapp.models.Coleccion
import com.example.collectorapp.models.Item
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


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
                )
                    //Datos precargados
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            CoroutineScope(Dispatchers.IO).launch {
                                val coleccionDao = getDatabase(context).coleccionDao()
                                val itemDao = getDatabase(context).itemDao()

                                // Colecciones frikis
                                coleccionDao.insertar(Coleccion(1, "Figuras", "Figuras de comics, videojuegos y películas."))
                                coleccionDao.insertar(Coleccion(2, "Videojuegos", "Videojuegos de PS y Xbox."))
                                coleccionDao.insertar(Coleccion(3, "Cómics y Manga", "Colección de cómics americanos y manga japoneses."))

                                // Items
                                itemDao.insertar(Item(1, "Figura Batman", "Figura de acción de Batman de la serie animada.", "Batman", idColeccion = 1))
                                itemDao.insertar(Item(2, "Figura Spider-Man", "Figura articulada de Spider-Man.", "Figuras", idColeccion = 1))
                                itemDao.insertar(Item(3, "PS5 God of War", "Juego God of War para PS5.", "Videojuegos", idColeccion = 2))
                                itemDao.insertar(Item(4, "Xbox Series X Halo", "Juego Halo Infinite para Xbox Series X.", "Videojuegos", idColeccion = 2))
                                itemDao.insertar(Item(5, "Manga Naruto Vol.1", "Primer volumen del manga Naruto.", "Manga", idColeccion = 3))
                                itemDao.insertar(Item(6, "Comic Batman Año Uno", "Comic clásico de Batman: Año Uno.", "Cómics", idColeccion = 3))
                            }
                        }
                    })

                    .build()
                INSTANCE = instance
                instance
            }
        }


    }


}