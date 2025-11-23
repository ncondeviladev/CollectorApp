package com.example.collectorapp.ui.screens.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.collectorapp.ui.navigation.Rutas
import kotlinx.coroutines.delay

//Pantalla inicio bienvenida
@Composable
fun SplashScreen(navController: NavController) {
    LaunchedEffect(key1 = true) {
        delay(2000) // Esperamos 2 segundos
        navController.navigate(Rutas.LISTA_COLECCIONESS) {
            // Limpiamos la pila de navegación para que el usuario no pueda volver atrás.
            popUpTo(Rutas.SPLASH) {
                inclusive = true
            }
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Collector App",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}
