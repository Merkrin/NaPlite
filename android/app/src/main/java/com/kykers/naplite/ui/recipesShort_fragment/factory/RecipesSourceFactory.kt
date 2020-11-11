package com.kykers.naplite.ui.recipesShort_fragment.factory

import androidx.paging.DataSource
import com.kykers.naplite.business_layer.objects.RecipeShort
import com.kykers.naplite.ui.recipesShort_fragment.presentation.RecipesViewModel

/**
 * Класс, отвечающий за своевременную догрузку RecipeShort
 *
 *
 * @author DmitriiShkudov
 */
class RecipesSourceFactory(private val viewModel: RecipesViewModel) :
    DataSource.Factory<Int, RecipeShort>() {

    override fun create() = RecipesSource(viewModel)
}