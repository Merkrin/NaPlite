package com.kykers.naplite.presentation_layer.recipes_short.presentation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.Config
import androidx.paging.LivePagedListBuilder
import com.kykers.naplite.business_layer.network.NetworkService
import com.kykers.naplite.business_layer.objects.Order
import com.kykers.naplite.presentation_layer.recipes_short.State
import com.kykers.naplite.presentation_layer.recipes_short.adapters.data.RecipesSourceFactory
import java.net.SocketTimeoutException
import java.net.UnknownHostException


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

    internal var recipesShortList
            = LivePagedListBuilder(factory, config).build()

    internal val state = MutableLiveData<State>().apply {
        value = State.LOADING
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
        fun getRecipes(order: Order, skipAmount: Int, amount: Int) = try {

            with(NetworkService.retrofitService().getRecipes(order, skipAmount).execute().body()) {

                // Успешная загрузка
                if (this != null) {

                    state.postValue(State.UPDATED)
                    recipes.subList(0, amount)

                } else {

                    state.postValue(State.SERVER_ERROR)
                    emptyList()

                }
            }

        }
        catch (e: SocketTimeoutException) {

            state.postValue(State.NETWORK_ERROR)
            emptyList()

        }
        catch (e: UnknownHostException) {

            state.postValue(State.NETWORK_ERROR)
            emptyList()

        }
        catch (e: Throwable) {

            e.printStackTrace()
            state.postValue(State.UNKNOWN_ERROR)
            emptyList()

        }
    }

}