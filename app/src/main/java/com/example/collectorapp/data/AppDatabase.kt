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

                                // --- COLECCIONES ---
                                coleccionDao.insertar(Coleccion(1, "Figuras de Acción", "Figuras de comics, videojuegos y películas."))
                                coleccionDao.insertar(Coleccion(2, "Videojuegos", "Formato físico y digital."))
                                coleccionDao.insertar(Coleccion(3, "Cómics y Manga", "Tomos únicos y sagas completas."))
                                coleccionDao.insertar(Coleccion(4, "Funkos", "Figuras Funko Pop de varias franquicias."))
                                coleccionDao.insertar(Coleccion(5, "Lego", "Sets de construcción Lego."))
                                coleccionDao.insertar(Coleccion(6, "Juegos de Mesa", "Juegos de estrategia, rol y cartas."))

                                // --- ITEMS ---

                                // Figuras de Acción (ID: 1)
                                itemDao.insertar(Item(nombre = "Figura Batman", descripcion = "Figura de acción de Batman de la serie animada.", categoria = "DC Comics", idColeccion = 1))
                                itemDao.insertar(Item(nombre = "Figura Spider-Man", descripcion = "Figura articulada de Spider-Man de Marvel Legends.", categoria = "Marvel", idColeccion = 1))
                                itemDao.insertar(Item(nombre = "Darth Vader Black Series", descripcion = "Figura de 6 pulgadas de la línea Star Wars Black Series.", categoria = "Star Wars", idColeccion = 1))

                                // Videojuegos (ID: 2)
                                itemDao.insertar(Item(nombre = "PS5 God of War", descripcion = "Juego God of War para PS5.", categoria = "PlayStation", idColeccion = 2))
                                itemDao.insertar(Item(nombre = "Xbox Series X Halo", descripcion = "Juego Halo Infinite para Xbox Series X.", categoria = "Xbox", idColeccion = 2))
                                itemDao.insertar(Item(nombre = "Zelda: Tears of the Kingdom", descripcion = "Juego para Nintendo Switch.", categoria = "Nintendo", idColeccion = 2))
                                itemDao.insertar(Item(nombre = "Baldur's Gate 3", descripcion = "Edición coleccionista para PC.", categoria = "PC", idColeccion = 2))

                                // Cómics y Manga (ID: 3)
                                itemDao.insertar(Item(nombre = "Manga Naruto Vol.1", descripcion = "Primer volumen del manga Naruto.", categoria = "Manga", idColeccion = 3))
                                itemDao.insertar(Item(nombre = "Comic Batman Año Uno", descripcion = "Comic clásico de Batman: Año Uno.", categoria = "DC Comics", idColeccion = 3))
                                itemDao.insertar(Item(nombre = "The Infinity Gauntlet", descripcion = "Saga completa en un solo tomo.", categoria = "Marvel", idColeccion = 3))

                                // Funkos (ID: 4) - 15 ítems
                                itemDao.insertar(Item(nombre = "Funko Pop Grogu", descripcion = "Funko de 'The Child' de The Mandalorian.", categoria = "Star Wars", idColeccion = 4))
                                itemDao.insertar(Item(nombre = "Funko Pop Harry Potter", descripcion = "Funko de Harry con la cicatriz y las gafas.", categoria = "Harry Potter", idColeccion = 4))
                                itemDao.insertar(Item(nombre = "Funko Pop Goku Super Saiyan", descripcion = "Funko de Goku en su primera transformación.", categoria = "Dragon Ball", idColeccion = 4))
                                itemDao.insertar(Item(nombre = "Funko Pop Daenerys Targaryen", descripcion = "Daenerys en el Trono de Hierro.", categoria = "Game of Thrones", idColeccion = 4))
                                itemDao.insertar(Item(nombre = "Funko Pop Michael Scott", descripcion = "Personaje de The Office.", categoria = "TV Shows", idColeccion = 4))
                                itemDao.insertar(Item(nombre = "Funko Pop Pikachu", descripcion = "Funko del personaje de Pokémon.", categoria = "Pokémon", idColeccion = 4))
                                itemDao.insertar(Item(nombre = "Funko Pop Iron Man", descripcion = "Iron Man de Avengers: Endgame.", categoria = "Marvel", idColeccion = 4))
                                itemDao.insertar(Item(nombre = "Funko Pop Wonder Woman", descripcion = "Wonder Woman con su lazo.", categoria = "DC Comics", idColeccion = 4))
                                itemDao.insertar(Item(nombre = "Funko Pop Rick Sanchez", descripcion = "Funko de Rick and Morty.", categoria = "Animation", idColeccion = 4))
                                itemDao.insertar(Item(nombre = "Funko Pop Stitch", descripcion = "Funko de Lilo & Stitch.", categoria = "Disney", idColeccion = 4))
                                itemDao.insertar(Item(nombre = "Funko Pop Gandalf", descripcion = "Funko de The Lord of the Rings.", categoria = "Movies", idColeccion = 4))
                                itemDao.insertar(Item(nombre = "Funko Pop Tanjiro Kamado", descripcion = "Personaje de Demon Slayer.", categoria = "Anime", idColeccion = 4))
                                itemDao.insertar(Item(nombre = "Funko Pop Jon Snow", descripcion = "Jon Snow con su lobo Ghost.", categoria = "Game of Thrones", idColeccion = 4))
                                itemDao.insertar(Item(nombre = "Funko Pop Wednesday Addams", descripcion = "Personaje de la serie de Netflix.", categoria = "TV Shows", idColeccion = 4))
                                itemDao.insertar(Item(nombre = "Funko Pop Baby Yoda with Cookie", descripcion = "Grogu comiendo una galleta azul.", categoria = "Star Wars", idColeccion = 4))

                                // Lego (ID: 5)
                                itemDao.insertar(Item(nombre = "Lego Halcón Milenario", descripcion = "Set 75192, más de 7500 piezas.", categoria = "Star Wars", idColeccion = 5))
                                itemDao.insertar(Item(nombre = "Lego Castillo de Hogwarts", descripcion = "Set 71043, recreación a microescala.", categoria = "Harry Potter", idColeccion = 5))

                                // Juegos de Mesa (ID: 6)
                                itemDao.insertar(Item(nombre = "Catan", descripcion = "Juego de estrategia y gestión de recursos.", categoria = "Estrategia", idColeccion = 6))
                                itemDao.insertar(Item(nombre = "Dungeons & Dragons Starter Set", descripcion = "Caja de inicio para el juego de rol.", categoria = "Rol", idColeccion = 6))
                                itemDao.insertar(Item(nombre = "Magic: The Gathering Commander Deck", descripcion = "Mazo preconstruido para el formato Commander.", categoria = "Cartas", idColeccion = 6))
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