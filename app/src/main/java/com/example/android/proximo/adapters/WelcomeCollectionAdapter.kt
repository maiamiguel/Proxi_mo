package com.example.android.proximo.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.android.proximo.ui.WelcomeFragment


class WelcomeCollectionAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    override fun getCount(): Int = 2

    override fun getPageTitle(position: Int): CharSequence {
        return "OBJECT ${(position + 1)}"
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> WelcomeFragment().newInstance(position + 1)
            1 -> WelcomeFragment().newInstance(position + 1)
            2 -> WelcomeFragment().newInstance(position + 1)
            else -> null
        }!!
    }
}