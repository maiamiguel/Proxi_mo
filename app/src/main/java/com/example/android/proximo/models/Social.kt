package com.example.android.proximo.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Social(
    val facebook : String,
    val instagram : String,
    val twitter : String
) : Parcelable
