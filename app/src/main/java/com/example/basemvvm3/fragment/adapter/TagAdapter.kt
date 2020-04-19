package com.example.basemvvm3.fragment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.basemvvm3.R
import com.example.basemvvm3.classes.data.db.TagEntity
import kotlinx.android.synthetic.main.item_tag.view.*

class TagAdapter() : PagedListAdapter<TagEntity, TagViewHolder>(TagDiffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        val viewHolder = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return TagViewHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    
    override fun getItemViewType(position: Int): Int {
        return R.layout.item_tag
    }
}

class TagViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    
    fun bind(item: TagEntity?){
        itemView.apply{
            tagName.text = item?.name
        }
    }
}

object TagDiffCallBack : DiffUtil.ItemCallback<TagEntity>() {

    override fun areItemsTheSame(oldItem: TagEntity, newItem: TagEntity): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: TagEntity, newItem: TagEntity) = oldItem == newItem
}