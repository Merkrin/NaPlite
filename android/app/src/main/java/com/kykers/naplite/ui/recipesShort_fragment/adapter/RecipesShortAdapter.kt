package com.kykers.naplite.ui.recipesShort_fragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.kykers.naplite.R
import com.kykers.naplite.business_layer.objects.RecipeShort


/**
 * Адаптер RecipeShort'ов
 *
 *
 * @author DmitriiShkudov
 */

class RecipesShortAdapter : PagedListAdapter<RecipeShort, RecipeShortViewHolder>(CALLBACK) {

    companion object {

        val CALLBACK = object : DiffUtil.ItemCallback<RecipeShort>() {

            override fun areItemsTheSame(oldItem: RecipeShort, newItem: RecipeShort) =
                oldItem.title == newItem.title

            override fun areContentsTheSame(oldItem: RecipeShort, newItem: RecipeShort) = false
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        RecipeShortViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_recipe_short, parent, false)
        )

    override fun onBindViewHolder(holder: RecipeShortViewHolder, position: Int) =
        holder.binding(getItem(position)!!)

}