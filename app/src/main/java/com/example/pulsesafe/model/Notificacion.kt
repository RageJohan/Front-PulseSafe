package com.example.pulsesafe.model

data class Notificacion(
    val id_notificacion: Long,
    val id_usuario: Int,
    val tipo_alerta: String,
    val mensaje: String,
    val timestamp: String,
    val estado_envio: Int
)
