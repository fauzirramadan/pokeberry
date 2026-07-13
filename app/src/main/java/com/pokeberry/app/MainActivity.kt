package com.pokeberry.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.pokeberry.app.network.ConnectivityObserver
import com.pokeberry.app.presentation.navigation.AppNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ConnectivityObserver.init(applicationContext)
        enableEdgeToEdge()
        setContent {
            AppNavigation()
        }
    }

}

