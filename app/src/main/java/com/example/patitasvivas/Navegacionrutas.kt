package com.example.patitasvivas

//este archivo se encargara de llevar las rutas
//al presionar en un boton este sabra a que ruta o archivo llevar

//importamos librerias

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.patitasvivas.Presentacion.GestionPet.GestionMascota
import com.example.patitasvivas.Presentacion.Inicio.Inicio
import com.example.patitasvivas.Presentacion.Login.Login
import com.example.patitasvivas.Presentacion.Menu.Menus
import com.example.patitasvivas.Presentacion.Signup.Signup
import com.google.firebase.auth.FirebaseAuth

// el Composable lo lleva si o si

@Composable
// fun es una funcion
//funcion que se llama navegacion la cual llevara  un parametro navHostController
fun Navegacion(navHostController: NavHostController, auth: FirebaseAuth){
    //ruta para cuando se presione inicio se vaya a Inicio()
    NavHost(navController = navHostController, startDestination = "INICIO") {
        composable(route = "INICIO"){
            //se llama la funcion que se creo en la carpeta presentacion/Inicio dentro del archivo Pantalla Inicio
            Inicio(
                //esto es para hacer el evento click y se envia los parametros del archivo PantallaInicio
                navigateToLogin = {navHostController.navigate(route = "LOGIN")},
                navigateToSignUp = {navHostController.navigate(route = "Signup")}
            )
        }

        //ruta para cuando se presione Login se vaya a LOGIN()
        composable(route = "LOGIN"){
            //se llama la funcion que se creo en la carpeta presentacion/Login dentro del archivo Login
            Login(auth,navHostController){
                 navHostController.navigate("Menu")
            }
        }

        //ruta para cuando se presione Signup se vaya a Signup()
        composable(route = "Signup"){
            //se llama la funcion que se creo en la carpeta presentacion/Signup dentro del archivo Signup
            Signup(auth,navHostController)
        }

        composable(route = "Menu"){
            //se llama la funcion que se creo en la carpeta presentacion/Signup dentro del archivo Signup
            Menus(auth,navHostController)
        }

    }
}