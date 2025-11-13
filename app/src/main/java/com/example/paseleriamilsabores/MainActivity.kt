package com.example.paseleriamilsabores

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.compose.PaseleriaMilSaboresTheme
import com.example.paseleriamilsabores.navigation.AppNavigation
import com.example.paseleriamilsabores.ui.components.BottomNavBar
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics

class MainActivity : ComponentActivity() {
    private lateinit var analytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)
        analytics = Firebase.analytics

        Log.d("FirebaseTest", "✅ Firebase inicializado correctamente")

        enableEdgeToEdge()
        setContent {
            PaseleriaMilSaboresTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    AppNavigation() // Asegúrate de que llamas a tu sistema de navegación aquí

                }
            }
        }
    }
}
