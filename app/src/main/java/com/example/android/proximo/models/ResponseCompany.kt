package com.example.android.proximo.models

import com.squareup.moshi.Json

class ResponseCompany(
    @Json(name = "state")
    val state: String,
    @Json(name = "district")
    val district: String,
    @Json(name = "counties")
    val counties: List<List<String>>
)