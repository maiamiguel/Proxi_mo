package com.example.android.proximo.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.android.proximo.R
import com.example.android.proximo.viewmodels.OverviewViewModel
import com.example.android.proximo.adapters.TypeServicesAdapter
import com.example.android.proximo.databinding.FragmentListServicesTypesBinding

/**
 * This fragment shows the the status of the Mars real-estate web services transaction.
 */
class TypeServicesFragment : Fragment() {

    /**
     * Lazily initialize our [OverviewViewModel].
     */
    private val viewModel: OverviewViewModel by lazy {
        ViewModelProviders.of(this).get(OverviewViewModel::class.java)
    }

    /**
     * Inflates the layout with Data Binding, sets its lifecycle owner to the TypeServicesFragment
     * to enable Data Binding to observe LiveData, and sets up the RecyclerView with an adapter.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentListServicesTypesBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the OverviewViewModel
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
                this.findNavController().navigate(TypeServicesFragmentDirections.actionShowDetail(it))
                // Tell the ViewModel we've made the navigate call to prevent multiple navigation
                viewModel.displayPropertyDetailsComplete()
            }
        })

        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            // Handle the back button event
            Log.d("debug", "BACK BUTTON")
        }

        setHasOptionsMenu(true)
        return binding.root
    }

    /**
     * Inflates the overflow menu that contains filtering options.
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    /**
     * Updates the filter in the [OverviewViewModel] when the menu items are selected from the
     * overflow menu.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        viewModel.updateFilter(
//                when (item.itemId) {
//                    R.id.show_rent_menu -> MarsApiFilter.SHOW_RENT
//                    R.id.show_buy_menu -> MarsApiFilter.SHOW_BUY
//                    else -> MarsApiFilter.SHOW_ALL
//                }
//        )
        return true
    }
}
