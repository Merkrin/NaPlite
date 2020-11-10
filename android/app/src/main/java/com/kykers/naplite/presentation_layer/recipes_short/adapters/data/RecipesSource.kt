package com.kykers.naplite.presentation_layer.recipes_short.adapters.data

import android.os.Parcel
import android.util.Log
import androidx.paging.PositionalDataSource
import com.kykers.naplite.business_layer.objects.Order
import com.kykers.naplite.business_layer.objects.RecipeShort
import com.kykers.naplite.objects.Helper.requireValue
import com.kykers.naplite.presentation_layer.recipes_short.State
import com.kykers.naplite.presentation_layer.recipes_short.presentation.RecipesFragment
import com.kykers.naplite.presentation_layer.recipes_short.presentation.RecipesViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


/**
 * Класс, отвечающий за своевременную догрузку RecipeShort
 *
 *
 * @author DmitriiShkudov
 */
class RecipesSource(private val viewModel: RecipesViewModel): PositionalDataSource<RecipeShort>() {

    companion object {

        const val AVAILABLE_LOAD_CAPACITY = 20

    }


    /**
     * Загрузка первой порции
     * */
    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<RecipeShort>) {

        val fixedSize = if (params.requestedLoadSize >= AVAILABLE_LOAD_CAPACITY) {

            AVAILABLE_LOAD_CAPACITY

        } else {

            params.requestedLoadSize

        }

        /**
         * Загрузка порции в зависимости от параметров
         * */
        val part = execute(params.requestedStartPosition, fixedSize)

        /**
         * Если порция загрузилась, то вызываем callback, иначе - пробуем заново
         * */
        if (part.isNotEmpty()) {

            callback.onResult(part, params.requestedStartPosition)

        } else {

            loadInitial(params, callback)

        }

    }

    /**
     * Загрузка порции
     * */
    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<RecipeShort>) {

        if (viewModel.state.requireValue() != State.NETWORK_ERROR) {

            viewModel.state.postValue(State.LOADING)

        }

        /**
         * Загрузка порции в зависимости от параметров
         * */
        val part = execute(params.startPosition, params.loadSize)

        /**
         * Если порция загрузилась, то вызываем callback, иначе - пробуем заново
         * */
        if (part.isNotEmpty()) {

            callback.onResult(part)

        } else {

            loadRange(params, callback)

        }

    }

    private fun execute(from: Int, size: Int) = viewModel.NetworkRepository().getRecipes(Order.COMMENTS, from, size)

}