package com.example.basemvvm3.fragment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.basemvvm3.R
import com.example.basemvvm3.classes.data.PersonItem
import kotlinx.android.synthetic.main.item_person.view.*

class PersonViewBinder : BaseViewBinder<PersonItem, PersonViewHolder>(PersonItem::class.java) {
    override fun createViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewHolder = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return PersonViewHolder(viewHolder)
    }

    override fun bindViewHolder(model: PersonItem, viewHolder: PersonViewHolder) {
        viewHolder.bind(model)
    }

    override fun getItemViewType(): Int = R.layout.item_person

    override fun areItemsTheSame(oldItem: PersonItem, newItem: PersonItem): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: PersonItem, newItem: PersonItem): Boolean = true

}

class PersonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(model: PersonItem) {
        itemView.apply {
            name.text = model.name
            age.text = model.age.toString()
        }
    }
}