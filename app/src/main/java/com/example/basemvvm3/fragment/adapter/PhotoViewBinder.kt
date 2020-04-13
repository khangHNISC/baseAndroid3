package com.example.basemvvm3.fragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.basemvvm3.R
import com.example.basemvvm3.classes.data.PhotoItem

class PhotoViewBinder : BaseViewBinder<PhotoItem, PhotoViewHolder>(PhotoItem::class.java) {
    override fun createViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewHolder = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return PhotoViewHolder(viewHolder)
    }

    override fun bindViewHolder(model: PhotoItem, viewHolder: PhotoViewHolder) {
        viewHolder.bind(model)
    }

    override fun getItemViewType(): Int = R.layout.item_photo

    override fun areItemsTheSame(oldItem: PhotoItem, newItem: PhotoItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PhotoItem, newItem: PhotoItem): Boolean = true

}

