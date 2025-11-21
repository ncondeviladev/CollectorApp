package com.example.collectorapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.collectorapp.data.AppDatabase
import com.example.collectorapp.data.repositorio.Repositorio
import com.example.collectorapp.models.Coleccion
import com.example.collectorapp.models.Displayable
import com.example.collectorapp.models.Item
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

        val coleccionVM = ColeccionVMFactory(repo).create(ColeccionViewModel::class.java)
        val itemVM = ItemVMFactory(repo).create(ItemViewModel::class.java)

        setContent {
            CollectorAppTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    AppNavHost(
                        coleccionVM = coleccionVM,
                        itemVM = itemVM
                    )
                }
            }
        }

    }
}


@Preview(showBackground = true)
@Composable
fun PreviewAppConContenido() {


}