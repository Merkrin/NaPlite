package com.kykers.naplite.presentation_layer.recipes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.paging.Config
import androidx.paging.LivePagedListBuilder
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kykers.naplite.R
import com.kykers.naplite.business_layer.objects.Order
import com.kykers.naplite.business_layer.objects.RecipeShort
import com.kykers.naplite.objects.Helper.requireValue
import com.kykers.naplite.presentation_layer.adapters.RecipesShortAdapter
import kotlinx.android.synthetic.main.fragment_recipes.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import kotlin.reflect.jvm.internal.impl.renderer.DescriptorRenderer


class RecipesFragment : Fragment() {

    private val recipesViewModel by lazy { ViewModelProvider(this).get(RecipesViewModel::class.java) }

    private val recipesShortAdapter = RecipesShortAdapter()

    private val recyclerLayoutManager by lazy { LinearLayoutManager(context) }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        /** Observing list and apply changes*/
        recipesViewModel.recipesShortList.observe(viewLifecycleOwner) {

            recipesShortAdapter.submitList(it)

        }

        return inflater.inflate(R.layout.fragment_recipes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_recipes_short.layoutManager = recyclerLayoutManager
        rv_recipes_short.adapter = recipesShortAdapter

    }

}