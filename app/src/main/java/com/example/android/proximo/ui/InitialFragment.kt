package com.example.android.proximo.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController

import com.example.android.proximo.R
import com.example.android.proximo.databinding.FragmentInitialBinding
import com.example.android.proximo.databinding.LocationFragmentBinding
import com.example.android.proximo.viewmodels.LocationViewModel

class InitialFragment : Fragment() {

    /**
     * Inflates the layout with Data Binding, sets its lifecycle owner to the TypeServicesFragment
     * to enable Data Binding to observe LiveData, and sets up the RecyclerView with an adapter.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentInitialBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        binding.btn.setOnClickListener {
            findNavController().navigate(R.id.action_initialFragment_to_viewPagerFragment)
        }

        return binding.root
    }
}
