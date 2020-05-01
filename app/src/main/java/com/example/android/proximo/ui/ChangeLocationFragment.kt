package com.example.android.proximo.ui

import android.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.proximo.databinding.ChangeLocationFragmentBinding
import com.example.android.proximo.viewmodels.ChangeLocationViewModel
import com.google.android.material.snackbar.Snackbar

class ChangeLocationFragment : Fragment(), AdapterView.OnItemSelectedListener {
    private var selectedDistrict: String? = null
    private var selectedCounty: String? = null
    private val counties: MutableList<String> = ArrayList()
    private val districts: MutableList<String> = ArrayList()
    private lateinit var spinnerDistrict: Spinner
    private lateinit var spinnerCounty : Spinner
    private lateinit var dataAdapterCounty: ArrayAdapter<String>

    private val viewModel: ChangeLocationViewModel by lazy {
        ViewModelProvider(this).get(ChangeLocationViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = ChangeLocationFragmentBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        // Spinner element
        spinnerDistrict = binding.district
        spinnerCounty = binding.county

        // Spinner Drop down elements
        districts.add(resources.getString(com.example.android.proximo.R.string.district))

        viewModel.districts.observe(viewLifecycleOwner, Observer {
            districts.addAll(it)
        })

        viewModel.status.observe(viewLifecycleOwner, Observer {
            Log.d("debug", "STATUS CHANGED ${it}")
        })

        // Creating adapter for District spinner
        val dataAdapterDistrict: ArrayAdapter<String> = ArrayAdapter<String>(context!!, R.layout.simple_spinner_item, districts)
        // Drop down layout style - list view with radio button
        dataAdapterDistrict.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        // attaching data adapter to spinner
        spinnerDistrict.adapter = dataAdapterDistrict
        // Spinner click listener
        spinnerDistrict.onItemSelectedListener = this

        // ------------------------------------------------------------------------------------- //

        // Spinner Drop down elements
        counties.add(resources.getString(com.example.android.proximo.R.string.county))

        viewModel.counties.observe(viewLifecycleOwner, Observer {
            counties.clear()
            counties.add(resources.getString(com.example.android.proximo.R.string.county))
            counties.addAll(it)
        })

        // Creating adapter for County spinner
        dataAdapterCounty = ArrayAdapter<String>(context!!, R.layout.simple_spinner_item, counties)
        // Drop down layout style - list view with radio button
        dataAdapterCounty.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        // attaching data adapter to spinner
        spinnerCounty.adapter = dataAdapterCounty
        // Spinner click listener
        spinnerCounty.onItemSelectedListener = this

        binding.ackBTN.setOnClickListener {
            if (selectedCounty != null && selectedCounty != "Concelho:") {
                findNavController().navigate(ChangeLocationFragmentDirections.actionChangeLocationFragmentToTypesServicesFragment(selectedCounty!!, selectedDistrict!!))
            } else {
                Snackbar.make(binding.rootLayout, "Por favor introduza uma localização válida.. ", Snackbar.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    private fun clearCounties() {
        val str: String = spinnerCounty.selectedItem.toString()
        dataAdapterCounty.remove(str)
        dataAdapterCounty.notifyDataSetChanged()
        spinnerCounty.setSelection(0)

        counties.clear()
        counties.add(resources.getString(com.example.android.proximo.R.string.county))
        selectedCounty = null
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (parent?.id == com.example.android.proximo.R.id.district) {
            if (parent.getItemAtPosition(position) != "Distrito:") {
                viewModel.searchCounties(parent.getItemAtPosition(position).toString())
                selectedDistrict = parent.getItemAtPosition(position).toString()
            }
            if (parent.getItemAtPosition(position) == "Distrito:" && selectedCounty != null) {
                clearCounties()
            }
        }

        if (parent?.id == com.example.android.proximo.R.id.county) {
            if (parent.getItemAtPosition(position) != "Concelho:") {
                selectedCounty = parent.getItemAtPosition(position).toString()
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }
}
