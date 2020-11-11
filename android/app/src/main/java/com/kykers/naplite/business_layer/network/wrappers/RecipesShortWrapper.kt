package com.kykers.naplite.business_layer.network.wrappers

import com.kykers.naplite.business_layer.objects.RecipeShort

/**
 * Класс - обёртка под RecipeShort.
 * @see RecipeShort
 *
 * @author goga133
 */
data class RecipesShortWrapper(val count: Int?, val recipes: ArrayList<RecipeShort>)