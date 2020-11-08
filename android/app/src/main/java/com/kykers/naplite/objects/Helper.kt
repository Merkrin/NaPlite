package com.kykers.naplite.objects

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import java.lang.Exception

object Helper {

    /**
        Хелпер-метод для схожей сигнатуры с LiveData.postValue()
     */
    fun <T> MutableLiveData<T>.value(value: T) {
        this.value = value
    }

    /**
        Хелпер-метод для запроса значения без non-null
     */
    inline fun <reified T> MutableLiveData<T>.requireValue() =

        this.value ?: try {
            T::class.objectInstance ?: throw ClassNotFoundException()
        } catch (e: Throwable) {
            throw Exception(e)
        }

}