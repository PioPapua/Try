package com.example.atry.network

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

// Login
@Parcelize
data class Login(
    @Json(name="nombreUsuario") val username: String,
    @Json(name="contraseña") val password: String
): Parcelable

@Parcelize
data class LoginResponse(
    val status: String,
    val data: LoginData
): Parcelable

@Parcelize
data class LoginData (
    @Json(name="nombreUsuario") val username: String
): Parcelable


// Products
@Parcelize
data class Products(
    val status: String,
    val data: List<ProductData>
): Parcelable

@Parcelize
data class ProductData (
    @Json(name="codigoBarra") val barcode: String,
    @Json(name="nombreProducto") val name: String,
    @Json(name="rubro") val category: String,
    @Json(name="esAlimento") val isFood: Boolean,
    @Json(name="porcion") val portion: Int,
    @Json(name="tipoPorcion") val portionType: String,
    @Json(name="marca") val trademark: String,
    @Json(name="contenidoNeto") val netWeight: String,
    @Json(name="descripcion") val description: String,
    @Json(name="tipoRubro") val categoryType: String,
    @Json(name="urlImagen") val imageUrl: String,
    @Json(name="fabricante") val manufacturer: Manufacturer,
    @Json(name="envase") val packaging: Packaging,
    @Json(name="habilitado") val enabled: Boolean,
    @Json(name="idProducto") val idProduct: Int
): Parcelable

@Parcelize
data class Manufacturer(
    @Json(name="idFabricante") val idManufacturer: Int,
    @Json(name="nombre") val name: String
): Parcelable

@Parcelize
data class Packaging(
    @Json(name="idEnvase") val idPackaging: Int,
    @Json(name="descripcion") val description: String,
    @Json(name="codigoTipoEnvase") val packagingType: String
): Parcelable


// Nutrition Facts
@Parcelize
data class NutritionFacts(
    val status: String,
    val data: List<NutritionFactData>
): Parcelable

@Parcelize
data class NutritionFactData (
    @Json(name="nombre") val name: String,
    @Json(name="descripcion") val description: String,
    @Json(name="recomendableDiario") val dairyRecommendation: Float,
    @Json(name="tipoPorcion") val portionType: String,
    @Json(name="linkInformacionExtra") val informationLink: String,
    @Json(name="idValorEnergetico") val id: Int
): Parcelable

// Ingredients
@Parcelize
data class Ingredients(
    val status: String,
    val data: List<Ingredient>
): Parcelable

@Parcelize
data class Ingredient (
    @Json(name="nombre") val name: String,
    @Json(name="descripcion") val description: String,
    @Json(name="linkInformacionExtra") val informationLink: String,
    @Json(name="tipoRubro") val categoryType: String,
    @Json(name="conAdvertencia") val warning: Boolean,
    @Json(name="clasificacionEpa") val epaClassification: String,
    @Json(name="idIngrediente") val id: Int
): Parcelable

// Packaging Characteristic
@Parcelize
data class PackagingCharacteristics(
    val status: String,
    val data: List<PackagingCharacteristic>
): Parcelable

@Parcelize
data class PackagingCharacteristic (
    @Json(name="categoria") val category: String,
    @Json(name="descripcion") val description: String,
    @Json(name="valor") val value: Int,
    @Json(name="idCaracteristicaEnvase") val id: Int
): Parcelable

// Labels
@Parcelize
data class Labels(
    val status: String,
    val data: List<Label>
): Parcelable

@Parcelize
data class Label (
    @Json(name="urlLogo") val logoUrl: String,
    @Json(name="descripcion") val description: String,
    @Json(name="tipoRubro") val categoryType: String,
    @Json(name="idExtra") val id: Int
): Parcelable