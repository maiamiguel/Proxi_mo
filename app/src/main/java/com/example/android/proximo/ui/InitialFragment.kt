package com.example.android.proximo.ui

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.android.proximo.R
import com.example.android.proximo.databinding.FragmentInitialBinding

class InitialFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentInitialBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        val sharedPref = this.activity?.getPreferences(Context.MODE_PRIVATE)
        val tutorialDone = sharedPref!!.getBoolean(getString(R.string.introTutorial), false)

        val textView = binding.title
        val typeface = Typeface.createFromAsset(context?.assets, "fonts/Proxima_Nova_Thin.otf")
        textView.typeface = typeface

        binding.btn.setOnClickListener {
            if (tutorialDone) {
                findNavController().navigate(R.id.action_initialFragment_to_locationFragment)
            } else {
                findNavController().navigate(R.id.action_initialFragment_to_viewPagerFragment)
            }
        }

        return binding.root
    }
}
