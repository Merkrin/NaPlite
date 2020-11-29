package dashkudov.handy_pager

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil

internal class Adapter<T>(private val builder: HandyPager.Builder<T>):
        PagedListAdapter<T, Holder<T>>(builder.mItemCallback) {

        override fun onBindViewHolder(holder: Holder<T>, position: Int) =
            builder.requireOnBindListener().onBind(holder.itemView, getItem(position)!!, position)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            Holder<T>(LayoutInflater.from(parent.context).inflate(builder.mItemViewResId, parent, false))

}