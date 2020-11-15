package com.kykers.naplite.business_layer.network

import com.kykers.naplite.business_layer.objects.Order
import com.kykers.naplite.business_layer.objects.RecipeShort

object NetworkRepository {

    fun getRecipes(order: Order, skipAmount: Int, amount: Int): List<RecipeShort> {
        try {
            with(
                NetworkService.retrofitService().getRecipes(order, skipAmount).execute().body()
            ) {

                // Успешная загрузка
                return if (this != null)
                    recipes.subList(0, amount)
                else
                    emptyList()
            }

        } catch (e: Throwable) {
            return emptyList()
        }
    }
}