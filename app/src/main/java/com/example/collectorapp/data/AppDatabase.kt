package com.example.collectorapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.collectorapp.data.dao.ColeccionDao
import com.example.collectorapp.data.dao.ItemDao
import com.example.collectorapp.models.Coleccion
import com.example.collectorapp.models.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import java.util.concurrent.Executors

@Database(
    entities = [Coleccion::class, Item::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun coleccionDao(): ColeccionDao
    abstract fun itemDao(): ItemDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            Executors.newSingleThreadExecutor().execute {
                                INSTANCE?.let { database ->
                                    runBlocking(Dispatchers.IO) {
                                        val coleccionDao = database.coleccionDao()
                                        val itemDao = database.itemDao()

                                        // --- COLECCIONES ---
                                        coleccionDao.insertar(Coleccion(1, "Figuras de Acción", "Figuras de comics, videojuegos y películas.", null))
                                        coleccionDao.insertar(Coleccion(2, "Videojuegos", "Formato físico y digital.", null))
                                        coleccionDao.insertar(Coleccion(3, "Cómics y Manga", "Tomos únicos y sagas completas.", null))
                                        coleccionDao.insertar(Coleccion(4, "Funkos", "Figuras Funko Pop de varias franquicias.", null))
                                        coleccionDao.insertar(Coleccion(5, "Lego", "Sets de construcción Lego.", null))
                                        coleccionDao.insertar(Coleccion(6, "Juegos de Mesa", "Juegos de estrategia, rol y cartas.", null))
                                        coleccionDao.insertar(Coleccion(7, "Monedas y Billetes", "Numismática de todo el mundo.", null))
                                        coleccionDao.insertar(Coleccion(8, "Sellos", "Filatelia de diferentes épocas y países.", null))
                                        coleccionDao.insertar(Coleccion(9, "Discos de Vinilo", "Álbumes de música de todos los géneros.", null))
                                        coleccionDao.insertar(Coleccion(10, "Cartas Coleccionables", "Magic, Pokémon, Yu-Gi-Oh!, etc.", null))


                                        // --- ITEMS ---
                                        // Figuras de Acción
                                        itemDao.insertar(Item(nombre = "Figura Batman", descripcion = "Figura de acción de Batman de la serie animada.", categoria = "DC Comics", idColeccion = 1))
                                        itemDao.insertar(Item(nombre = "Figura Spider-Man", descripcion = "Figura de acción de Spider-Man de Marvel Legends.", categoria = "Marvel", idColeccion = 1))
                                        itemDao.insertar(Item(nombre = "Son Goku Super Saiyan", descripcion = "SH Figuarts de Bandai.", categoria = "Dragon Ball", idColeccion = 1))

                                        // Videojuegos
                                        itemDao.insertar(Item(nombre = "The Legend of Zelda: Tears of the Kingdom", descripcion = "Edición coleccionista.", categoria = "Nintendo Switch", idColeccion = 2))
                                        itemDao.insertar(Item(nombre = "Elden Ring", descripcion = "Launch Edition para PS5.", categoria = "PlayStation 5", idColeccion = 2))
                                        itemDao.insertar(Item(nombre = "Cyberpunk 2077", descripcion = "Edición Day One para PC.", categoria = "PC", idColeccion = 2))

                                        // Cómics y Manga
                                        itemDao.insertar(Item(nombre = "Berserk Vol. 1", descripcion = "Primer tomo del manga de Kentaro Miura.", categoria = "Manga", idColeccion = 3))
                                        itemDao.insertar(Item(nombre = "One Piece Vol. 100", descripcion = "Tomo 100 del manga de Eiichiro Oda.", categoria = "Manga", idColeccion = 3))
                                        itemDao.insertar(Item(nombre = "Watchmen", descripcion = "Novela gráfica de Alan Moore.", categoria = "Cómic USA", idColeccion = 3))

                                        // Funkos
                                        itemDao.insertar(Item(nombre = "Funko Pop! Groot", descripcion = "Groot de Guardianes de la Galaxia.", categoria = "Marvel", idColeccion = 4))
                                        itemDao.insertar(Item(nombre = "Funko Pop! Darth Vader", descripcion = "Lord Vader de Star Wars.", categoria = "Star Wars", idColeccion = 4))
                                        itemDao.insertar(Item(nombre = "Funko Pop! Michael Scott", descripcion = "De la serie The Office.", categoria = "TV", idColeccion = 4))

                                        // Lego
                                        itemDao.insertar(Item(nombre = "LEGO Halcón Milenario", descripcion = "Set de construcción de la nave de Star Wars.", categoria = "Star Wars", idColeccion = 5))
                                        itemDao.insertar(Item(nombre = "LEGO Castillo de Hogwarts", descripcion = "El gran comedor y más.", categoria = "Harry Potter", idColeccion = 5))
                                        itemDao.insertar(Item(nombre = "LEGO Bugatti Chiron", descripcion = "Modelo a escala Technic.", categoria = "Technic", idColeccion = 5))

                                        // Juegos de Mesa
                                        itemDao.insertar(Item(nombre = "Catan", descripcion = "Juego de mesa de colonización.", categoria = "Estrategia", idColeccion = 6))
                                        itemDao.insertar(Item(nombre = "Dungeons & Dragons", descripcion = "Kit de inicio, 5ª Edición.", categoria = "Rol", idColeccion = 6))
                                        itemDao.insertar(Item(nombre = "Gloomhaven", descripcion = "Juego cooperativo de fantasía.", categoria = "Cooperativo", idColeccion = 6))

                                        // Monedas y Billetes
                                        itemDao.insertar(Item(nombre = "Dólar de plata Morgan", descripcion = "Moneda de 1887, ceca de Filadelfia.", categoria = "EE.UU.", idColeccion = 7))
                                        itemDao.insertar(Item(nombre = "Billete de 500 Pesetas", descripcion = "Emitido en 1979, con Rosalía de Castro.", categoria = "España", idColeccion = 7))

                                        // Sellos
                                        itemDao.insertar(Item(nombre = "Penique negro", descripcion = "Primer sello postal de la historia, 1840.", categoria = "Reino Unido", idColeccion = 8))

                                        // Discos de Vinilo
                                        itemDao.insertar(Item(nombre = "The Dark Side of the Moon", descripcion = "Pink Floyd, edición original de 1973.", categoria = "Rock Progresivo", idColeccion = 9))
                                        itemDao.insertar(Item(nombre = "Thriller", descripcion = "Michael Jackson, edición de 1982.", categoria = "Pop", idColeccion = 9))

                                        // Cartas Coleccionables
                                        itemDao.insertar(Item(nombre = "Black Lotus", descripcion = "Carta de Magic: The Gathering, Alpha.", categoria = "Magic", idColeccion = 10))
                                        itemDao.insertar(Item(nombre = "Charizard 1ª Edición", descripcion = "Carta holográfica de Pokémon TCG, Base Set.", categoria = "Pokémon", idColeccion = 10))
                                    }
                                }
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
