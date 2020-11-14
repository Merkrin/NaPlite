package com.kykers.naplite

import androidx.lifecycle.MutableLiveData
import java.lang.NullPointerException

object Helper {

    fun <T> MutableLiveData<T>.requireValue()
            = value ?: throw NullPointerException("Value is null")


}