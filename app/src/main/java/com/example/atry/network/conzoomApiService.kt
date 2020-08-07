package com.example.atry.network

import com.squareup.moshi.Moshi
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

private const val BASE_URL = "https://api-conzoom.herokuapp.com/api/v1/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()


interface ConzoomApiService{
    @POST("usuarios/login")
    fun getLoginPropertiesAsync(@Body login: Login):
            Deferred<LoginResponse>

    @POST("ingredientes/create")
    fun postIngredientAsync(@Body ingredient: IngredientData):
            Deferred<IngredientData>

    @POST("productos/create")
    fun postProductAsync(@Body product: ProductCompleteData)
//            :Deferred<ProductFinalData>

    @POST("valoresEnergeticos/create")
    fun postNutritionFactAsync(@Body nutritionFact: NutritionFactData):
            Deferred<NutritionFactData>

    @POST("extras/create")
    fun postLabelAsync(@Body ingredient: LabelData):
            Deferred<LabelData>

    @POST ("envases/create")
    fun setPackagingAsync(@Body Packaging: Packaging):
            Deferred<Packaging>

    @POST ("productosValorEnergetico/create")
    fun postAssociatedNutritionAsync(@Body valorEnergetico: NutritionFactData, value: Float)

    @PUT ("productosValorEnergetico/update/{id}")
    fun putAssociatedNutritionAsync(@Body id: Int, value: Float)

    @POST ("fabricantes/create")
    fun postManufacturerAsync(@Body manufacturerName: ManufacturerName)
//            :Deferred<String>

    @GET ("productos")
    fun getProductsPropertiesAsync():
            Deferred<Products>

    @GET ("productos/{idProducto}")
    fun getProductPropertiesAsync(@Path("idProducto") idProduct: Int):
            Deferred<ProductCompleteData>

    @GET ("fabricantes/nombre/{nombre}")
    fun getManufacturerByNameAsync(@Path("nombre") name: String)
            :Deferred<Manufacturer>

    @GET ("valoresEnergeticos")
    fun getNutritionFactsAsync():
            Deferred<NutritionFacts>

    @GET ("ingredientes")
    fun getIngredientsAsync():
            Deferred<Ingredients>

    @GET ("caracteristicasEnvase")
    fun getPackagingCharacteristicsAsync():
            Deferred<PackagingCharacteristics>

    @GET ("caracteristicasEnvase/envase")
    fun getPackagingCharacteristicsByPackageIdAsync(@Query("idEnvase") id: Int):
            Deferred<PackagingCharacteristics>

    @GET ("extras")
    fun getLabelsAsync():
            Deferred<Labels>
}

object ConzoomApi {
    val retrofitService: ConzoomApiService by lazy {
        retrofit.create(ConzoomApiService::class.java)
    }
}

