package com.kykers.naplite

import android.util.Log
import com.kykers.naplite.network.NetworkService
import com.kykers.naplite.objects.Order
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import retrofit2.awaitResponse

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun isCorrectGetRecipe() {
        runBlocking {
            launch(Dispatchers.Main) {
                val api = NetworkService.retrofitService()
                val recipe = api.getRecipe(15223).awaitResponse()
                val recipes = api.getRecipes(Order.DATE).awaitResponse()

                println(recipe.body().toString())
                println(recipes.body().toString())

                assertNotNull(recipe)
                assertNotNull(recipes)
            }
        }
    }
}