package com.kykers.naplite.ui.recipe_activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kykers.naplite.business_layer.network.BaseViewModel
import com.kykers.naplite.business_layer.network.Event
import com.kykers.naplite.business_layer.objects.RecipeFull

class RecipeActivityViewModel : BaseViewModel() {
    private val _recipe = MutableLiveData<Event<RecipeFull>>()
    val recipe: LiveData<Event<RecipeFull>> = _recipe

    fun getRecipe(recipeId: Int) {
        request(_recipe) {
            api.getRecipe(recipeId)
        }
    }
}