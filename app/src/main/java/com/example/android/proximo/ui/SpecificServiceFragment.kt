package com.example.android.proximo.ui

import android.R
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.android.proximo.databinding.SpecificFragmentBinding
import com.example.android.proximo.models.Company
import com.example.android.proximo.viewmodels.SpecificViewModel
import com.example.android.proximo.viewmodels.SpecificViewModelFactory


class SpecificServiceFragment : Fragment() {
    private lateinit var viewModel: SpecificViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val application = requireNotNull(activity).application
        val binding = SpecificFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val specificCompany: Company = SpecificServiceFragmentArgs.fromBundle(requireArguments()).specificCompany
        val viewModelFactory = SpecificViewModelFactory(specificCompany, application)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SpecificViewModel::class.java)

        binding.viewModel = viewModel

        val googleMapsIMG: ImageView = binding.googleMapsIMG
        googleMapsIMG.setOnClickListener(View.OnClickListener {
            if (specificCompany.gmaps_url != ""){
                val intent = Intent()
                intent.action = Intent.ACTION_VIEW
                intent.addCategory(Intent.CATEGORY_BROWSABLE)
                intent.data = Uri.parse(specificCompany.gmaps_url)
                startActivity(intent)
            }
            else{
                Toast.makeText(context, "No link is provided..", Toast.LENGTH_SHORT).show()
            }
        })

        val facebookIMG: ImageView = binding.facebookIMG
        facebookIMG.setOnClickListener(View.OnClickListener {
            if (specificCompany.social.facebook != ""){
                val intent = Intent()
                intent.action = Intent.ACTION_VIEW
                intent.addCategory(Intent.CATEGORY_BROWSABLE)
                intent.data = Uri.parse(specificCompany.social.facebook)
                startActivity(intent)
            }
            else{
                Toast.makeText(context, "No link is provided..", Toast.LENGTH_SHORT).show()
            }
        })

        val instagramIMG: ImageView = binding.instagramIMG
        instagramIMG.setOnClickListener(View.OnClickListener {
            if (specificCompany.social.instagram != ""){
                val intent = Intent()
                intent.action = Intent.ACTION_VIEW
                intent.addCategory(Intent.CATEGORY_BROWSABLE)
                intent.data = Uri.parse(specificCompany.social.instagram)
                startActivity(intent)
            }
            else{
                Toast.makeText(context, "No link is provided..", Toast.LENGTH_SHORT).show()
            }
        })

        (activity as? AppCompatActivity)?.supportActionBar?.title = specificCompany.name

        return binding.root
    }
}