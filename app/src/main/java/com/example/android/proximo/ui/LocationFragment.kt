package com.example.android.proximo.ui

import android.Manifest
import android.R
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.birjuvachhani.locus.Locus
import com.example.android.proximo.databinding.LocationFragmentBinding
import com.example.android.proximo.viewmodels.LocationViewModel
import com.tomtom.online.sdk.search.OnlineSearchApi
import com.tomtom.online.sdk.search.data.reversegeocoder.ReverseGeocoderSearchQueryBuilder
import com.tomtom.online.sdk.search.data.reversegeocoder.ReverseGeocoderSearchResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import java.io.IOException

class LocationFragment : Fragment() , AdapterView.OnItemSelectedListener {
    private val PERMISSION_ID = 42
    lateinit var districtSpinner : Spinner

    /**
     * Lazily initialize our [LocationViewModel].
     */
    private val viewModel: LocationViewModel by lazy {
        ViewModelProviders.of(this).get(LocationViewModel::class.java)
    }

    /**
     * Inflates the layout with Data Binding, sets its lifecycle owner to the TypeServicesFragment
     * to enable Data Binding to observe LiveData, and sets up the RecyclerView with an adapter.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = LocationFragmentBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel

        //Request Location
        getLastLocation()

        // you need to have a list of data that you want the spinner to display
        val spinnerArray: MutableList<String> = ArrayList()

        viewModel.properties.observe(
                this.viewLifecycleOwner,
                Observer { t ->
                    t.let {
                        // Sets new Data to RecyclerView
                        Log.d("debug", "districts")
                        for (district in it) {
                            spinnerArray.add(district)
                        }
                    }
                })

        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(requireContext(), R.layout.simple_spinner_item, spinnerArray)

        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        districtSpinner = binding.district
        districtSpinner.adapter = adapter
        districtSpinner.onItemSelectedListener = this

        binding.ackBTN.setOnClickListener {
            findNavController().navigate(com.example.android.proximo.R.id.action_locationFragment_to_overviewFragment)
        }

        return binding.root
    }

    private fun setDistrictSpinner(spnr: Spinner, district: String) {
        val adapter = spnr.adapter
        for (position in 0 until adapter.count) {
            if (adapter.getItem(position) == district) {
                spnr.setSelection(position)
                return
            }
        }
    }

    private fun getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                context?.let {
                    Locus.getCurrentLocation(it) { result ->
                        result.location?.let {
                            reverseGeocoder(result.location!!.latitude, result.location!!.longitude)
                        }
                        result.error?.let { /* Received error! */ }
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
        try {
            val searchApi = context?.let { OnlineSearchApi.create(it) }
            searchApi?.reverseGeocoding(ReverseGeocoderSearchQueryBuilder(40.8035515,-8.5693427).build())
                    ?.subscribeOn(Schedulers.io())
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.subscribe(object : DisposableSingleObserver<ReverseGeocoderSearchResponse?>() {
                        override fun onSuccess(response: ReverseGeocoderSearchResponse) {
                            Log.d("debug", "Freguesia - ${response.addresses.get(0).address.municipalitySubdivision}")
                            Log.d("debug", "Concelho - ${response.addresses.get(0).address.municipality}")
                            Log.d("debug", "Distrito - ${response.addresses.get(0).address.countrySecondarySubdivision}")
                            Log.d("debug", "País - ${response.addresses.get(0).address.country}")

                            setDistrictSpinner(districtSpinner, response.addresses.get(0).address.countrySecondarySubdivision)
                            viewModel.searchCounties(response.addresses.get(0).address.countrySecondarySubdivision)
                        }

                        override fun onError(e: Throwable) {
                            Log.e("error", e.toString())
                        }
                    })
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun onStop() {
        Locus.stopLocationUpdates()
        super.onStop()
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager = (activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager)
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        )
    }

    private fun checkPermissions(): Boolean {
        if (context?.let { ActivityCompat.checkSelfPermission(it, Manifest.permission.ACCESS_COARSE_LOCATION) } == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true
        }
        return false
    }

    private fun requestPermissions() {
        activity?.let { ActivityCompat.requestPermissions(it, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_ID) }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLastLocation()
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        // Another interface callback
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
    }
}
