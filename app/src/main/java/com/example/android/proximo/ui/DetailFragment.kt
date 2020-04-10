package com.example.android.proximo.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.android.proximo.R
import com.example.android.proximo.databinding.FragmentDetailBinding
import com.example.android.proximo.viewmodels.DetailViewModel
import com.example.android.proximo.viewmodels.DetailViewModelFactory
import com.example.android.proximo.adapters.ServiceItemAdapter
import com.example.android.proximo.models.TypesOfServices
import com.example.android.proximo.viewmodels.OverviewViewModel

/**
 * This [Fragment] shows the detailed information about a selected piece of Mars real estate.
 * It sets this information in the [DetailViewModel], which it gets as a Parcelable property
 * through Jetpack Navigation's SafeArgs.
 */
class DetailFragment : Fragment() {
    private lateinit var viewModel : DetailViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val application = requireNotNull(activity).application
        val binding = FragmentDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val selectedTypesOfServices : String = DetailFragmentArgs.fromBundle(requireArguments()).selectedTypesOfServices
        val viewModelFactory = DetailViewModelFactory(selectedTypesOfServices, application)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DetailViewModel::class.java)

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
//                this.findNavController().navigate(OverviewFragmentDirections.actionShowDetail(it))
//                // Tell the ViewModel we've made the navigate call to prevent multiple navigation
//                binding.viewModel.displayServiceDetailsComplete()
            }
        })

        (activity as? AppCompatActivity)?.supportActionBar?.title = selectedTypesOfServices

        //setHasOptionsMenu(true)
        return binding.root
    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.overflow_menu, menu)
//        super.onCreateOptionsMenu(menu, inflater)
//    }
//
//    /**
//     * Updates the filter in the [OverviewViewModel] when the menu items are selected from the
//     * overflow menu.
//     */
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
////        viewModel.updateFilter(
////                when (item.itemId) {
////                    R.id.show_rent_menu -> MarsApiFilter.SHOW_RENT
////                    R.id.show_buy_menu -> MarsApiFilter.SHOW_BUY
////                    else -> MarsApiFilter.SHOW_ALL
////                }
////        )
//        return true
//    }
}