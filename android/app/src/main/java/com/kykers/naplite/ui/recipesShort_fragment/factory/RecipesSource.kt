package com.kykers.naplite.ui.recipesShort_fragment.factory

import android.os.AsyncTask.execute
import com.kykers.naplite.business_layer.network.Event
import com.kykers.naplite.business_layer.network.Status
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import android.os.Parcel
import android.util.Log
import androidx.paging.PositionalDataSource
import com.kykers.naplite.business_layer.objects.Order
import com.kykers.naplite.business_layer.objects.RecipeShort
import com.kykers.naplite.ui.recipesShort_fragment.presentation.RecipesViewModel


/**
 * Класс, отвечающий за своевременную догрузку RecipeShort
 *
 *
 * @author DmitriiShkudov
 */
class RecipesSource(private val viewModel: RecipesViewModel) : PositionalDataSource<RecipeShort>() {

    companion object {
        const val AVAILABLE_LOAD_CAPACITY = 20
    }


    /**
     * Загрузка первой порции
     * */
    override fun loadInitial(
        params: LoadInitialParams,
        callback: LoadInitialCallback<RecipeShort>
    ) {

        if (viewModel.recipesShortEvent.value!!.status != Status.ERROR) {
            viewModel.recipesShortEvent.postValue(Event.loading())
        }

        val fixedSize = if (params.requestedLoadSize >= AVAILABLE_LOAD_CAPACITY) {
            AVAILABLE_LOAD_CAPACITY
        } else {
            params.requestedLoadSize
        }

        /**
         * Загрузка порции в зависимости от параметров
         * */
        CoroutineScope(IO).launch {

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

    }

    /**
     * Загрузка порции
     * */
    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<RecipeShort>) {

        if (viewModel.recipesShortEvent.value!!.status != Status.ERROR) {
            viewModel.recipesShortEvent.postValue(Event.loading())
        }

        CoroutineScope(IO).launch {
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

    }

    private suspend fun execute(from: Int, size: Int)
            = viewModel.NetworkRepository().getRecipes(Order.COMMENTS, from, size)


}