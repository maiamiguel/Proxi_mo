package com.example.android.proximo.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Company(
        val name : String,
        val district : String,
        val county : String,
        val parish : String,
        val address : String,
        val latitude : Double,
        val longitude : Double,
        val geo_hash : String,
        val gmaps_url : String,
        val social : Social,
        val website : String,
        val notes : String,
        val home_delivery : Boolean,
        val contacts : Contacts,
        val categories : List<String>,
        val schedules : Schedule,
        val images: Picture,
        val id: String
) : Parcelable