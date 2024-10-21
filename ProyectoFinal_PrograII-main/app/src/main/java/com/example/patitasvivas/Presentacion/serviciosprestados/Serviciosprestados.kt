package com.example.patitasvivas.Presentacion.serviciosprestados

import android.Manifest // Aquí decimos que vamos a usar permisos de Android
import android.app.Activity // Importamos la clase Activity para manejar actividades en la app
import android.app.NotificationChannel // Usamos esto para crear canales de notificaciones
import android.app.NotificationManager // Esto es para manejar las notificaciones
import android.content.pm.PackageManager // Para manejar permisos de aplicaciones
import android.os.Build // Para saber en qué versión de Android estamos
import androidx.compose.foundation.layout.* // Importamos herramientas para hacer la interfaz
import androidx.compose.foundation.text.KeyboardOptions // Para opciones del teclado
import androidx.compose.material3.* // Traemos herramientas de Material Design para la app
import androidx.compose.runtime.* // Para usar variables y estados en Compose
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment // Para alinear cosas en la pantalla
import androidx.compose.ui.Modifier // Para modificar cómo se ve cada parte
import androidx.compose.ui.graphics.Color // Para usar colores
import androidx.compose.ui.platform.LocalContext // Para obtener el contexto local
import androidx.compose.ui.semantics.SemanticsProperties.Text // Para la propiedad de texto
import androidx.compose.ui.text.TextStyle // Para el estilo del texto
import androidx.compose.ui.text.input.KeyboardType // Para tipos de teclado (números, texto, etc.)
import androidx.compose.ui.text.style.TextAlign // Para alinear el texto
import androidx.compose.ui.unit.dp // Para manejar medidas (días, altura, etc.)
import androidx.core.app.ActivityCompat // Para usar compatibilidad con actividades
import androidx.core.app.NotificationCompat // Para crear notificaciones
import androidx.core.app.NotificationManagerCompat // Para manejar las notificaciones
import androidx.core.content.ContextCompat // Para manejar contexto y permisos
import androidx.lifecycle.ViewModel // Para manejar el estado de la aplicación
import com.example.patitasvivas.mensaje
import com.google.firebase.auth.FirebaseAuth // Importamos Firebase para autenticación de usuarios
import com.google.firebase.firestore.FieldValue // Para manejar valores en Firestore
import com.google.firebase.firestore.FirebaseFirestore // Para interactuar con Firestore


