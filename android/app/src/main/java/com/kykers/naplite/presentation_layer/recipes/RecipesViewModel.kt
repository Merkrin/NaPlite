package com.kykers.naplite.presentation_layer.recipes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.Config
import androidx.paging.LivePagedListBuilder
import com.kykers.naplite.business_layer.network.NetworkService
import com.kykers.naplite.business_layer.objects.Order
import com.kykers.naplite.business_layer.objects.RecipeShort
import com.kykers.naplite.objects.Helper.requireValue
import com.kykers.naplite.objects.Helper.value
import com.kykers.naplite.presentation_layer.adapters.data.RecipesSourceFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import java.util.Collections.addAll
import kotlin.collections.ArrayList
import kotlin.math.exp


class RecipesViewModel : ViewModel() {

    companion object {

        const val PAGE_SIZE = 20
        const val PREFETCH_DISTANCE = 15

    }


    /**  LiveData RecipeShort'ов, фабрика догрузки рецптов и конфигурация,
     *  отвечающая за параметры догрузки
     *  @author: DmitriiShkudov
     *  */
    //
    private val factory = RecipesSourceFactory()

    private val config = Config(PAGE_SIZE, PREFETCH_DISTANCE, enablePlaceholders = false)

    internal val recipesShortList
            = LivePagedListBuilder(factory, config).build()
    //

}