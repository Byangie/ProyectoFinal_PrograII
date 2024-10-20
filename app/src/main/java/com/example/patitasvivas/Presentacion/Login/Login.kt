package com.example.patitasvivas.Presentacion.Login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.patitasvivas.Presentacion.GestionPet.GestionMascota
import com.example.patitasvivas.R
import com.example.patitasvivas.ui.theme.Black
import com.example.patitasvivas.ui.theme.SelectedField
import com.example.patitasvivas.ui.theme.UnselectedField
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

@Composable
//esta es la pantalla del login.
// fun es una funcion
//funcion que se encarga de crear el login de la aplicacion
fun Login(auth: FirebaseAuth,navHostController: NavHostController, navigateToMenu: () -> Unit = {}) {
     var email by remember { mutableStateOf("") }
     var contraseña by remember { mutableStateOf("") }
     Column(modifier= Modifier.fillMaxSize().background(Black), horizontalAlignment = Alignment.CenterHorizontally) {

          Row() {
               //Creamos boton para atras, se debe de importar antes en la carpeta drawable
               Icon(painter=painterResource(id= R.drawable.back), contentDescription="", tint = White,modifier=Modifier.padding(24.dp).clickable { navHostController.popBackStack() })
               Spacer(Modifier.weight(1f))
          }

          //creamos el texto del Email para identificar
          Text("Email",color=White,fontWeight = FontWeight.Bold, fontSize= 40.sp)
          //creamos el textfield del Email para ingresar el dato
          TextField(value=email, onValueChange= {email=it}, modifier=Modifier.fillMaxWidth(),
          //para colocar color distinto al perder el focus
          colors = TextFieldDefaults.colors(unfocusedContainerColor = UnselectedField, focusedContainerColor = SelectedField))



          Spacer(Modifier.height(40.dp))
          Text("Contraseña",color=White,fontWeight = FontWeight.Bold, fontSize= 40.sp)
          //creamos el textfield del Contraseña para ingresar el dato
          TextField(value=contraseña, onValueChange= {contraseña=it},modifier=Modifier.fillMaxWidth(),
          //para colocar color distinto al perder el focus
          colors = TextFieldDefaults.colors(unfocusedContainerColor = UnselectedField, focusedContainerColor = SelectedField))

          //logica del boton
          Spacer(Modifier.height(40.dp))
          Button(onClick = {
               if (!email.equals("")&& !contraseña.equals("")) {
                    auth.signInWithEmailAndPassword(email, contraseña).addOnCompleteListener {
                         if (it.isSuccessful) {
                              //significa que me logee correctamente
                              navigateToMenu()
                         } else {
                              //error


                         }
                    }
               }else{

               }
          }) {
               Text("Login")
          }
     }
}