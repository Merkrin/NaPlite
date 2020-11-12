package com.kykers.naplite.ui.recipesShort_fragment.presentation

import android.graphics.Bitmap
import android.media.audiofx.DynamicsProcessing
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.Config
import androidx.paging.LivePagedListBuilder
import com.kykers.naplite.business_layer.network.Event
import com.kykers.naplite.business_layer.network.NetworkService
import com.kykers.naplite.business_layer.network.RecipesApi
import com.kykers.naplite.business_layer.network.wrappers.RecipesShortWrapper
import com.kykers.naplite.business_layer.objects.Order
import com.kykers.naplite.business_layer.objects.RecipeShort
import com.kykers.naplite.ui.recipesShort_fragment.factory.RecipesSourceFactory
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.*


class RecipesViewModel : ViewModel() {

    private companion object {
        const val PAGE_SIZE = 20
        const val PREFETCH_DISTANCE = 10
    }

    /**  LiveData RecipeShort'ов, фабрика догрузки рецептов и конфигурация,
     *  отвечающая за параметры догрузки
     *  @author DmitriiShkudov
     *  */
    //

    private var factory = RecipesSourceFactory(this)

    private var config = Config(PAGE_SIZE, PREFETCH_DISTANCE, enablePlaceholders = false)

    internal var recipesShortList = LivePagedListBuilder(factory, config).build()

    internal val recipesShortEvent = MutableLiveData<Event<RecipesShortWrapper>>().apply {

        value = Event.loading()

    }

    //

    internal inner class NetworkRepository {

        /**
         * Метод для загрузки @param amount элементов, начиная с @param skipAmount элемента
         * в порядке @param order
         * @see Order
         * @see RecipesSourceFactory
         *
         *
         * @author DmitriiShkudov
         *
         * */
        suspend fun getRecipes(order: Order, skipAmount: Int, amount: Int): List<RecipeShort?> {

            with (NetworkService.retrofitService().getRecipes(order, skipAmount)) {

                if (data != null) {

                    recipesShortEvent.postValue(Event.success(data))
                    return data.recipes!!.subList(0, amount)

                } else {

                    recipesShortEvent.postValue(Event.error(error))
                    return emptyList()

                }

            }

        }

    }

}