package com.kykers.naplite.presentation_layer.recipes_short.adapters.data

import androidx.paging.DataSource
import com.kykers.naplite.business_layer.objects.RecipeShort
import com.kykers.naplite.presentation_layer.recipes_short.presentation.RecipesViewModel

/**
 * Класс, отвечающий за своевременную догрузку RecipeShort
 *
 *
 * @author DmitriiShkudov
 */
class RecipesSourceFactory(private val viewModel: RecipesViewModel)
    : DataSource.Factory<Int, RecipeShort>() {

    override fun create() = RecipesSource(viewModel)

}