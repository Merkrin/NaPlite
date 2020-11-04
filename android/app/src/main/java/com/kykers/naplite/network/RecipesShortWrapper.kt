package com.kykers.naplite.network

import com.kykers.naplite.objects.RecipeShort

/**
 * Класс - обёртка под RecipeShort.
 * @see RecipeShort
 *
 * @author goga133
 */
data class RecipesShortWrapper(val count: Int?, val recipes: ArrayList<RecipeShort>)