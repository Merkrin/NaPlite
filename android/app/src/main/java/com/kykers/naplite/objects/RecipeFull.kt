package com.kykers.naplite.objects

import java.io.Serializable

/**
 * Полный рецепт.
 * @param id - id рецепта.
 * @param url - url-ссылка на рецепт.
 * @param title - Название рецепта.
 * @param titleImageUrl - Ссылка на основную фотографию рецепта.
 * @param description - Описание рецепта.
 * @param ingredients - Ингредиенты.
 * @param steps - Шаги рецепта.
 * @param authorName - Имя автора.
 * @param countPortions - Количество порций. Если < 0, значит автор не указал.
 * @param prepMinutes - Количество минут готовки. Если < 0, значит автор не указал.
 * @param compositions - БЖУ на разные грамовки.
 * @param dateOfCreate - Дата создания рецепта в строковом виде.
 * @param views - Количество просмотров рецепта.
 * @param videoUrl - Ссылнка на видео-контент. Если null, значит видео нет.
 *
 * @author goga133
 */
data class RecipeFull(
    val id: Int,
    val url: String?,
    val title: String?,
    val titleImageUrl: String?,
    val description: String?,
    val ingredients: ArrayList<Ingredient>?,
    val steps: ArrayList<Step>?,
    val authorName: String?,
    val countPortions: Int,
    val prepMinutes: Int,
    val compositions: ArrayList<Composition>?,
    val dateOfCreate: String?,
    val views: Int,
    val videoUrl: String?
) : Serializable {

    fun haveVideo(): Boolean {
        return videoUrl != null
    }

    fun haveCountPortions(): Boolean {
        return countPortions > 0
    }

    fun havePrepMinutes(): Boolean {
        return prepMinutes > 0
    }
}