package com.kykers.naplite.presentation_layer.adapters.data

import androidx.paging.DataSource
import com.kykers.naplite.business_layer.objects.RecipeShort
import javax.xml.transform.Source

/**
 * Класс, отвечающий за своевременную догрузку RecipeShort
 *
 *
 * @author DmitriiShkudov
 */
class RecipesSourceFactory: DataSource.Factory<Int, RecipeShort>() {
    override fun create() = RecipesSource()
}