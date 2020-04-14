package com.example.basemvvm3.fragment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.basemvvm3.R
import com.example.basemvvm3.classes.data.PersonList
import kotlinx.android.synthetic.main.item_person_list.view.*

class PersonListViewBinder(private val viewPool: RecyclerView.RecycledViewPool) : BaseViewBinder<PersonList, PersonListViewHolder>(PersonList::class.java) {
    override fun createViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewHolder = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return PersonListViewHolder(viewHolder)
    }

    override fun bindViewHolder(model: PersonList, viewHolder: PersonListViewHolder) {
        viewHolder.bind(model, viewPool)
    }

    override fun getItemViewType(): Int = R.layout.item_person_list

    override fun areItemsTheSame(oldItem: PersonList, newItem: PersonList): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: PersonList, newItem: PersonList): Boolean = oldItem == newItem

}

class PersonListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(model: PersonList, viewPool: RecyclerView.RecycledViewPool) {
        itemView.apply {
            if(recyclerview.adapter == null) {
                recyclerview.adapter = PersonAdapter()
                recyclerview.setRecycledViewPool(viewPool)
            }
            (recyclerview.adapter as PersonAdapter).submitList(model.list)
        }
    }
}