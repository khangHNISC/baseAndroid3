package com.example.basemvvm3.fragment.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder as ViewHolder1

typealias ItemClass = Class<out Any>

typealias MapTypeToVH = BaseViewBinder<Any, ViewHolder1>

class MultiViewItemAdapter(
    private val viewBinders: Map<ItemClass, MapTypeToVH>
) : ListAdapter<Any, ViewHolder1>(ItemDiffCallback(viewBinders)){

    //map to series of ItemType to ViewBinder
    private val viewTypeToBinders = viewBinders.mapKeys { it.value.getItemViewType() }

    private fun getViewBinder(viewType: Int) : MapTypeToVH = viewTypeToBinders.getValue(viewType)

    //getValue throws exceptions if no value exists
    override fun getItemViewType(position: Int): Int {
        return viewBinders.getValue(getItem(position).javaClass).getItemViewType()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return getViewBinder(viewType).createViewHolder(parent, viewType)
    }

    // purpose why having a map for
    // ItemClass -> MapTypeToVH even though MapTypeToVH already infers Type because
    // getViewBinder() require view Type and how to get viewType from position
    override fun onBindViewHolder(holder: ViewHolder1, position: Int) {
        getViewBinder(getItemViewType(position)).bindViewHolder(getItem(position), holder)
    }

    override fun onViewRecycled(holder: ViewHolder1) {
        getViewBinder(holder.itemViewType).onViewRecycled(holder)
        super.onViewRecycled(holder)

    }

    override fun onViewDetachedFromWindow(holder: ViewHolder1) {
        getViewBinder(holder.itemViewType).onViewDetachedFromWindow(holder)
        super.onViewDetachedFromWindow(holder)
    }
}

//map 1-1 type and VH, modelClass extends from M
abstract class BaseViewBinder<M, in VH : ViewHolder1>(
    val modelClass: Class<out M>
) : DiffUtil.ItemCallback<M>() {
    abstract fun createViewHolder(parent: ViewGroup, viewType: Int): ViewHolder1
    abstract fun bindViewHolder(model: M, viewHolder: VH)
    abstract fun getItemViewType(): Int

    open fun onViewRecycled(viewHolder: VH) = Unit
    open fun onViewDetachedFromWindow(viewHolder: VH) = Unit
}

internal class ItemDiffCallback(
    //map 1-1 Class extend M and ItemViewBinder
    private val viewBinders: Map<ItemClass, MapTypeToVH>
) : DiffUtil.ItemCallback<Any>() {
    override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
        if (oldItem::class != newItem::class) {
            return false
        }
        return viewBinders[oldItem::class.java]?.areItemsTheSame(oldItem, newItem) ?: false
    }

    override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
        return viewBinders[oldItem::class.java]?.areContentsTheSame(oldItem, newItem) ?: false
    }
}