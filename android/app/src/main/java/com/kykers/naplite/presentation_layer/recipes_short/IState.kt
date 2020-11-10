package com.kykers.naplite.presentation_layer.recipes_short

import androidx.lifecycle.MutableLiveData


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