package com.example.patitasvivas.Presentacion.Inicio

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.example.patitasvivas.ui.theme.Black
import com.example.patitasvivas.ui.theme.Gray
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.patitasvivas.R
import com.example.patitasvivas.ui.theme.Green


@Composable
// fun es una funcion
//funcion que se encarga de crear la pantalla inicial de la aplicacion
//se llama en el parametro lo del archivo NavegacionToLogin
fun Inicio(navigateToLogin: () -> Unit = {}, navigateToSignUp: () -> Unit = {}) {
    //se crea una columna en la pantalla de inicio
    Column(
        //fillmaxsize es que se cree en toda la pantalla
        modifier = Modifier.fillMaxSize().
        //background lo utilizaremos para dar fondo en la pantalla en este
        //caso utilizaremos un degradado de color empezando de gris a negro
        //se utiliza el brush.verticalgradient
        //list of lo agregamos para que nos permita agregar los colores que
            // queremos que se degrade el primero sera el color mas degradado.
            // los colores estan en la carpeta ui.theme y en el archivo color
        background(Brush.verticalGradient(listOf(Gray, Black),startY = 0f, endY = 600f)),
        //horizontalAlignment nos ayuda a ajustar al columna en este caso lo centramos
        horizontalAlignment = Alignment.CenterHorizontally
        ) {

        //Spacer divide la pantalla uniformemente
        Spacer(modifier=Modifier.weight(1f))
            //la imagen la usamos para colocar un icono el painter nos ayuda a pintar la imagen
            //modifier dentro de imagen la usaremos para redondear la imagen
            Image(painter = painterResource(id= R.drawable.logo), contentDescription = "", modifier = Modifier.clip(CircleShape).size(200.dp))
        Spacer(modifier=Modifier.weight(1f))
            //Creamos un texto
            Text("Salvando y dando un mejor",color= Color.White, fontSize = 30.sp, fontWeight = FontWeight.Bold)
        Text("hogar",color= Color.White, fontSize = 30.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier=Modifier.weight(1f))
            //creamos boton para Sign Up
            Button(onClick = { navigateToSignUp() },
                //fillMaxWidth es para que se expanda al tamaño de la pantalla
                modifier = Modifier.fillMaxWidth().
                height(48.dp).
                padding(horizontal = 32.dp), colors= ButtonDefaults.buttonColors(containerColor = Green)) {
                Text(text="Sign Up", color=Black, fontWeight = FontWeight.Bold)
            }
            Text(text="Log In", color=Color.White, modifier=Modifier.padding(48.dp).clickable { navigateToLogin() })
        Spacer(modifier=Modifier.weight(1f))

    }
}