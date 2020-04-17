package com.example.android.proximo.models

import com.squareup.moshi.Json

class ListTypeofServices(
    @Json(name = "state")
    val state : String,
    @Json(name = "categories")
    val categories : List<Category>
)
