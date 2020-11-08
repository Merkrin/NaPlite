package com.kykers.naplite.business_layer.network

import android.util.Log
import com.kykers.naplite.business_layer.objects.Order
import com.kykers.naplite.business_layer.objects.RecipeShort
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class NetworkRepository {

    companion object {


        /**
         * Метод для загрузки @param amount элементов, начиная с @param skipAmount элемента
         * в порядке @param order
         * @see Order
         * TODO: @exception TEST предстоит обработать
         *
         *
         * @author DmitriiShkudov
         *
         * */
        fun getRecipes(order: Order, skipAmount: Int, amount: Int) = try {

                with(NetworkService.retrofitService().getRecipes(order, skipAmount).execute().body()) {

                    // Успешная загрузка
                    if (this != null) {

                        recipes.subList(0, amount)

                    } else throw Exception("TEST")
                }

                } catch (e: Throwable) { e.printStackTrace(); throw java.lang.Exception("TEST") }
        }
    }