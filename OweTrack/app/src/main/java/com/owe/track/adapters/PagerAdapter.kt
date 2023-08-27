package com.owe.track.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.owe.track.data.Debt
import com.owe.track.data.IntervalUnit
import com.owe.track.fragments.DebtFilter
import com.owe.track.fragments.DebtFragment

class PagerAdapter(
    fragment: Fragment,
    private val onClick: (Debt) -> Unit
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment = when(position) {
        0 -> DebtFragment(DebtFilter.Today, onClick)
        1 -> DebtFragment(DebtFilter.Active, onClick)
        2 -> DebtFragment(DebtFilter.Completed, onClick)
        else -> DebtFragment(DebtFilter.Active) { }
    }
}