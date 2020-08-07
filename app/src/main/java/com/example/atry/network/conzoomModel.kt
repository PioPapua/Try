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
    @Json(name="fabricante") val manufacturer: ManufacturerData,
    @Json(name="envase") val packaging: Packaging,
    @Json(name="habilitado") val enabled: Boolean,
    @Json(name="idProducto") val idProduct: Int
): Parcelable

@Parcelize
data class ProductCompleteData (
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
    @Json(name="fabricante") val manufacturer: ManufacturerData,
    @Json(name="envase") val packaging: CompletePackaging,
    @Json(name="productosValorEnergetico") val nutritionFacts: List<ProductNutritionFacts>,
    @Json(name="extras") val labels: List<LabelData>,
    @Json(name="ingredientes") val ingredientsIds: List<IngredientData>
): Parcelable

@Parcelize
data class ProductFinalData (
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
    @Json(name="fabricante") val manufacturer: ManufacturerData,
    @Json(name="envase") val packaging: CompletePackaging,
    @Json(name="productosValorEnergetico") val nutritionFacts: List<ProductNutritionFactData>,
    @Json(name="extras") val labels: List<LabelData>,
    @Json(name="ingredientes") val ingredientsIds: List<IngredientData>
): Parcelable

@Parcelize
data class Manufacturer(
    val status: String,
    val data: ManufacturerData
): Parcelable

@Parcelize
data class ManufacturerData(
    @Json(name="nombre") val name: String,
    @Json(name="idFabricante") val idManufacturer: Int
): Parcelable

@Parcelize
data class ManufacturerName(
    @Json(name="nombre") val name: String
): Parcelable

@Parcelize
data class Packaging(
    @Json(name="idEnvase") val idPackaging: Int,
    @Json(name="descripcion") val description: String,
    @Json(name="codigoTipoEnvase") val packagingType: String
): Parcelable

@Parcelize
data class CompletePackaging(
    @Json(name="idEnvase") val idPackaging: Int,
    @Json(name="descripcion") val description: String,
    @Json(name="codigoTipoEnvase") val packagingType: String,
    @Json(name="caracteristicasEnvase") val characteristicData: List<PackagingCharacteristicData>
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

@Parcelize
data class ProductNutritionFacts (
    @Json(name="idProductoValorEnergetico") val id: Int,
    @Json(name="ValorEnergetico") val nutritionFactData: NutritionFactData,
    @Json(name="valor") val value: Float
): Parcelable

@Parcelize
data class ProductNutritionFactData (
    @Json(name="ValorEnergetico") val nutritionFactData: NutritionFactData,
    @Json(name="valor") val value: Float
): Parcelable

// Ingredients
@Parcelize
data class Ingredients(
    val status: String,
    val data: List<IngredientData>
): Parcelable

@Parcelize
data class IngredientData (
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
    val data: List<PackagingCharacteristicData>
): Parcelable

@Parcelize
data class PackagingCharacteristicData (
    @Json(name="categoria") val category: String,
    @Json(name="descripcion") val description: String,
    @Json(name="valor") val value: Int,
    @Json(name="idCaracteristicaEnvase") val id: Int
): Parcelable

// Labels
@Parcelize
data class Labels(
    val status: String,
    val data: List<LabelData>
): Parcelable

@Parcelize
data class LabelData (
    @Json(name="urlLogo") val logoUrl: String,
    @Json(name="descripcion") val description: String,
    @Json(name="tipoRubro") val categoryType: String,
    @Json(name="idExtra") val id: Int
): Parcelable