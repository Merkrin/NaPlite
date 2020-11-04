package com.kykers.naplite.network

import com.kykers.naplite.objects.Category
import com.kykers.naplite.objects.Order
import com.kykers.naplite.objects.RecipeFull
import retrofit2.http.GET
import retrofit2.http.Query

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
    suspend fun getRecipe(@Query("id") id: Int): ResponseWrapper<RecipeFull>

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
    @GET("recipes/search?")
    suspend fun getRecipes(
        @Query("q") query: String,
        @Query("order") order: Order = Order.DATE,
        @Query("skip") count: Int = 0,
    ): ResponseWrapper<RecipesShortWrapper>

    // TODO: @GET("categories")

}