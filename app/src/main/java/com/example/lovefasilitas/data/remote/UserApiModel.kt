package com.example.lovefasilitas.data.remote

import com.example.lovefasilitas.domain.model.User
import com.google.gson.annotations.SerializedName

data class UserApiModel(
    @SerializedName("id") val id: String,
    @SerializedName("username") val username: String = "",
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("phone") val phone: String = ""
)

data class AuthResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: UserApiModel?
)

fun UserApiModel.toDomain(): User = User(
    id = id.toIntOrNull() ?: 0,
    email = email,
    name = name,
    phone = phone
)
