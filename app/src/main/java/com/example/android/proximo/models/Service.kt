package com.example.android.proximo.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Service(
    val name: String,
    val img: Int
) : Parcelable