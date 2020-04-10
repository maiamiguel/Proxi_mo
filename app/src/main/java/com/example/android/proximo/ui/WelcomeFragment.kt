package com.example.android.proximo.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.android.proximo.R

/**
 * A simple [Fragment] subclass.
 */
class WelcomeFragment : Fragment() {
    private val ARG_OBJECT = "object"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_welcome, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
            val titleTextView = view.findViewById<TextView>(R.id.titleText)
            val descriptionTextView = view.findViewById<TextView>(R.id.descriptionText)
            val img = view.findViewById<ImageView>(R.id.img)
            val btn = view.findViewById<Button>(R.id.btnStart)

            val pos = getInt(ARG_OBJECT)

            if (pos == 0){
                img.setImageResource(R.drawable.viewpager_img_1)
                titleTextView.text = getString(R.string.welcome_text)
                descriptionTextView.text = getString(R.string.description1)
                btn.visibility = (View.INVISIBLE);
            }

            if (pos == 1){
                img.setImageResource(R.drawable.viewpager_img_2)
                titleTextView.text = getString(R.string.list_services)
                descriptionTextView.text = getString(R.string.description1)
                btn.visibility = (View.INVISIBLE);
            }

            if (pos == 2){
                img.setImageResource(R.drawable.viewpager_img_3)
                titleTextView.text = getString(R.string.location)
                descriptionTextView.text = getString(R.string.description1)
                btn.visibility = (View.VISIBLE);
                btn.setOnClickListener {
                    findNavController().navigate(R.id.action_viewPagerFragment_to_overviewFragment)
                }
            }

            Log.d("debug", "Args ${getInt(ARG_OBJECT)}")
        }
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