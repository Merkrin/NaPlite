package com.kykers.naplite.business_layer.objects

/**
 * Категории с Поварёнка.
 *
 * @author goga133
 */
data class Category(
    val id: Int,
    val title: String,
    val parentId: Int,
    val level : Int,
    val children: ArrayList<Category>
)
