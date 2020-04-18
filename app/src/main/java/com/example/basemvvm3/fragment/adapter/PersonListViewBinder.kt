package com.example.basemvvm3.fragment.adapter

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.basemvvm3.R
import com.example.basemvvm3.classes.data.PersonList
import kotlinx.android.synthetic.main.item_person_list.view.*

class PersonListViewBinder(
    private val personListViewPool: RecyclerView.RecycledViewPool,
    var saveState: Bundle?
) : BaseViewBinder<PersonList, PersonListViewHolder>(PersonList::class.java) {

    init {
        if (saveState == null) {
            saveState = Bundle()
        }
    }

    override fun createViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        val holder = PersonListViewHolder(inflater)

        holder.itemView.findViewById<RecyclerView>(R.id.recyclerview)
            .addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        saveInstanceState(holder)
                    }
                }
            })

        return holder
    }

    override fun bindViewHolder(model: PersonList, viewHolder: PersonListViewHolder) {
        viewHolder.bind(
            model,
            personListViewPool,
            saveState?.getParcelable(viewHolder.adapterPosition.toString())
        )
    }

    override fun getItemViewType(): Int = R.layout.item_person_list

    override fun onViewRecycled(viewHolder: PersonListViewHolder) {
        saveInstanceState(viewHolder)
    }

    override fun areItemsTheSame(oldItem: PersonList, newItem: PersonList): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: PersonList, newItem: PersonList): Boolean =
        oldItem == newItem

    private fun saveInstanceState(viewHolder: PersonListViewHolder) {
        if (viewHolder.adapterPosition == RecyclerView.NO_POSITION) {
            return
        }
        saveState?.putParcelable(
            viewHolder.adapterPosition.toString(),
            viewHolder.getLayoutManagerState()
        )
    }

}

class PersonListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var layoutManager: RecyclerView.LayoutManager? = null

    fun bind(
        model: PersonList,
        viewPool: RecyclerView.RecycledViewPool,
        layoutManagerState: Parcelable?
    ) {
        itemView.apply {
            if (recyclerview.adapter == null) {
                recyclerview.adapter = PersonAdapter()
                recyclerview.setRecycledViewPool(viewPool)
                layoutManager = recyclerview.layoutManager
            }
            (recyclerview.adapter as PersonAdapter).submitList(model.list)

            if (layoutManagerState != null) {
                layoutManager?.onRestoreInstanceState(layoutManagerState)
            }
        }
    }

    fun getLayoutManagerState(): Parcelable? = layoutManager?.onSaveInstanceState()
}