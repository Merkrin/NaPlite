package com.kykers.naplite.business_layer.objects

import java.io.Serializable

/**
 * Шаг рецепта с описанием и картинкой.
 *
 * @author goga133
 */
data class Step(val description: String, val imageUrl: String) : Serializable