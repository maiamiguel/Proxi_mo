package com.example.android.proximo.ui

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.android.proximo.R
import com.example.android.proximo.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {
    private val ARG_OBJECT = "object"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = FragmentWelcomeBinding.inflate(inflater)

        arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
            val sharedPref : SharedPreferences = activity?.getPreferences(Context.MODE_PRIVATE)!!

            val titleTextView = binding.titleText
            val proximaBold = Typeface.createFromAsset(context?.assets, "fonts/Proxima_Nova_Extrabold.otf")
            titleTextView.typeface = proximaBold

            val descriptionTextView = binding.descriptionText
            val proximaRegular = Typeface.createFromAsset(context?.assets, "fonts/Proxima_Nova_Bold.otf")
            titleTextView.typeface = proximaRegular

            val img = binding.img
            val btn = binding.btnStart

            val pos = getInt(ARG_OBJECT)

            if (pos == 0){
                img.setImageResource(R.drawable.viewpagerimg1)
                titleTextView.text = getString(R.string.welcome_text)
                descriptionTextView.text = getString(R.string.description1)
                btn.visibility = (View.INVISIBLE);
            }

            if (pos == 1){
                img.setImageResource(R.drawable.img2)
                titleTextView.text = getString(R.string.list_services)
                descriptionTextView.text = getString(R.string.description2)
                btn.visibility = (View.INVISIBLE);
            }

            if (pos == 2){
                img.setImageResource(R.drawable.viewpager_img_3)
                titleTextView.text = getString(R.string.location)
                descriptionTextView.text = getString(R.string.description3)
                btn.visibility = (View.VISIBLE);
                btn.setOnClickListener {
                    Log.d("debug", "CLICK SHARED PREFERENCES")
                    with (sharedPref.edit()) {
                        putBoolean(getString(R.string.introTutorial), true)
                        this.commit()
                    }
                    findNavController().navigate(R.id.action_viewPagerFragment_to_locationFragment)
                }
            }

            Log.d("debug", "Args ${getInt(ARG_OBJECT)}")
        }
        return binding.root
    }

    // newInstance constructor for creating fragment with arguments
    fun newInstance(page: Int): WelcomeFragment? {
        val fragmentFirst = WelcomeFragment()
        val args = Bundle()
        args.putInt(ARG_OBJECT, page)
        fragmentFirst.arguments = args
        return fragmentFirst
    }
}
