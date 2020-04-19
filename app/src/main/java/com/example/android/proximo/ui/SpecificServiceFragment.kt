package com.example.android.proximo.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.android.proximo.R
import com.example.android.proximo.databinding.SpecificFragmentBinding
import com.example.android.proximo.models.Company
import com.example.android.proximo.viewmodels.SpecificViewModel
import com.example.android.proximo.viewmodels.SpecificViewModelFactory
import com.google.android.material.snackbar.Snackbar

class SpecificServiceFragment : Fragment() {
    private lateinit var viewModel: SpecificViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val application = requireNotNull(activity).application
        val binding = SpecificFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val specificCompany: Company = SpecificServiceFragmentArgs.fromBundle(requireArguments()).specificCompany
        // Setting action bar title
        (activity as? AppCompatActivity)?.supportActionBar?.title = specificCompany.name

        val viewModelFactory = SpecificViewModelFactory(specificCompany, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SpecificViewModel::class.java)

        binding.viewModel = viewModel

        val googleMapsIMG: ImageView = binding.googleMapsIMG
        if (specificCompany.gmaps_url == "") {
            googleMapsIMG.alpha = 0.3f
        } else {
            googleMapsIMG.setOnClickListener {
                val intent = Intent()
                intent.action = Intent.ACTION_VIEW
                intent.addCategory(Intent.CATEGORY_BROWSABLE)
                intent.data = Uri.parse(specificCompany.gmaps_url)
                startActivity(intent)
            }
        }

        val facebookIMG: ImageView = binding.facebookIMG
        if (specificCompany.social.facebook == "") {
            facebookIMG.alpha = 0.3f
        } else {
            facebookIMG.setOnClickListener {
                val intent = Intent()
                intent.action = Intent.ACTION_VIEW
                intent.addCategory(Intent.CATEGORY_BROWSABLE)
                intent.data = Uri.parse(specificCompany.social.facebook)
                startActivity(intent)
            }
        }

        val instagramIMG: ImageView = binding.instagramIMG
        if (specificCompany.social.instagram == "") {
            instagramIMG.alpha = 0.3f
        } else {
            instagramIMG.setOnClickListener {
                val intent = Intent()
                intent.action = Intent.ACTION_VIEW
                intent.addCategory(Intent.CATEGORY_BROWSABLE)
                intent.data = Uri.parse(specificCompany.social.instagram)
                startActivity(intent)
            }
        }

        return binding.root
    }
}