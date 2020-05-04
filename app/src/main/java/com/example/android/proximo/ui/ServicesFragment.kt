package com.example.android.proximo.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.proximo.viewmodels.ServicesViewModel
import com.example.android.proximo.viewmodels.ServicesViewModelFactory
import com.example.android.proximo.adapters.ServiceItemAdapter
import com.example.android.proximo.databinding.FragmentListServicesBinding
import com.example.android.proximo.models.Category

class ServicesFragment : Fragment() {
    private lateinit var viewModel: ServicesViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val application = requireNotNull(activity).application
        val binding = FragmentListServicesBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val selectedTypesOfServices: Category = ServicesFragmentArgs.fromBundle(requireArguments()).selectedTypesOfServices
        val county: String = ServicesFragmentArgs.fromBundle(requireArguments()).county

        val viewModelFactory = ServicesViewModelFactory(selectedTypesOfServices, county, application)

        viewModel = ViewModelProvider(this, viewModelFactory).get(ServicesViewModel::class.java)

        binding.viewModel = viewModel

        val adapter = ServiceItemAdapter(ServiceItemAdapter.OnClickListener {
            viewModel.displayServiceDetails(it)
        })

        binding.rv.adapter = adapter

        viewModel.navigateToSelectedProperty.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                this.findNavController().navigate(ServicesFragmentDirections.actionSpecificService(it))
                viewModel.displayServiceDetailsComplete()
            }
        })

        binding.contributeBTN.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_VIEW
            intent.addCategory(Intent.CATEGORY_BROWSABLE)
            intent.data = Uri.parse("https://docs.google.com/forms/d/e/1FAIpQLScKkV0IyOAYYK0Afiju7Rpqlo7wTTIss7gvpsMElXDx-Z0Spw/viewform")
            startActivity(intent)
        }

        return binding.root
    }
}