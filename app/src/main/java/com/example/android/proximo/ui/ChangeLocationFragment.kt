package com.example.android.proximo.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.proximo.R
import com.example.android.proximo.databinding.ChangeLocationFragmentBinding
import com.example.android.proximo.viewmodels.ChangeLocationViewModel

class ChangeLocationFragment : Fragment() {
    private val viewModel: ChangeLocationViewModel by lazy {
        ViewModelProvider(this).get(ChangeLocationViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = ChangeLocationFragmentBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.ackBTN.setOnClickListener {
            // TODO: Change county to use from spinner
            findNavController().navigate(ChangeLocationFragmentDirections.actionChangeLocationFragmentToTypesServicesFragment("Estarreja"))
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

}
