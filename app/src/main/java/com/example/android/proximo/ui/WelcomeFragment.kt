package com.example.android.proximo.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
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
            val textView: TextView = view.findViewById(R.id.text)
            textView.text = getInt(ARG_OBJECT).toString()
            Log.d("debug", "HERE ${getInt(ARG_OBJECT)}")
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
