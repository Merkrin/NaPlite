package com.kykers.naplite.objects

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable
import java.util.*

/**
 * Объект, который представляет сущность в виде кратного рецепта.
 * Используется для отображения в списках рецептов.
 *
 * Реализует Serializable и Parcelable.
 *
 * @author goga133
 */
data class RecipeShort(
    val id: Int,
    val url: String?,
    val title: String?,
    val imageFullUrl: String?,
    val image150xUrl: String?,
    val image200xUrl: String?,
    val image300xUrl: String?,
    val description: String?,
    val pubDateTime: Date,
    val comments: Int,
    val favourites: Int,
    val points: Int,
    val author: String?
) : Serializable, Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readSerializable() as Date,
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(url)
        parcel.writeString(title)
        parcel.writeString(imageFullUrl)
        parcel.writeString(image150xUrl)
        parcel.writeString(image200xUrl)
        parcel.writeString(image300xUrl)
        parcel.writeString(description)
        parcel.writeSerializable(pubDateTime)
        parcel.writeInt(comments)
        parcel.writeInt(favourites)
        parcel.writeInt(points)
        parcel.writeString(author)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RecipeShort> {
        override fun createFromParcel(parcel: Parcel): RecipeShort {
            return RecipeShort(parcel)
        }

        override fun newArray(size: Int): Array<RecipeShort?> {
            return arrayOfNulls(size)
        }
    }
}
