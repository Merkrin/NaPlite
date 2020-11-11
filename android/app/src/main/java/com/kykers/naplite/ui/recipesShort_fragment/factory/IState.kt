package com.kykers.naplite.ui.recipesShort_fragment.factory

import com.kykers.naplite.ui.recipesShort_fragment.presentation.RecipesFragment
/**
 * Интерфейс, определяющий возможный вид UI
 * @author DmitriiShkudov
 * @see RecipesFragment
 * @see State
 * */

interface IState {
    fun updated()
    fun loading()
    fun networkError()
    fun serverError()
    fun unknownError()
}