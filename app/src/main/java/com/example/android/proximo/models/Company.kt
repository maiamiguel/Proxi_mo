package com.example.android.proximo.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Company(
    val name : String,
    val id: String,
    val address: String
) : Parcelable