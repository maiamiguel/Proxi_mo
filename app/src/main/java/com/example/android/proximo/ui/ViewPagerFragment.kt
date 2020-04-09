package com.example.android.proximo.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.example.android.proximo.adapters.WelcomeCollectionAdapter
import com.example.android.proximo.databinding.ViewPagerFragmentBinding
import com.example.android.proximo.viewmodels.WelcomeViewModel


class ViewPagerFragment : Fragment() {
    private lateinit var demoCollectionPagerAdapter: WelcomeCollectionAdapter
    private lateinit var viewPager: ViewPager

    /**
     * Lazily initialize our [WelcomeViewModel].
     */
    private val viewModel: WelcomeViewModel by lazy {
        ViewModelProviders.of(this).get(WelcomeViewModel::class.java)
    }

    /**
     * Inflates the layout with Data Binding, sets its lifecycle owner to the OverviewFragment
     * to enable Data Binding to observe LiveData, and sets up the RecyclerView with an adapter.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = ViewPagerFragmentBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel

        demoCollectionPagerAdapter = WelcomeCollectionAdapter(childFragmentManager)
        viewPager = binding.viewPager
        viewPager.adapter = demoCollectionPagerAdapter
        val dotsIndicator = binding.dotsIndicator
        dotsIndicator.setViewPager(viewPager)

        // Attach the page change listener inside the activity

        // Attach the page change listener inside the activity
        viewPager.addOnPageChangeListener(object : OnPageChangeListener {
            // This method will be invoked when a new page becomes selected.
            override fun onPageSelected(position: Int) {
                Log.d("debug","Selected page position: $position")
            }

            // This method will be invoked when the current page is scrolled
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                // Code goes here
                Log.d("debug", "onPageScrolled")
            }

            // Called when the scroll state changes:
            // SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
            override fun onPageScrollStateChanged(state: Int) {
                // Code goes here
                Log.d("debug", "onPageScrollStateChanged")
            }
        })

        return binding.root
    }
}
