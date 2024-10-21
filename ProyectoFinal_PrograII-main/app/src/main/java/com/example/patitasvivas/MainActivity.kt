package com.example.patitasvivas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.patitasvivas.ui.theme.PatitasVivasTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : ComponentActivity() {

    //variable tipo navHostController para la funcion creada en el archivo Navegacionrutas
    private lateinit var navHostController: NavHostController
    //para usar el firebase para la autenticacion se declara una variable
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //se inicializa la variable
        auth= Firebase.auth
        setContent {
            //se inicializa la variable
            navHostController = rememberNavController()

            PatitasVivasTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    //se llama la funcion que definimos en Navegacionrutas y le mandamos como parametro la varible creada como navHostController
                    Navegacion(navHostController, auth)
                }
            }
        }
    }

    //metodo que se ejecuta luego de logearse

    override fun onStart() {
        super.onStart()
        //variable para saber si estamos logeado o no
        val currentUser = auth.currentUser
        if(currentUser != null){
            //navegar siguiente pantalla
        }
    }


}



