package com.kykers.naplite.ui.recipe_activity

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.kykers.naplite.R

class RecipeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)
        setSupportActionBar(findViewById(R.id.toolbar))

        val viewModel = ViewModelProviders.of(this).get(RecipeActivityViewModel::class.java)

        viewModel.recipe.observe(viewLifecycleOwner, )
    }
}