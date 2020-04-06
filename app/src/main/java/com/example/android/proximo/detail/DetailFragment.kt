package com.example.android.proximo.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.android.proximo.databinding.FragmentDetailBinding
import com.example.android.proximo.models.TypesOfServices
import com.example.android.proximo.overview.OverviewFragmentDirections
import com.example.android.proximo.overview.PhotoGridAdapter

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

        val selectedTypesOfServices : TypesOfServices = DetailFragmentArgs.fromBundle(arguments!!).selectedTypesOfServices
        val viewModelFactory = DetailViewModelFactory(selectedTypesOfServices, application)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DetailViewModel::class.java)

        binding.viewModel = viewModel

        // Sets the adapter of the photosGrid RecyclerView with clickHandler lambda that
        // tells the viewModel when our property is clicked
        binding.rv.adapter = ServiceItemAdapter(ServiceItemAdapter.OnClickListener {
            viewModel.displayServiceDetails(it)
        })

        // Observe the navigateToSelectedProperty LiveData and Navigate when it isn't null
        // After navigating, call displayPropertyDetailsComplete() so that the ViewModel is ready
        // for another navigation event.
        viewModel.navigateToSelectedProperty.observe(this, Observer {
            if ( null != it ) {
                // Must find the NavController from the Fragment
//                this.findNavController().navigate(OverviewFragmentDirections.actionShowDetail(it))
//                // Tell the ViewModel we've made the navigate call to prevent multiple navigation
//                binding.viewModel.displayServiceDetailsComplete()
            }
        })

        setHasOptionsMenu(true)
        return binding.root
    }
}