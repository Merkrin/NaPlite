package com.kykers.naplite.ui.recipesShort_fragment.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kykers.naplite.R
import com.kykers.naplite.business_layer.network.Event.*
import com.kykers.naplite.business_layer.network.Status.*
import com.kykers.naplite.ui.recipesShort_fragment.adapter.RecipesShortAdapter
import kotlinx.android.synthetic.main.fragment_recipes_short.*


class RecipesFragment : Fragment() {

    private val recipesViewModel by lazy { ViewModelProvider(this).get(RecipesViewModel::class.java) }

    private val recipesShortAdapter = RecipesShortAdapter()

    private val recyclerLayoutManager by lazy { LinearLayoutManager(context) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        /** Реакция на изменения в листе */
        recipesViewModel.recipesShortList.observe(viewLifecycleOwner) {

            recipesShortAdapter.submitList(it)

        }

        /** Реакция на изменение состояний */
        recipesViewModel.recipesShortEvent.observe(viewLifecycleOwner) {

            Log.i("STATUS IN OBSERVE", it.status.name)

            when (it.status) {

                LOADING -> loading()
                SUCCESS -> updated()
                ERROR -> error()

            }

        }

        return inflater.inflate(R.layout.fragment_recipes_short, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_recipes_short.layoutManager = recyclerLayoutManager
        rv_recipes_short.adapter = recipesShortAdapter

    }

    /**
     * Внешний вид интерфейса в зависимости от состояния
     * @author DmitriiShkudov
     *
     */

    private fun updated() {
        status_layout.visibility = View.INVISIBLE
    }

    private fun loading() {
        lay_try_again.visibility = View.GONE
        status_layout.visibility = View.VISIBLE
        cpv_loading.visibility = View.VISIBLE
    }

    private fun error() {

        lay_try_again.visibility = View.VISIBLE
        tv_try_again.text = getString(R.string.bad_connection_info)
        cpv_loading.visibility = View.GONE
        status_layout.visibility = View.VISIBLE

    }


}