// Esta función muestra la pantalla para ofrecer un servicio
@Composable
fun OfferServiceScreen(auth: FirebaseAuth) {
    // Aquí definimos varias cosas que vamos a usar
    var serviceName by remember { mutableStateOf("") } // Nombre del servicio
    var serviceType by remember { mutableStateOf("") } // Tipo de servicio
    var serviceDescription by remember { mutableStateOf("") } // Descripción del servicio
    var serviceCost by remember { mutableStateOf("") } // Costo del servicio
    var isFree by remember { mutableStateOf(false) } // ¿Es gratuito?
    var permissionGranted by remember { mutableStateOf(false) } // Verifica si tenemos permiso para enviar notificaciones

    var dialogTitle by remember { mutableStateOf("") } // Título del diálogo de alerta.
    var dialogText by remember { mutableStateOf("") } // Texto del diálogo de alerta.
    var show by rememberSaveable { mutableStateOf(false) } // Estado para controlar la visibilidad del diálogo de alerta.

    // Inicializamos Firebase para que podamos usarlo
    val db = FirebaseFirestore.getInstance() // Obtenemos la instancia de Firestore

    // Obtenemos el ID del usuario que ha iniciado sesión
    val userId = auth.currentUser?.uid // Verificamos si el usuario ha iniciado sesión y obtenemos su ID

    // Obtenemos el contexto de la aplicación
    val context = LocalContext.current // Contexto que se usará para la creación de notificaciones

    // Creamos un canal para las notificaciones
    createNotificationChannel(context) // Función que crea un canal de notificaciones si es necesario

    // Verificamos si ya tenemos permiso para enviar notificaciones
    LaunchedEffect(Unit) { // Ejecuta el bloque de código una vez cuando se inicia
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS // Verificamos el permiso de notificaciones
            ) == PackageManager.PERMISSION_GRANTED // Comprobamos si el permiso está concedido
        ) {
            permissionGranted = true // Si tenemos permiso, lo decimos
        } else {
            requestNotificationPermission(context) // Si no, pedimos permiso
        }
    }

    // Aquí comenzamos a construir nuestra interfaz
    Column(
        modifier = Modifier // Modificamos cómo se ve la columna
            .fillMaxSize() // La columna ocupa todo el espacio disponible
            .padding(16.dp), // Agregamos un poco de espacio alrededor
        verticalArrangement = Arrangement.Center, // Alineamos verticalmente al centro
        horizontalAlignment = Alignment.CenterHorizontally // Alineamos horizontalmente al centro
    ) {
        // Mostramos un texto que dice "Ofrecer un servicio para mascotas"
        Text(text = "Ofrecer un servicio para mascotas", color = Color.White)

        Spacer(modifier = Modifier.height(8.dp)) // Un espacio entre elementos

        // Campo para ingresar el nombre del servicio
        OutlinedTextField(
            value = serviceName, // El valor actual del campo
            onValueChange = { serviceName = it }, // Lo que pasa cuando cambia el texto
            label = { Text("Nombre del Servicio", style = TextStyle(color = Color.White, textAlign = TextAlign.Center)) }, // Etiqueta del campo
            modifier = Modifier.fillMaxWidth(), // El campo ocupa todo el ancho
            textStyle = TextStyle(color = Color.White) // Estilo del texto
        )

        Spacer(modifier = Modifier.height(8.dp)) // Otro espacio

        // Campo para ingresar el tipo de servicio
        OutlinedTextField(
            value = serviceType, // El valor actual del campo
            onValueChange = { serviceType = it }, // Lo que pasa cuando cambia el texto
            label = { Text("Tipo de Servicio (ej.: paseo, cuidado temporal)", style = TextStyle(color = Color.White, textAlign = TextAlign.Center)) }, // Etiqueta del campo
            modifier = Modifier.fillMaxWidth(), // El campo ocupa todo el ancho
            textStyle = TextStyle(color = Color.White) // Estilo del texto
        )

        Spacer(modifier = Modifier.height(8.dp)) // Otro espacio

        // Campo para ingresar la descripción del servicio
        OutlinedTextField(
            value = serviceDescription, // El valor actual del campo
            onValueChange = { serviceDescription = it }, // Lo que pasa cuando cambia el texto
            label = { Text(text = "Descripción del Servicio", style = TextStyle(color = Color.White, textAlign = TextAlign.Center)) }, // Etiqueta del campo
            modifier = Modifier.fillMaxWidth().height(120.dp), // El campo ocupa todo el ancho y tiene una altura definida
            textStyle = TextStyle(color = Color.White) // Estilo del texto
        )

        Spacer(modifier = Modifier.height(8.dp)) // Otro espacio

        // Checkbox para seleccionar si el servicio es gratuito
        Row(verticalAlignment = Alignment.CenterVertically) { // Fila para alinear el checkbox y el texto
            Checkbox(checked = isFree, onCheckedChange = { isFree = it }, colors = CheckboxDefaults.colors(Color.White)) // La caja de verificación
            Text("Servicio Gratuito", style = TextStyle(color = Color.White, textAlign = TextAlign.Center)) // Texto al lado
        }

        // Si el servicio no es gratuito, pedimos el costo
        if (!isFree) {
            OutlinedTextField(
                value = serviceCost, // El valor actual del campo
                onValueChange = { serviceCost = it }, // Lo que pasa cuando cambia el texto
                label = { Text("Costo del Servicio", style = TextStyle(color = Color.White, textAlign = TextAlign.Center)) }, // Etiqueta del campo
                modifier = Modifier.fillMaxWidth(), // El campo ocupa todo el ancho
                textStyle = TextStyle(color = Color.White), // Estilo del texto
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number) // Solo permite entrada numérica
            )
        }

        Spacer(modifier = Modifier.height(16.dp)) // Otro espacio

        // Botón para ofrecer el servicio
        Button(onClick = { // Acción al hacer clic en el botón
            userId?.let { // Si tenemos el ID del usuario
                if (permissionGranted) { // Si tenemos permiso para enviar notificaciones
                    // Llamamos a la función para ofrecer el servicio
                    offerService(
                        db, // Base de datos Firestore
                        it, // ID del usuario
                        serviceName, // Nombre del servicio
                        serviceType, // Tipo de servicio
                        serviceDescription, // Descripción del servicio
                        if (isFree) "Gratis" else serviceCost, // Costo del servicio
                        context // Contexto para notificaciones
                    ) {
                        show = true // Mostramos el diálogo de alerta
                        dialogTitle = "Alerta" // Título del diálogo
                        dialogText = "Servicio Agregado, por favor revise su notificaciones" // Texto del diálogo
                        // Aquí limpiamos los campos después de enviar el servicio
                        serviceName = "" // Reiniciamos el campo del nombre
                        serviceType = "" // Reiniciamos el campo del tipo de servicio
                        serviceDescription = "" // Reiniciamos el campo de descripción
                        serviceCost = "" // Reiniciamos el campo de costo
                        isFree = false // Reiniciamos el estado del checkbox
                    }
                } else {
                    // Si no, volvemos a pedir el permiso
                    requestNotificationPermission(context)
                }
            }
        }) {
            Text(text = "Ofrecer Servicio") // Texto del botón
        }

        // Mostramos el diálogo de alerta si show es verdadero
        if (show) {
            AlertDialog(
                onDismissRequest = { show = false }, // Acción al cerrar el diálogo
                title = { Text(dialogTitle) }, // Título del diálogo
                text = { Text(dialogText) }, // Texto del diálogo
                confirmButton = {
                    Button(onClick = { show = false }) { // Botón de confirmación
                        Text("Aceptar") // Texto del botón
                    }
                }
            )
        }
    }
}

