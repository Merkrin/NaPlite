package com.kykers.naplite.business_layer.network

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class BaseViewModel : ViewModel() {

    var api: RecipesApi = NetworkService.retrofitService()

    // У нас будут две базовые функции requestWithLiveData и
    // requestWithCallback, в зависимости от ситуации мы будем
    // передавать в них лайвдату или колбек вместе с параметрами сетевого
    // запроса. Функция принимает в виде параметра ретрофитовский suspend запрос,
    // проверяет на наличие ошибок и сетит данные в виде ивента либо в
    // лайвдату либо в колбек. Про ивент будет написано ниже

    protected fun <T> request(
        liveData: MutableLiveData<Event<T>>,
        request: suspend () -> Call<T>
    ) {

        liveData.postValue(Event.loading())

        this.viewModelScope.launch(Dispatchers.IO) {
            try {
                request.invoke().enqueue(object : Callback<T> {
                    override fun onFailure(call: Call<T>, t: Throwable) {
                        t.printStackTrace()
                        liveData.postValue(Event.error(t))
                    }

                    override fun onResponse(
                        call: Call<T>,
                        response: Response<T>
                    ) {
                        if (response.isSuccessful) {
                            liveData.postValue(Event.success(response.body()))
                        }
                        else {
                            liveData.postValue(Event.error(Throwable(response.errorBody()?.string())))
                        }
                    }
                })
            } catch (e: Exception) {
                e.printStackTrace()
                liveData.postValue(Event.error(null))
            }
        }
    }
}