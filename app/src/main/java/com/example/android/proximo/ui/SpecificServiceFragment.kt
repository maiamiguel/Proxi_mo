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
        googleMapsIMG.setOnClickListener {
            if (specificCompany.gmaps_url != "") {
                val intent = Intent()
                intent.action = Intent.ACTION_VIEW
                intent.addCategory(Intent.CATEGORY_BROWSABLE)
                intent.data = Uri.parse(specificCompany.gmaps_url)
                startActivity(intent)
            } else {
                val contextView: View = binding.contextView
                Snackbar.make(contextView, R.string.no_info_provided, Snackbar.LENGTH_SHORT).show();
            }
        }

        val facebookIMG: ImageView = binding.facebookIMG
        facebookIMG.setOnClickListener {
            if (specificCompany.social.facebook != "") {
                val intent = Intent()
                intent.action = Intent.ACTION_VIEW
                intent.addCategory(Intent.CATEGORY_BROWSABLE)
                intent.data = Uri.parse(specificCompany.social.facebook)
                startActivity(intent)
            } else {
                val contextView: View = binding.contextView
                Snackbar.make(contextView, R.string.no_info_provided, Snackbar.LENGTH_SHORT).show();
            }
        }

        val instagramIMG: ImageView = binding.instagramIMG
        instagramIMG.setOnClickListener {
            if (specificCompany.social.instagram != "") {
                val intent = Intent()
                intent.action = Intent.ACTION_VIEW
                intent.addCategory(Intent.CATEGORY_BROWSABLE)
                intent.data = Uri.parse(specificCompany.social.instagram)
                startActivity(intent)
            } else {
                val contextView: View = binding.contextView
                Snackbar.make(contextView, getString(R.string.no_info_provided), Snackbar.LENGTH_SHORT).show();
            }
        }

        return binding.root
    }
}