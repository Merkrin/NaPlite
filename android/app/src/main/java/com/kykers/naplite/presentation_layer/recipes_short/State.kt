package com.kykers.naplite.presentation_layer.recipes_short


/**
 * Перечисление всех возможных видов UI
 * @author DmitriiShkudov
 * @see RecipesFragment
 * @see State
 * */

enum class State {

    LOADING, UPDATED, UNKNOWN_ERROR, SERVER_ERROR, NETWORK_ERROR

}