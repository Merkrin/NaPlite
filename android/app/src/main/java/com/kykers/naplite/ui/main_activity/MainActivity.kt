package com.kykers.naplite.ui.main_activity

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.kykers.naplite.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        view_pager.adapter = ViewPagerAdapter(this)
        TabLayoutMediator(tabs, view_pager) {
                tab, position -> view_pager.setCurrentItem(tab.position, true) }.attach()

        tabs.getTabAt(0)!!.text = "Категории"
        tabs.getTabAt(1)!!.text = "Рецепты"

    }
}