package com.example.android.proximo.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
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
    private lateinit var viewModel : ServicesViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val application = requireNotNull(activity).application
        val binding = FragmentListServicesBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val selectedTypesOfServices : Category = ServicesFragmentArgs.fromBundle(requireArguments()).selectedTypesOfServices
        val county : String = ServicesFragmentArgs.fromBundle(requireArguments()).county

        // Setting actionBar title
        //(activity as? AppCompatActivity)?.supportActionBar?.title = selectedTypesOfServices.display

        val viewModelFactory = ServicesViewModelFactory(selectedTypesOfServices, county, application)

        viewModel = ViewModelProvider(this, viewModelFactory).get(ServicesViewModel::class.java)

        binding.viewModel = viewModel

        // Sets the adapter of the photosGrid RecyclerView with clickHandler lambda that
        // tells the viewModel when our property is clicked
        val adapter = ServiceItemAdapter(ServiceItemAdapter.OnClickListener {
            viewModel.displayServiceDetails(it)
        })

        binding.rv.adapter = adapter

        viewModel.properties.observe(
            this.viewLifecycleOwner,
            Observer { t ->
                t.let {
                    // Sets new Data to RecyclerView
                    Log.d("debug", "setServicesList changed")
                    adapter.setServicesList(it)
                }
            })

        // Observe the navigateToSelectedProperty LiveData and Navigate when it isn't null
        // After navigating, call displayPropertyDetailsComplete() so that the ViewModel is ready
        // for another navigation event.
        viewModel.navigateToSelectedProperty.observe(viewLifecycleOwner, Observer {
            if ( null != it ) {
                // Must find the NavController from the Fragment
                this.findNavController().navigate(ServicesFragmentDirections.actionSpecificService(it))
                // Tell the ViewModel we've made the navigate call to prevent multiple navigation
                viewModel.displayServiceDetailsComplete()
            }
        })

        return binding.root
    }
}