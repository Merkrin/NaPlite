package com.kykers.naplite.ui.recipesShort_fragment.factory

import com.kykers.naplite.ui.recipesShort_fragment.presentation.RecipesFragment

/**
 * Перечисление всех возможных видов UI
 * @author DmitriiShkudov
 * @see RecipesFragment
 * @see State
 * */

enum class State {
    LOADING, UPDATED, UNKNOWN_ERROR, SERVER_ERROR, NETWORK_ERROR
}