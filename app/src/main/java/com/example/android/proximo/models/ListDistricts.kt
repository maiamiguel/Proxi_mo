package com.example.android.proximo.models

import com.squareup.moshi.Json

class ListDistricts(
    @Json(name = "state")
    val state: String,
    @Json(name = "districts")
    val districts: List<String>
)

