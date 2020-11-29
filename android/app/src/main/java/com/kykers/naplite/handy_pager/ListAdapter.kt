package dashkudov.handy_pager

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

internal class ListAdapter<T>(private val builder: HandyPager.Builder<T>,
                              private val list: List<T>): RecyclerView.Adapter<Holder<T>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        Holder<T>(LayoutInflater.from(parent.context).inflate(builder.mItemViewResId, parent, false))

    override fun onBindViewHolder(holder: Holder<T>, position: Int) =
        builder.requireOnBindListener().onBind(holder.itemView, list[position], position)

    override fun getItemCount() = list.size

}