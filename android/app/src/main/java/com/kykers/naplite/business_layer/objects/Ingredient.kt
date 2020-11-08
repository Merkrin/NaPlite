package com.kykers.naplite.business_layer.objects

import java.io.Serializable

/**
 * Ингредиент для рецепта.
 * @param name - Название ингредиента. Например: Шоколад тёмный
 * @param unit - Количество. Например: 100 г.
 * @param recipeName - Название части рецепта, к которому пренадлежит ингредиент.
 * Например: название рецепта - "Торт", у него есть "Корж", а у него есть сливки, тогда некоторые
 * авторы указывает в качестве ингредиента "Сливки", а в качестве recipeName - "Корж"
 * Может быть null.
 * @param idWeb - id-номер ингредиента на сайте Поварёнок.
 *
 * @author goga133
 */
data class Ingredient(val name: String, val unit: String, val recipeName: String?, val idWeb: Int) :
    Serializable