package com.example.android.proximo.models

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
class Category (
    @Json(name = "category")
    val category : String,
    @Json(name = "display")
    val display : String
) : Parcelable