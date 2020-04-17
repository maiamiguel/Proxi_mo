package com.example.android.proximo.network

import com.example.android.proximo.models.CompaniesByLocationResponse
import com.example.android.proximo.models.ListDistricts
import com.example.android.proximo.models.ListTypeofServices
import com.example.android.proximo.models.ResponseCompany
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "http://api.proxi-mo.pt/"

/**
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
 * full Kotlin compatibility.
 */
private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

/**
 * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
 * object.
 */
private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(BASE_URL)
        .build()

/**
 * A public interface that exposes the [getServicesTypesAsync], [getDistrictsAsync], [counties_by_districAsync], [companies_by_locationAsync] method
 */
interface ApiService {

    @GET("categories")
    fun getServicesTypesAsync():
    Deferred<ListTypeofServices>

    @GET("all_districts")
    fun getDistrictsAsync():
    Deferred<ListDistricts>

    @GET("counties_by_distric")
    fun counties_by_districAsync(
            @Query("district") district: String?
    ): Deferred<ResponseCompany>

    @GET("companies_by_location")
    fun companies_by_locationAsync(
            @Query("county") county: String?
    ): Deferred<CompaniesByLocationResponse>
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object Api {
    val retrofitService : ApiService by lazy { retrofit.create(ApiService::class.java) }
}
