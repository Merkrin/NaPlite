package com.kykers.naplite.presentation_layer.adapters.data

import androidx.paging.PositionalDataSource
import com.kykers.naplite.business_layer.network.NetworkRepository
import com.kykers.naplite.business_layer.objects.Order
import com.kykers.naplite.business_layer.objects.RecipeShort
import kotlinx.coroutines.CoroutineExceptionHandler
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
class RecipesSource: PositionalDataSource<RecipeShort>() {

    companion object {

        const val AVAILABLE_LOAD_CAPACITY = 20

    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<RecipeShort>) {

        val fixedSize = if (params.requestedLoadSize >= AVAILABLE_LOAD_CAPACITY) {

            AVAILABLE_LOAD_CAPACITY

        } else {

            params.requestedLoadSize

        }

        CoroutineScope(IO).launch {
            val part = execute(params.requestedStartPosition, fixedSize)
            withContext(Main) { callback.onResult(part, params.requestedStartPosition) }
        }

    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<RecipeShort>) {
        val portion = execute(params.startPosition, params.loadSize)
        callback.onResult(portion)
    }


    private fun execute(from: Int, size: Int) = NetworkRepository.getRecipes(Order.FAVOURITES, from, size)

}