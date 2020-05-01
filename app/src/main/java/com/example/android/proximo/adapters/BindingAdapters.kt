package com.example.android.proximo.adapters

import android.opengl.Visibility
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
import com.example.android.proximo.viewmodels.ChangeLocationStatus
import com.example.android.proximo.viewmodels.LocationStatus
import com.example.android.proximo.viewmodels.MarsApiStatus
import java.util.*
import kotlin.collections.ArrayList

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
    if (homeDelivery) {
        text.text = "Sim"
    } else {
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
    val calendar: Calendar = Calendar.getInstance()

    when (calendar.get(Calendar.DAY_OF_WEEK)) {
        Calendar.MONDAY -> {
            text.text = schedule.monday[0]
        }
        Calendar.TUESDAY -> {
            text.text = schedule.tuesday[0]
        }
        Calendar.WEDNESDAY -> {
            text.text = schedule.wednesday[0]
        }
        Calendar.THURSDAY -> {
            text.text = schedule.thursday[0]
        }
        Calendar.FRIDAY -> {
            text.text = schedule.friday[0]
        }
        Calendar.SATURDAY -> {
            text.text = schedule.saturday[0]
        }
        Calendar.SUNDAY -> {
            text.text = schedule.sunday[0]
        }
    }
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

@BindingAdapter("changeLocationStatusImage")
fun changeLocationStatusImage(statusImageView: ImageView, status: ChangeLocationStatus?) {
    when (status) {
        ChangeLocationStatus.LOCATING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        ChangeLocationStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        ChangeLocationStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}

@BindingAdapter("changeLocationStatusText")
fun changeLocationStatusText(view: TextView, status: ChangeLocationStatus?) {
    when (status) {
        ChangeLocationStatus.ERROR -> {
            view.setText(R.string.error)
        }
        ChangeLocationStatus.DONE -> {
            view.setText(R.string.changeLocationText)
        }
    }
}

@BindingAdapter("visibilityChangeLocationStatus")
fun visibilityChangeLocationStatus(view: View, status: ChangeLocationStatus?) {
    when (status) {
        ChangeLocationStatus.LOCATING -> {
            view.visibility = View.INVISIBLE
        }
        ChangeLocationStatus.ERROR -> {
            view.visibility = View.INVISIBLE
        }
        ChangeLocationStatus.DONE -> {
            view.visibility = View.VISIBLE
        }
    }
}

@BindingAdapter("contactsCellphone")
fun contactsCellphone(text: TextView, contacts: List<String>) {
    if (contacts.isNullOrEmpty()){
        text.visibility = View.GONE
    }
    else{
        text.visibility = View.VISIBLE
        text.text = contacts[0]
    }
}

@BindingAdapter("contactsCellphoneLabel")
fun contactsCellphoneLabel(text: TextView, contacts: List<String>) {
    if (contacts.isNullOrEmpty()){
        text.visibility = View.GONE
    }
    else{
        text.visibility = View.VISIBLE
    }
}

@BindingAdapter("contactsTelephone")
fun contactsTelephone(text: TextView, contacts: List<String>) {
    if (contacts.isNullOrEmpty()){
        text.visibility = View.GONE
    }
    else{
        text.visibility = View.VISIBLE
        text.text = contacts[0]
    }
}

@BindingAdapter("contactsTelephoneLabel")
fun contactsTelephoneLabel(text: TextView, contacts: List<String>) {
    if (contacts.isNullOrEmpty()){
        text.visibility = View.GONE
    }
    else{
        text.visibility = View.VISIBLE
    }
}

@BindingAdapter("notesVisibility")
fun notesVisibility(text: TextView, notes: String) {
    if (notes.isEmpty()){
        text.visibility = View.GONE
    }
    else{
        text.visibility = View.VISIBLE
        text.text = notes
    }
}

@BindingAdapter("notesVisibilityLabel")
fun notesVisibilityLabel(text: TextView, notes: String) {
    if (notes.isEmpty()){
        text.visibility = View.GONE
    }
    else{
        text.visibility = View.VISIBLE
    }
}