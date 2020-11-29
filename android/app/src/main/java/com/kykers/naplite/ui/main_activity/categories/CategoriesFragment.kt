package com.kykers.naplite.ui.main_activity.categories

import android.app.SearchableInfo
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.kykers.naplite.R
import com.kykers.naplite.business_layer.network.NetworkRepository
import com.kykers.naplite.business_layer.objects.Category
import dashkudov.handy_pager.HandyPager
import kotlinx.android.synthetic.main.fragment_categories.*
import kotlinx.android.synthetic.main.fragment_categories.search
import kotlinx.android.synthetic.main.fragment_recipes_short.*
import kotlinx.android.synthetic.main.item_category.*
import kotlinx.android.synthetic.main.item_category.view.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import java.util.*
import kotlin.collections.ArrayList


fun View.hideKeyboard() =

    (context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).
    hideSoftInputFromWindow(windowToken, 0)


fun View.setSafeOnClickListener(action: () -> Unit) = this.setOnClickListener {
    action.invoke()
    this.setOnClickListener(null)
}


class CategoriesFragment : Fragment() {

    private companion object {

        const val FADE_DURATION: Long = 500
        const val SOLID = 1f
        const val TRANSPARENT = 0f

    }

    private lateinit var handyPager: HandyPager<Category>

    private val scheme by lazy { ArrayList<Int>() }
    private val categoriesRoot by lazy { NetworkRepository.getCategories() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = LayoutInflater.from(context).inflate(R.layout.fragment_categories, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        search.setOnCloseListener {

            search.hideKeyboard()
            search.clearFocus()
            update(null)
            true
        }

        search.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                search.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText!!.isEmpty()) {

                    update(null)

                } else {

                    update(newText)

                }
                return true
            }
        })

        btn_back.setOnClickListener {

            scheme.removeLast()
            update(null)

        }

        handyPager = HandyPager.Builder<Category>().setStrategyList().setContainer(categories)
            .setItemView(R.layout.item_category)
            .setFactory { _, _ -> runBlocking { categoriesRoot } }
            .setOnBindListener(object : HandyPager.OnBindListener<Category> {

                override fun onBind(itemView: View, item: Category, position: Int) {

                    itemView.tv_category.text = item.title

                    if (scheme.isEmpty())
                        itemView.img_category.setImageDrawable(getCategoryImage(position))
                    else
                        itemView.img_category.visibility = View.GONE

                    if (item.children.isNotEmpty()) {

                        itemView.setOnClickListener {

                            scheme.add(getIndexByTitle(item.title, scheme))
                            search.clearFocus()
                            search.setQuery("", true)
                            update(null)
                        }

                    }

                    itemView.btn_choose.setOnClickListener {

                        // TODO

                    }
                }
            }).create()
    }

    private fun update(query: String?) {

        configureBackButton()
        var listToShow = (categoriesRoot).toList() as ArrayList<Category>
        Log.i("LIST_TO_SHOW SIZE: ", listToShow.size.toString())

        scheme.forEach { i ->
            listToShow = listToShow[i].children
        }

        Log.i("SCHEME SIZE", scheme.size.toString())

        if (query != null) {

            listToShow.toList().forEach {

                if (!it.title.contains(query)) {

                    listToShow.remove(it)

                }
            }
        }

        Log.i("query is null", (query == null).toString())

        handyPager.submitList(listToShow)
    }

    private fun configureBackButton() {

        if (scheme.isNotEmpty()) {

            btn_back.isClickable = true
            btn_back.animate().apply {

                duration = FADE_DURATION
                scaleX(SOLID)
                scaleY(SOLID)

            }

        } else {

            btn_back.isClickable = false
            btn_back.animate().apply {

                duration = FADE_DURATION
                scaleX(TRANSPARENT)
                scaleY(TRANSPARENT)

            }
        }
    }

    private fun getCategoryImage(index: Int) = ContextCompat.getDrawable(
        requireContext(), when (index) {

            0 -> R.drawable.categ0
            1 -> R.drawable.categ1
            2 -> R.drawable.categ2
            3 -> R.drawable.categ3
            4 -> R.drawable.categ4
            5 -> R.drawable.categ5
            6 -> R.drawable.categ6
            7 -> R.drawable.categ7
            8 -> R.drawable.categ8
            9 -> R.drawable.categ9
            10 -> R.drawable.categ10
            11 -> R.drawable.categ11
            12 -> R.drawable.categ12
            13 -> R.drawable.categ13
            14 -> R.drawable.categ14
            15 -> R.drawable.categ15
            else -> R.drawable.categ16

        })

    private fun getIndexByTitle(title: String, scheme: ArrayList<Int>): Int {

        var listToShow = (categoriesRoot).toList() as ArrayList<Category>

        scheme.forEach { i ->
            listToShow = listToShow[i].children
        }

        for (i in listToShow.indices) {

                if (title == listToShow[i].title)
                    return i


        }

        return -1

    }

}