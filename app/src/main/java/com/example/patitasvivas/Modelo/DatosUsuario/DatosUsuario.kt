package com.example.patitasvivas.Modelo.DatosUsuario

import android.provider.ContactsContract.CommonDataKinds.Email

data class DatosUsuario(
    val ExperienciaA: String,
    val Interes: String,
    val Nombre: String,
    val ServiciosInteres: String,
    val ServiciosOfrecidos: String,
    val email: String,
    val userid: String
){
    constructor() : this("","","","","","","")
}