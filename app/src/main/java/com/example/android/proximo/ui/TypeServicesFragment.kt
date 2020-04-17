package com.example.android.proximo.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.android.proximo.R
import com.example.android.proximo.adapters.TypeServicesAdapter
import com.example.android.proximo.databinding.FragmentListServicesTypesBinding
import com.example.android.proximo.models.Category
import com.example.android.proximo.viewmodels.ServicesViewModel
import com.example.android.proximo.viewmodels.ServicesViewModelFactory
import com.example.android.proximo.viewmodels.TypeServiceViewModelFactory
import com.example.android.proximo.viewmodels.TypeServicesViewModel

/**
 * This fragment shows the the status of the Mars real-estate web services transaction.
 */
class TypeServicesFragment : Fragment() {
    private lateinit var viewModel : TypeServicesViewModel

    /**
     * Inflates the layout with Data Binding, sets its lifecycle owner to the TypeServicesFragment
     * to enable Data Binding to observe LiveData, and sets up the RecyclerView with an adapter.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val application = requireNotNull(activity).application
        val binding = FragmentListServicesTypesBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        val county : String = TypeServicesFragmentArgs.fromBundle(requireArguments()).county
        val viewModelFactory = TypeServiceViewModelFactory(county, application)

        viewModel = ViewModelProvider(this, viewModelFactory).get(TypeServicesViewModel::class.java)

        binding.viewModel = viewModel

        // Sets the adapter of the photosGrid RecyclerView with clickHandler lambda that
        // tells the viewModel when our property is clicked
        binding.photosGrid.adapter = TypeServicesAdapter(TypeServicesAdapter.OnClickListener {
            viewModel.displayPropertyDetails(it)
        })

        // Observe the navigateToSelectedProperty LiveData and Navigate when it isn't null
        // After navigating, call displayPropertyDetailsComplete() so that the ViewModel is ready
        // for another navigation event.
        viewModel.navigateToSelectedProperty.observe(viewLifecycleOwner, Observer {
            if ( null != it ) {
                // Must find the NavController from the Fragment
                this.findNavController().navigate(TypeServicesFragmentDirections.actionShowServices("Estarreja", it))
                // Tell the ViewModel we've made the navigate call to prevent multiple navigation
                viewModel.displayPropertyDetailsComplete()
            }
        })

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.overflow_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, requireView().findNavController()) || super.onOptionsItemSelected(item)
    }
}
