package com.example.android.proximo.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Contacts(
    val cellphone : List<String>,
    val telephone : List<String>
) : Parcelable