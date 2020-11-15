package com.kykers.naplite.ui.recipesShort_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kykers.naplite.R
import com.kykers.naplite.business_layer.network.NetworkRepository.getRecipes
import com.kykers.naplite.business_layer.objects.Order
import com.kykers.naplite.business_layer.objects.RecipeShort
import com.squareup.picasso.Picasso
import dashkudov.handy_pager.HandyPager
import kotlinx.android.synthetic.main.fragment_recipes_short.*
import kotlinx.android.synthetic.main.item_recipe_short.view.*
import java.time.ZoneId


class RecipesFragment : Fragment() {

    companion object {

        const val PAGE_SIZE = 20
        const val PREFETCH_DISTANCE = 18

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_recipes_short, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        integrateHandyPager()

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
        tv_try_again.text = getString(R.string.error_info)
        cpv_loading.visibility = View.GONE
        status_layout.visibility = View.VISIBLE
    }

    private fun integrateHandyPager() {
        HandyPager.Builder<RecipeShort>().
        setOwner(viewLifecycleOwner).
        setPageSize(PAGE_SIZE).
        setPrefetchDistance(PREFETCH_DISTANCE).
        setContainer(rv_recipes_short).
        setItemView(R.layout.item_recipe_short).
        setFactory {
             from: Int, size: Int -> getRecipes(Order.COMMENTS, from, size)
        }.setOnBindListener(object: HandyPager.OnBindListener<RecipeShort> {
            override fun onBind(itemView: View, item: RecipeShort, position: Int) {
                with(itemView) {
                    val fixedRecipeTitle = item.title

                    val fixedUrl = item.image300XUrl
                    Picasso.get().load(fixedUrl).into(civ_recipes_short)

                    tv_recipes_short_title.text = fixedRecipeTitle
                    tv_recipes_short_comments.text = item.comments.toString()
                    tv_recipes_short_date.text =
                        item.pubDateTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                            .toString()
                }
            }
        }).
        setCallback(object: HandyPager.Callback {

            override fun onError(from: Int, size: Int) { error() }

            override fun onLoading(from: Int, size: Int) { loading() }

            override fun onSuccess(from: Int, size: Int) { updated() }

        }).
        create()

    }

}