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

class TypeServicesFragment : Fragment() {
    private lateinit var viewModel : TypeServicesViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val application = requireNotNull(activity).application
        val binding = FragmentListServicesTypesBinding.inflate(inflater)

        binding.lifecycleOwner = this

        val county : String = TypeServicesFragmentArgs.fromBundle(requireArguments()).county
        val viewModelFactory = TypeServiceViewModelFactory(county, application)

        viewModel = ViewModelProvider(this, viewModelFactory).get(TypeServicesViewModel::class.java)

        binding.viewModel = viewModel

        binding.photosGrid.adapter = TypeServicesAdapter(TypeServicesAdapter.OnClickListener {
            viewModel.displayPropertyDetails(it)
        })

        viewModel.navigateToSelectedProperty.observe(viewLifecycleOwner, Observer {
            if ( null != it ) {
                this.findNavController().navigate(TypeServicesFragmentDirections.actionShowServices(county, it))
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
