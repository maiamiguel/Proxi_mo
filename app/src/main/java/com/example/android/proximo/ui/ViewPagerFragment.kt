package com.example.android.proximo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.example.android.proximo.adapters.WelcomeViewPagerAdapter
import com.example.android.proximo.databinding.ViewPagerFragmentBinding

class ViewPagerFragment : Fragment() {
    private lateinit var demoViewPagerPagerAdapter: WelcomeViewPagerAdapter
    private lateinit var viewPager: ViewPager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = ViewPagerFragmentBinding.inflate(inflater)

        binding.lifecycleOwner = this

        demoViewPagerPagerAdapter = WelcomeViewPagerAdapter(childFragmentManager)
        viewPager = binding.viewPager
        viewPager.adapter = demoViewPagerPagerAdapter
        val dotsIndicator = binding.dotsIndicator
        dotsIndicator.setViewPager(viewPager)

        viewPager.addOnPageChangeListener(object : OnPageChangeListener {
            // This method will be invoked when a new page becomes selected.
            override fun onPageSelected(position: Int) {

            }

            // This method will be invoked when the current page is scrolled
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            // Called when the scroll state changes:
            // SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
            override fun onPageScrollStateChanged(state: Int) {

            }
        })

        return binding.root
    }
}
