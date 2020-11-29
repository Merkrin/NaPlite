package com.kykers.naplite.business_layer.network.wrappers

import com.kykers.naplite.business_layer.objects.Category
import com.kykers.naplite.business_layer.objects.RecipeShort
import java.util.*

data class CategoriesWrapper(val count: Int?, val categories: LinkedList<Category>)