package com.example.android.proximo.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.proximo.databinding.SpecificFragmentBinding
import com.example.android.proximo.models.Service
import com.example.android.proximo.viewmodels.SpecificViewModel
import com.example.android.proximo.viewmodels.SpecificViewModelFactory

class SpecificServiceFragment : Fragment() {
    private lateinit var viewModel: SpecificViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val application = requireNotNull(activity).application
        val binding = SpecificFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val specificService: Service = SpecificServiceFragmentArgs.fromBundle(requireArguments()).specificService
        val viewModelFactory = SpecificViewModelFactory(specificService, application)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SpecificViewModel::class.java)

        binding.viewModel = viewModel

        // Sets the adapter of the photosGrid RecyclerView with clickHandler lambda that
        // tells the viewModel when our property is clicked
//        val adapter = ServiceItemAdapter(ServiceItemAdapter.OnClickListener {
//            viewModel.displayServiceDetails(it)
//        })
//
//        binding.rv.adapter = adapter
//
//        // Observe the navigateToSelectedProperty LiveData and Navigate when it isn't null
//        // After navigating, call displayPropertyDetailsComplete() so that the ViewModel is ready
//        // for another navigation event.
//        viewModel.navigateToSelectedProperty.observe(viewLifecycleOwner, Observer {
//            if (null != it) {
//                Log.d("debug", "CLICK")
//                // Must find the NavController from the Fragment
//                this.findNavController().navigate(ServicesFragmentD.actionShowDetail(it))
//                // Tell the ViewModel we've made the navigate call to prevent multiple navigation
//                binding.viewModel.displayServiceDetailsComplete()
//            }
//        })
//
//        (activity as? AppCompatActivity)?.supportActionBar?.title = selectedTypesOfServices

        return binding.root
    }
}