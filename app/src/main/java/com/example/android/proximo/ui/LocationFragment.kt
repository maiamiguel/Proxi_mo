package com.example.android.proximo.ui

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.birjuvachhani.locus.Locus
import com.example.android.proximo.R
import com.example.android.proximo.databinding.LocationFragmentBinding
import com.example.android.proximo.viewmodels.LocationViewModel
import com.tomtom.online.sdk.search.OnlineSearchApi
import com.tomtom.online.sdk.search.data.reversegeocoder.ReverseGeocoderSearchQueryBuilder
import com.tomtom.online.sdk.search.data.reversegeocoder.ReverseGeocoderSearchResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class LocationFragment : Fragment() {
    private val PERMISSION_ID = 42
    private lateinit var district: String
    private lateinit var county: String
    private lateinit var parish: String

    private val viewModel: LocationViewModel by lazy {
        ViewModelProvider(this).get(LocationViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d("debug", "onCreateView")
        val binding = LocationFragmentBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val titleTextView = binding.text
        val proximaBold = Typeface.createFromAsset(context?.assets, "fonts/Proxima_Nova_Bold.otf")
        titleTextView.typeface = proximaBold

        binding.distrito.typeface = proximaBold
        binding.concelho.typeface = proximaBold
        binding.freguesia.typeface = proximaBold

        //Request Location
        getLastLocation()

        viewModel.doneLocating.observe(viewLifecycleOwner, Observer {
            if (this::district.isInitialized && this::county.isInitialized && this::parish.isInitialized) {
                binding.distrito.text = district
                binding.concelho.text = county
                binding.freguesia.text = parish
            }
        })

        binding.ackBTN.setOnClickListener {
            findNavController().navigate(LocationFragmentDirections.actionLocationFragmentToOverviewFragment(county, district))
        }

        binding.changeLocation.setOnClickListener {
            findNavController().navigate(com.example.android.proximo.R.id.action_locationFragment_to_changeLocationFragment)
        }

        return binding.root
    }

    private fun getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                context?.let {
                    Locus.getCurrentLocation(it) { result ->
                        result.location?.let {
                            reverseGeocoder(result.location!!.latitude, result.location!!.longitude)
                            viewModel.locating()
                        }
                        result.error?.let {
                            viewModel.setError()
                        }
                    }
                }
            } else {
                Toast.makeText(context, "Turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    private fun reverseGeocoder(latitude: Double, longitude: Double) {
        Log.d("debug", "reverseGeocoder")
        try {
            val searchApi = context?.let { OnlineSearchApi.create(it) }
            searchApi?.reverseGeocoding(ReverseGeocoderSearchQueryBuilder(latitude, longitude).build())
                    ?.subscribeOn(Schedulers.io())
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.subscribe(object : DisposableSingleObserver<ReverseGeocoderSearchResponse?>() {
                        override fun onSuccess(response: ReverseGeocoderSearchResponse) {
                            Log.d("debug", "MORADA - ${response.addresses[0]}")

                            Log.d("debug", "Freguesia - ${response.addresses[0].address.municipalitySubdivision}")
                            Log.d("debug", "Concelho - ${response.addresses[0].address.municipality}")
                            Log.d("debug", "Distrito - ${response.addresses[0].address.countrySecondarySubdivision}")
                            Log.d("debug", "País - ${response.addresses[0].address.country}")

                            district = response.addresses[0].address.countrySecondarySubdivision
                            county = response.addresses[0].address.municipality
                            parish = response.addresses[0].address.municipalitySubdivision
                            viewModel.doneLocating()
                        }

                        override fun onError(e: Throwable) {
                            viewModel.setError()
                            Log.e("error", e.toString())
                        }
                    })
        } catch (e: Exception) {
            viewModel.setError()
            e.printStackTrace()
            Log.e("error", e.toString())
        }
    }

    override fun onStop() {
        Locus.stopLocationUpdates()
        super.onStop()
    }

    private fun isLocationEnabled(): Boolean {
        Log.d("location", "isLocationEnabled")
        val locationManager: LocationManager = (activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager)
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        )
    }

    private fun checkPermissions(): Boolean {
        Log.d("location", "checkPermissions")
        if (context?.let { ActivityCompat.checkSelfPermission(it, Manifest.permission.ACCESS_COARSE_LOCATION) } == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true
        }
        return false
    }

    private fun requestPermissions() {
        Log.d("location", "requestPermissions")
        activity?.let {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), PERMISSION_ID)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        Log.d("location", "onRequestPermissionsResult")
        if (requestCode == PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLastLocation()
            } else {
                findNavController().navigate(R.id.action_locationFragment_to_changeLocationFragment)
            }
        }
    }
}
