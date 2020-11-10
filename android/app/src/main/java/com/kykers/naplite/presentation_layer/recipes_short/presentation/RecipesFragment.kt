package com.kykers.naplite.presentation_layer.recipes_short.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.paging.Config
import androidx.paging.LivePagedListBuilder
import androidx.paging.PositionalDataSource
import androidx.recyclerview.widget.LinearLayoutManager
import com.kykers.naplite.R
import com.kykers.naplite.business_layer.objects.RecipeShort
import com.kykers.naplite.presentation_layer.recipes_short.IState
import com.kykers.naplite.presentation_layer.recipes_short.State
import com.kykers.naplite.presentation_layer.recipes_short.adapters.RecipesShortAdapter
import com.kykers.naplite.presentation_layer.recipes_short.adapters.data.RecipesSourceFactory
import kotlinx.android.synthetic.main.fragment_recipes_short.*
import okhttp3.internal.platform.android.BouncyCastleSocketAdapter.Companion.factory


class RecipesFragment : Fragment(), IState {

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
        recipesViewModel.state.observe(viewLifecycleOwner) {

            when (it) {

                State.LOADING -> loading()
                State.UPDATED -> updated()
                State.NETWORK_ERROR -> networkError()
                State.UNKNOWN_ERROR -> unknownError()
                State.SERVER_ERROR -> serverError()

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
     * @see IState
     * @see State
     */

    override fun updated() {
        status_layout.visibility = View.INVISIBLE
    }

    override fun loading() {
        lay_try_again.visibility = View.GONE
        status_layout.visibility = View.VISIBLE
        cpv_loading.visibility = View.VISIBLE
    }

    override fun networkError() {

        lay_try_again.visibility = View.VISIBLE
        tv_try_again.text = getString(R.string.bad_connection_info)
        cpv_loading.visibility = View.GONE
        status_layout.visibility = View.VISIBLE

    }

    override fun unknownError() {

        lay_try_again.visibility = View.VISIBLE
        tv_try_again.text = getString(R.string.server_connection_info)
        cpv_loading.visibility = View.GONE
        status_layout.visibility = View.VISIBLE

    }

    override fun serverError() {

        lay_try_again.visibility = View.VISIBLE
        tv_try_again.text = getString(R.string.server_connection_info)
        cpv_loading.visibility = View.GONE
        status_layout.visibility = View.VISIBLE

    }

}