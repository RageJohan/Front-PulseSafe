package com.example.pulsesafe.api

import com.example.pulsesafe.model.Notificacion
import com.tecsup.pulsesafe.model.Usuario
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @POST("/api/usuarios/login")
    fun login(@Body usuario: Usuario): Call<Map<String, Any>>


    @POST("/api/usuarios")
    fun register(@Body usuario: Usuario): Call<Usuario>

    @GET("/api/notificaciones/usuario/{idUsuario}")
    fun getNotificaciones(@Path("idUsuario") idUsuario: Long): Call<List<Notificacion>>

    @PUT("/api/usuarios/{id}")
    fun actualizarUsuario(
        @Path("id") id: Long,
        @Body datos: Map<String, String>
    ): Call<Void>

}