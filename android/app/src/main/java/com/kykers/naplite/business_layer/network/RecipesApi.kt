package com.kykers.naplite.business_layer.network

import com.kykers.naplite.business_layer.network.wrappers.RecipesShortWrapper
import com.kykers.naplite.business_layer.objects.Order
import com.kykers.naplite.business_layer.objects.RecipeFull
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

/**
 * Api интерфейс для взаимодействия с сервом.
 *
 * @author goga133
 */
interface RecipesApi {

    /**
     * Получение рецепта по Id.
     */
    @GET("recipe/get?")
    suspend fun getRecipe(@Query("id") id: Int): ResponseWrapper<RecipesShortWrapper>

    /**
     * Получение рецептов через сортировку и количество пропущенных.
     */
    @GET("recipes/get?")
    suspend fun getRecipes(
        @Query("order") order: Order = Order.DATE,
        @Query("skip") count: Int = 0
    ): ResponseWrapper<RecipesShortWrapper>


    /**
     * Получение рецептов через сортировку, количество пропущенных и номер категории.
     */
    @GET("recipes/get?")
    suspend fun getRecipes(
        @Query("order") order: Order = Order.DATE,
        @Query("skip") count: Int = 0,
        @Query("category") categoryId: Int,
    ): ResponseWrapper<RecipesShortWrapper>

    /**
     * Получение рецептов по строковому запросу.
     */
    @GET("search/get?")
    suspend fun getRecipes(
        @Query("q") query: String,
        @Query("order") order: Order = Order.DATE,
        @Query("skip") count: Int = 0,
    ): ResponseWrapper<RecipesShortWrapper>

    // TODO: @GET("categories")

}