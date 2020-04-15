package com.example.android.proximo.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Picture(
    val logo : String,
    val exterior : String
) : Parcelable
