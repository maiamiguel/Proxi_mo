package com.example.android.proximo.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.android.proximo.ui.WelcomeFragment

class WelcomeCollectionAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT ) {

    override fun getCount(): Int = 3

    override fun getPageTitle(position: Int): CharSequence {
        return "OBJECT ${(position + 1)}"
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> WelcomeFragment().newInstance(position)
            1 -> WelcomeFragment().newInstance(position)
            2 -> WelcomeFragment().newInstance(position)
            else -> null
        }!!
    }
}