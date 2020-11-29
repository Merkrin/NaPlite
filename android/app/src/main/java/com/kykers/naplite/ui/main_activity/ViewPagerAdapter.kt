package com.kykers.naplite.ui.main_activity

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.kykers.naplite.ui.main_activity.categories.CategoriesFragment
import com.kykers.naplite.ui.main_activity.recipes_short.RecipesFragment

class ViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> CategoriesFragment()
            else -> RecipesFragment()
        }
    }
}