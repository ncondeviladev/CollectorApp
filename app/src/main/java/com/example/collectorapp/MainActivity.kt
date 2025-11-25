package com.example.collectorapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.collectorapp.data.AppDatabase
import com.example.collectorapp.data.repositorio.Repositorio
import com.example.collectorapp.ui.navigation.AppNavHost
import com.example.collectorapp.ui.theme.CollectorAppTheme
import com.example.collectorapp.viewmodels.ColeccionVMFactory
import com.example.collectorapp.viewmodels.ColeccionViewModel
import com.example.collectorapp.viewmodels.ItemVMFactory
import com.example.collectorapp.viewmodels.ItemViewModel

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = AppDatabase.getDatabase(this)
        val repo = Repositorio(database)

        val coleccionVM: ColeccionViewModel by viewModels { ColeccionVMFactory(repo) }
        val itemVM: ItemViewModel by viewModels { ItemVMFactory(repo) }


        setContent {
            var darkTheme by remember { mutableStateOf(false) }


            CollectorAppTheme(darkTheme = darkTheme) {

                    AppNavHost(
                        coleccionVM = coleccionVM,
                        itemVM = itemVM,
                        onToggleTheme = { darkTheme = !darkTheme }
                    )
            }
        }
    }
}
