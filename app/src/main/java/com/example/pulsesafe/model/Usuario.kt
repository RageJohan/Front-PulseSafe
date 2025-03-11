    package com.tecsup.pulsesafe.model

    import com.google.gson.annotations.SerializedName

    data class Usuario(
        @SerializedName("idUsuario") val idUsuario: Long? = null,
        val nombre: String = "",
        val email: String,
        val password: String
    )
