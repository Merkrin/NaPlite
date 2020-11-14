package com.kykers.naplite.business_layer.network.wrappers

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.kykers.naplite.business_layer.objects.RecipeShort
import java.io.Serializable
import java.util.*

/**
 * Класс - обёртка под RecipeShort.
 * @see RecipeShort
 *
 * @author goga133
 */

data class RecipesShortWrapper(val count: Int?, val recipes: LinkedList<RecipeShort>)

