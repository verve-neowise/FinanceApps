package com.fininace.funds.income.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.fininace.funds.income.data.Goal
import com.fininace.funds.income.data.GoalType
import com.fininace.funds.income.fragments.GoalsFragment

class PagerAdapter(
    fragment: Fragment,
    private val onClick: (Goal) -> Unit
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment = when(position) {
        0 -> GoalsFragment(GoalType.Active, onClick)
        1 -> GoalsFragment(GoalType.Archived, onClick)
        else -> GoalsFragment(GoalType.None, onClick)
    }
}