// Esta función se encarga de enviar el servicio a Firebase
fun offerService(
    db: FirebaseFirestore,
    userId: String,
    name: String,
    type: String,
    description: String,
    cost: String,
    context: android.content.Context,
    onSuccess: () -> Unit
) {
    // Creamos un nuevo documento en la colección de servicios
    val documentReference = db.collection("services").document() // Aquí se crea un documento nuevo

    // Definimos qué información vamos a enviar
    val service = hashMapOf(
        "idServicio" to documentReference.id, // Agregamos el ID del servicio
        "idUsuario" to userId, // Agregamos el ID del usuario
        "name" to name, // Agregamos el nombre del servicio
        "type" to type, // Agregamos el tipo de servicio
        "description" to description, // Agregamos la descripción
        "cost" to cost, // Agregamos el costo
        "timestamp" to FieldValue.serverTimestamp() // Agregamos la hora en que se creó el servicio
    )

    // Subimos los datos a Firestore
    documentReference.set(service)
        .addOnSuccessListener {
            // Si todo sale bien, mostramos una notificación
            showNotification(context, name)
            onSuccess()
        }
        .addOnFailureListener { e ->
            // Si hay un error, no hacemos nada especial (podrías mostrar un mensaje)
        }
}

// Esta función crea un canal para las notificaciones
fun createNotificationChannel(context: android.content.Context) {
    // Solo necesitamos hacer esto si estamos en una versión de Android que lo requiere
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = "Service Notifications" // Nombre del canal
        val descriptionText = "Notifications for new services" // Descripción del canal
        val importance = NotificationManager.IMPORTANCE_DEFAULT // La importancia de las notificaciones
        val channel = NotificationChannel("SERVICE_CHANNEL_ID", name, importance).apply {
            description = descriptionText // Agregamos la descripción al canal
        }
        // Obtenemos el manejador de notificaciones
        val notificationManager: NotificationManager =
            context.getSystemService(android.content.Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel) // Creamos el canal
    }
}

// Esta función muestra una notificación al usuario
fun showNotification(context: android.content.Context, serviceName: String) {
    val builder = NotificationCompat.Builder(context, "SERVICE_CHANNEL_ID") // Construimos la notificación
        .setSmallIcon(android.R.drawable.ic_menu_info_details) // Icono de la notificación
        .setContentTitle("Nuevo servicio creado") // Título de la notificación
        .setContentText("El servicio $serviceName ha sido registrado.") // Texto que dice que se ha registrado el servicio
        .setPriority(NotificationCompat.PRIORITY_DEFAULT) // Prioridad de la notificación

    // Verificamos si tenemos permiso para enviar notificaciones
    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        requestNotificationPermission(context) // Si no tenemos permiso, lo pedimos
        return // Salimos de la función
    }

    // Si tenemos permiso, mostramos la notificación
    with(NotificationManagerCompat.from(context)) {
        notify(1001, builder.build()) // Mostramos la notificación con un ID único
    }
}

// Función para solicitar permiso para enviar notificaciones
private fun requestNotificationPermission(context: android.content.Context) {
    ActivityCompat.requestPermissions(
        context as Activity, // Aseguramos que el contexto es una actividad
        arrayOf(Manifest.permission.POST_NOTIFICATIONS), // Pedimos permiso para enviar notificaciones
        1001 // ID para la solicitud
    )
}