package com.kykers.naplite.presentation_layer.adapters

import android.net.Uri
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.kykers.naplite.business_layer.objects.RecipeShort
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recipes_short_item.view.*
import java.time.ZoneId

/**
 * Холдер RecipeShort'ов
 *
 *
 * @author DmitriiShkudov
 */

class RecipeShortViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun binding(recipe: RecipeShort) { with(itemView) {

            // TODO: исправить (на бэке?)
            val fixedRecipeTitle = recipe.title?.replace("&quot;", "\"")

            // TODO: исправить http на https на бэке
            val fixedUrl = recipe.image300XUrl?.replace("http", "https")
            Picasso.get().load(fixedUrl).into(civ_recipes_short)

            tv_recipes_short_title.text = fixedRecipeTitle
            tv_recipes_short_comments.text = recipe.comments.toString()
            tv_recipes_short_date.text = recipe.
                                         pubDateTime.
                                         toInstant().
                                         atZone(ZoneId.systemDefault()).
                                         toLocalDate().
                                         toString()

        }
    }
}