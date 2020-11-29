package com.kykers.naplite.ui.main_activity.recipes_short

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import com.kykers.naplite.R
import com.kykers.naplite.business_layer.network.NetworkRepository.getRecipes
import com.kykers.naplite.business_layer.objects.Order
import com.kykers.naplite.business_layer.objects.RecipeShort
import com.kykers.naplite.ui.main_activity.categories.setSafeOnClickListener
import com.squareup.picasso.Picasso
import dashkudov.handy_pager.HandyPager
import kotlinx.android.synthetic.main.fragment_recipes_short.*
import kotlinx.android.synthetic.main.fragment_recipes_short.options
import kotlinx.android.synthetic.main.fragment_recipes_short.spinner_sort
import kotlinx.android.synthetic.main.fragment_recipes_short.view.*
import kotlinx.android.synthetic.main.item_recipe_short.view.*
import java.time.ZoneId


class RecipesFragment : Fragment() {

    companion object {

        const val PAGE_SIZE = 20
        const val PREFETCH_DISTANCE = 20

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

        spinner_sort.adapter = ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            listOf("По комментариям", "По рейтингу", "По дате"))

        var i = 0 // spinner bug
        spinner_sort.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {


            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}

        }

        btn_options.setOnClickListener {

            options.animate().apply {

                duration = 400
                alpha(1f)

            }

            root.setSafeOnClickListener {

                options.animate().apply {

                    duration = 400
                    alpha(0f)
                }
                spinner_sort.isClickable = false
            }
        }

    }

    /**
     * Внешний вид интерфейса в зависимости от состояния
     * @author DmitriiShkudov
     *
     */

    private fun updated() {
        status_layout?.visibility = View.INVISIBLE
    }

    private fun loading() {
        lay_try_again?.visibility = View.GONE
        status_layout?.visibility = View.VISIBLE
        cpv_loading?.visibility = View.VISIBLE
    }

    private fun error() {
        lay_try_again?.visibility = View.VISIBLE
        tv_try_again?.text = getString(R.string.error_info)
        cpv_loading?.visibility = View.GONE
        status_layout?.visibility = View.VISIBLE
    }

    private fun integrateHandyPager() {

        HandyPager.Builder<RecipeShort>().
            setOwner(viewLifecycleOwner).
            setPageSize(PAGE_SIZE).
            setPrefetchDistance(PREFETCH_DISTANCE).
            setContainer(rv_recipes_short).
            setItemView(R.layout.item_recipe_short).
            setFactory { from, size -> getRecipes(Order.COMMENTS, from, size) }.
            setOnBindListener(object: HandyPager.OnBindListener<RecipeShort> {

                override fun onBind(itemView: View, item: RecipeShort, position: Int) {

                    with(itemView) {

                        setOnClickListener {

                            this@RecipesFragment.options.animate().apply {

                                duration = 400
                                alpha(0f)
                            }
                            this@RecipesFragment.spinner_sort.isClickable = false

                        }

                        // TODO: исправить (на бэке?)
                        val fixedRecipeTitle = item.title?.replace("&quot;", "\"")

                        // TODO: исправить http на https на бэке
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
            setItemCallback(object: DiffUtil.ItemCallback<RecipeShort>() {

                override fun areItemsTheSame(oldItem: RecipeShort, newItem: RecipeShort) =
                    oldItem.title == newItem.title

                override fun areContentsTheSame(oldItem: RecipeShort, newItem: RecipeShort) =
                    oldItem.id == newItem.id

            }).
            create()

    }

}