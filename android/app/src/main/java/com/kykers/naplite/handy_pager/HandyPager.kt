package dashkudov.handy_pager

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.paging.Config
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kykers.naplite.business_layer.objects.Category
import com.kykers.naplite.ui.main_activity.categories.CategoriesRepository
import dashkudov.handy_pager.HandyPager.Exception.BIND_LISTENER_EXCEPTION
import dashkudov.handy_pager.HandyPager.Exception.CONTAINER_EXCEPTION
import dashkudov.handy_pager.HandyPager.Exception.FACTORY_EXCEPTION
import dashkudov.handy_pager.HandyPager.Exception.FILLED_CONTAINER_EXCEPTION
import dashkudov.handy_pager.HandyPager.Exception.ITEM_VIEW_EXCEPTION
import dashkudov.handy_pager.HandyPager.Exception.OWNER_EXCEPTION
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class HandyPager<T> private constructor() {

    object Exception {

        const val FILLED_CONTAINER_EXCEPTION = "Chosen container already has a child"
        const val CONTAINER_EXCEPTION = "Container wasn't added. Use setContainer()"
        const val OWNER_EXCEPTION = "Owner wasn't added. Use setOwner()"
        const val FACTORY_EXCEPTION = "Factory wasn't added. Use setFactory()"
        const val ITEM_VIEW_EXCEPTION = "ItemView wasn't added. Use setItemView()"
        const val BIND_LISTENER_EXCEPTION = "ItemView wasn't added. Use setItemView()"

    }

    interface OnBindListener<T> {

        fun onBind(itemView: View, item: T, position: Int)

    }

    interface Callback {

        fun onSuccess(from: Int, size: Int)
        fun onLoading(from: Int, size: Int)
        fun onError(from: Int, size: Int)

    }

    companion object class Builder<T> {

        internal var mContext: Context? = null
            private set
        internal var mContainer: ViewGroup? = null
            private set
        internal var mFactory: ((indexFrom: Int, size: Int) -> List<T>)? = null
            private set
        internal var mOnBindListener: OnBindListener<T>? = null
            private set
        internal var mLifecycleOwner: LifecycleOwner? = null
            private set

        internal fun requireContainer() = mContainer!!
        internal fun requireContext() = mContext!!
        internal fun requireFactory() = mFactory!!
        internal fun requireOnBindListener() = mOnBindListener!!
        internal fun requireOwner() = mLifecycleOwner!!

        /** Default values */
        internal val DEFAULT_PAGE_SIZE = 20
        internal val DEFAULT_PREFETCH_DISTANCE = 10
        internal val DEFAULT_RES_ID = 0
        internal val DEFAULT_ERROR_STATE = false
        internal val DEFAULT_STRATEGY_LIST = false

        internal val DEFAULT_CALLBACK = object: Callback {

            override fun onSuccess(from: Int, size: Int) {}
            override fun onLoading(from: Int, size: Int) {}
            override fun onError(from: Int, size: Int) {}

        }

        internal val DEFAULT_ITEM_CALLBACK = object: DiffUtil.ItemCallback<T>() {

            override fun areItemsTheSame(oldItem: T, newItem: T) = false
            override fun areContentsTheSame(oldItem: T, newItem: T) = false
        }
        /***/

        internal var mPageSize = DEFAULT_PAGE_SIZE
        internal var mPrefetchDistance = DEFAULT_PREFETCH_DISTANCE
        internal var mItemViewResId = DEFAULT_RES_ID
        internal var mCallback = DEFAULT_CALLBACK
        internal var isErrorOccured = DEFAULT_ERROR_STATE
        internal var mItemCallback = DEFAULT_ITEM_CALLBACK
        internal var mStrategyList = DEFAULT_STRATEGY_LIST

        fun create(): HandyPager<T> {

            return HandyPager(this).apply {

                mBuilder = this@Builder

            }

        }

        fun setStrategyList(): Builder<T> {

            mStrategyList = true
            return this

        }

        fun setOwner(lifecycleOwner: LifecycleOwner): Builder<T> {

            mLifecycleOwner = lifecycleOwner
            return this

        }

        fun setContainer(container: ViewGroup): Builder<T> {

            mContainer = container
            mContext = requireContainer().context
            return this

        }

        fun setFactory(factory: (from: Int, size: Int) -> List<T>): Builder<T> {

            mFactory = factory
            return this

        }

        fun setItemView(layoutId: Int): Builder<T> {

            mItemViewResId = layoutId
            return this

        }

        fun setOnBindListener(onBindListener: OnBindListener<T>): Builder<T> {

            mOnBindListener = onBindListener
            return this

        }

        fun setCallback(callback: Callback): Builder<T> {

            mCallback = callback
            return this

        }

        fun setPageSize(pageSize: Int): Builder<T> {

            mPageSize = pageSize
            return this

        }

        fun setPrefetchDistance(prefetchDistance: Int): Builder<T> {

            mPrefetchDistance = prefetchDistance
            return this

        }

        fun setItemCallback(itemCallback: DiffUtil.ItemCallback<T>): Builder<T> {

            mItemCallback = itemCallback
            return this

        }

    }


    constructor(builder: Builder<T>): this() {

            with(builder) {

            if (mContainer == null) throw Exception(CONTAINER_EXCEPTION)
            if (mFactory == null) throw Exception(FACTORY_EXCEPTION)
            if (mOnBindListener == null) throw Exception(BIND_LISTENER_EXCEPTION)
            if (mItemViewResId == DEFAULT_RES_ID) throw Exception(ITEM_VIEW_EXCEPTION)
            if (requireContainer().childCount != 0) throw Exception(FILLED_CONTAINER_EXCEPTION)

            if (!mStrategyList) {

                if (mLifecycleOwner == null) throw Exception(OWNER_EXCEPTION)

                requireContainer().addView(RecyclerView(requireContext()).apply {

                    adapter = Adapter(builder)
                    layoutManager = LinearLayoutManager(mContext)

                })

                LivePagedListBuilder(Factory(builder), Config(mPageSize, mPrefetchDistance,false)).build().apply {

                    observe(requireOwner()) {

                        ((requireContainer().getChildAt(0) as RecyclerView).adapter
                                as PagedListAdapter<T, Holder<T>>).submitList(it)

                    }
                }

            } else {

                CoroutineScope(Main).launch {

                    val list = withContext(IO) { requireFactory().invoke(0, 0) }

                    requireContainer().addView(RecyclerView(requireContext()).apply {

                        mRecycler = this
                        adapter = ListAdapter(builder, list)
                        layoutManager = LinearLayoutManager(mContext)

                    })
                }
            }
        }
    }

    internal lateinit var mBuilder: Builder<T>
    internal lateinit var mRecycler: RecyclerView

    fun submitList(list: List<T>) {

        mRecycler.apply {

            adapter = ListAdapter(mBuilder, list)
            layoutManager = LinearLayoutManager(context)

        }
    }

}