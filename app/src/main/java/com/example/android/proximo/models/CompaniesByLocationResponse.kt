package com.example.android.proximo.models

import com.squareup.moshi.Json

class CompaniesByLocationResponse(
    @Json(name = "state")
    val state: String,

    @Json(name = "district")
    val district: String,

    @Json(name = "companies")
    val companies: Map<String,Company>
)