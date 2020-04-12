package com.example.android.proximo.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.android.proximo.R
import com.example.android.proximo.viewmodels.ServicesViewModel
import com.example.android.proximo.viewmodels.ServicesViewModelFactory
import com.example.android.proximo.adapters.ServiceItemAdapter
import com.example.android.proximo.databinding.FragmentListServicesBinding

/**
 * This [Fragment] shows the detailed information about a selected piece of Mars real estate.
 * It sets this information in the [ServicesViewModel], which it gets as a Parcelable property
 * through Jetpack Navigation's SafeArgs.
 */
class ServicesFragment : Fragment() {
    private lateinit var viewModel : ServicesViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val application = requireNotNull(activity).application
        val binding = FragmentListServicesBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val selectedTypesOfServices : String = ServicesFragmentArgs.fromBundle(requireArguments()).selectedTypesOfServices
        val viewModelFactory = ServicesViewModelFactory(selectedTypesOfServices, application)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ServicesViewModel::class.java)

        binding.viewModel = viewModel

        // Sets the adapter of the photosGrid RecyclerView with clickHandler lambda that
        // tells the viewModel when our property is clicked
        val adapter = ServiceItemAdapter(ServiceItemAdapter.OnClickListener {
            viewModel.displayServiceDetails(it)
        })

        binding.rv.adapter = adapter

        // Observe the navigateToSelectedProperty LiveData and Navigate when it isn't null
        // After navigating, call displayPropertyDetailsComplete() so that the ViewModel is ready
        // for another navigation event.
        viewModel.navigateToSelectedProperty.observe(viewLifecycleOwner, Observer {
            if ( null != it ) {
                Log.d("debug", "CLICK")
                // Must find the NavController from the Fragment
                this.findNavController().navigate(ServicesFragmentDirections.actionSpecificService(it))
                // Tell the ViewModel we've made the navigate call to prevent multiple navigation
                viewModel.displayServiceDetailsComplete()
            }
        })

        (activity as? AppCompatActivity)?.supportActionBar?.title = selectedTypesOfServices
        return binding.root
    }
}