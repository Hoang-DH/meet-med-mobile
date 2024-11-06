package com.example.doctorapp.moduleDoctor.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class WorkingPagerAdapter(
    fragment: Fragment,
    private val fragments: List<Fragment>,
    private val titles: List<String>
) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    fun getTitle(position: Int): String {
        return titles[position]
    }
}