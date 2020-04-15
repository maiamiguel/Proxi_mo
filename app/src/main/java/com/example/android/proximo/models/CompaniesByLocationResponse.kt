package com.example.android.proximo.models

import com.squareup.moshi.Json

class CompaniesByLocationResponse(
    @Json(name = "state")
    val state: String,

    @Json(name = "county")
    val county: String,

    @Json(name = "companies")
    val companies: Map<String,Company>
)