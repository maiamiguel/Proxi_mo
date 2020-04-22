package com.example.android.proximo.adapters

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.android.proximo.R
import com.example.android.proximo.models.Category
import com.example.android.proximo.models.Company
import com.example.android.proximo.models.Contacts
import com.example.android.proximo.models.Schedule
import com.example.android.proximo.viewmodels.ApiStatus
import com.example.android.proximo.viewmodels.LocationStatus
import com.example.android.proximo.viewmodels.MarsApiStatus

/**
 * When there is no Mars property data (data is null), hide the [RecyclerView], otherwise show it.
 */
@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Category>?) {
    val adapter = recyclerView.adapter as TypeServicesAdapter
    adapter.submitList(data)
}

@BindingAdapter("listDataServices")
fun bindRecyclerViewServices(recyclerView: RecyclerView, data: List<Company>) {
    val adapter = recyclerView.adapter as ServiceItemAdapter
    adapter.setServicesList(data as ArrayList<Company>)
}

/**
 * Uses the Glide library to load an image by URL into an [ImageView]
 */
@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
                .load(imgUri)
                .apply(RequestOptions()
                        .placeholder(R.drawable.loading_animation)
                        .error(R.drawable.iconeappcirc))
                .apply(RequestOptions().circleCrop())
                .into(imgView)
    }
}

@BindingAdapter("ApiStatus")
fun bindStatus(statusImageView: ImageView, status: MarsApiStatus?) {
    when (status) {
        MarsApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        MarsApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        MarsApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}

@BindingAdapter("homeDelivery")
fun homeDeliveryText(text: TextView, homeDelivery: Boolean) {
    if (homeDelivery){
        text.text = "Sim"
    }
    else{
        text.text = "Não"
    }
}

@BindingAdapter("infoCount")
fun infoCount(statusImageView: ImageView, status: ApiStatus?) {
    when (status) {
        ApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        ApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        ApiStatus.NONE -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.noservicesfound)
        }
        ApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}

@BindingAdapter("timetable")
fun setTimetable(text: TextView, schedule: Schedule) {
    text.text = schedule.monday[0] + "\n" + schedule.monday[1]
}

@BindingAdapter("locationStatusImage")
fun locationStatusImage(statusImageView: ImageView, status: LocationStatus?) {
    when (status) {
        LocationStatus.LOCATING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        LocationStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        LocationStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}

@BindingAdapter("locationStatus")
fun locationStatus(view: View, status: LocationStatus?) {
    when (status) {
        LocationStatus.LOCATING -> {
            view.visibility = View.INVISIBLE
        }
        LocationStatus.ERROR -> {
            view.visibility = View.INVISIBLE
        }
        LocationStatus.DONE -> {
            view.visibility = View.VISIBLE
        }
    }
}

@BindingAdapter("locationStatusText")
fun locationStatusText(view: TextView, status: LocationStatus?) {
    when (status) {
        LocationStatus.LOCATING -> {
            view.setText(R.string.gettingLocation)
        }
        LocationStatus.ERROR -> {
            view.setText(R.string.error)
        }
        LocationStatus.DONE -> {
            view.setText(R.string.confirm_location)
        }
    }
}