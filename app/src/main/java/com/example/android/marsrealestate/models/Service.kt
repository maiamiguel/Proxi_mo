package com.example.android.marsrealestate.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Service(
    val name: String,
    val img: Int
) : Parcelable