package com.example.basemvvm3.fragment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.basemvvm3.R
import com.example.basemvvm3.classes.data.PersonItem
import kotlinx.android.synthetic.main.item_person.view.*
import kotlinx.android.synthetic.main.item_person.view.avatar
import kotlinx.android.synthetic.main.item_photo.view.*

class PersonAdapter() : ListAdapter<PersonItem, PersonItemViewHolder>(PersonDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonItemViewHolder {
        val viewHolder = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return PersonItemViewHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: PersonItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_person
    }
}

class PersonItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: PersonItem){
        itemView.apply{
            name.text = item.name
            age.text = item.age.toString()
            val imgUrl =
                "https://moodle.fhgr.ch/pluginfile.php/124614/mod_page/content/4/example.jpg"
            val options = RequestOptions()
            Glide.with(itemView.context)
                .load(imgUrl)
                .apply(options.fitCenter())
                .into(avatar)
        }
    }
}

object PersonDiffUtil : DiffUtil.ItemCallback<PersonItem>() {

    override fun areItemsTheSame(oldItem: PersonItem, newItem: PersonItem): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: PersonItem, newItem: PersonItem) = true
}