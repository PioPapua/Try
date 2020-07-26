package com.example.atry.network

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Login(
    @Json(name="nombreUsuario") val username: String,
    @Json(name="contraseña") val password: String
): Parcelable

@Parcelize
data class LoginResponse(
    val status: String,
    val data: Data
): Parcelable

@Parcelize
data class Data (
    @Json(name="nombreUsuario") val username: String
): Parcelable
