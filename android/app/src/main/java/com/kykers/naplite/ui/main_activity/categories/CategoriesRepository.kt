package com.kykers.naplite.ui.main_activity.categories

import com.kykers.naplite.business_layer.network.NetworkRepository

class CategoriesRepository {

    internal val categoriesList by lazy { NetworkRepository.getCategories() }

}