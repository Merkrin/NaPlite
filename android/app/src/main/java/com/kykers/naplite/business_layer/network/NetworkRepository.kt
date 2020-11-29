package com.kykers.naplite.business_layer.network

import android.util.Log
import com.kykers.naplite.business_layer.objects.Category
import com.kykers.naplite.business_layer.objects.Order
import com.kykers.naplite.business_layer.objects.RecipeShort

object NetworkRepository {

    fun getRecipes(order: Order, skipAmount: Int, amount: Int): List<RecipeShort> { try {

        with(
            NetworkService.retrofitService().getRecipes(order, skipAmount).execute().body()
        ) {

            // Успешная загрузка
            return if (this != null) {

                recipes.subList(0, amount)

            } else {

                emptyList()
            }
        }

        } catch(e: Throwable) {

            return emptyList()

        }
    }

    fun getCategories(): ArrayList<Category> { try {

        with(
            NetworkService.retrofitService().getCategories().execute().body()
        ) {
            // Успешная загрузка
            return this as ArrayList<Category>
        }

        } catch(e: Throwable) {

            e.printStackTrace()
            return ArrayList()

        }
    }
}