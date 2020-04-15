package com.example.android.proximo.models

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
class Schedule (
    @Json(name = "segunda-feira")
    val monday: List<String>,

    @Json(name = "terça-feira")
    val tuesday: List<String>,

    @Json(name = "quarta-feira")
    val wednesday: List<String>,

    @Json(name = "quinta-feira")
    val thrusday: List<String>,

    @Json(name = "sexta-feira")
    val friday: List<String>,

    @Json(name = "sábado")
    val saturday: List<String>,

    @Json(name = "domingo")
    val sunday: List<String>
) : Parcelable
