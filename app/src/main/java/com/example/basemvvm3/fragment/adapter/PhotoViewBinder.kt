package com.example.basemvvm3.fragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.basemvvm3.R
import com.example.basemvvm3.classes.data.PhotoItem

internal class PhotoViewBinder : BaseViewBinder<PhotoItem, PhotoGeneralViewHolder.PhotoViewHolder>(PhotoItem::class.java) {
    override fun createViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return PhotoGeneralViewHolder.PhotoViewHolder(inflater)
    }

    override fun bindViewHolder(model: PhotoItem, viewHolder: PhotoGeneralViewHolder.PhotoViewHolder) {
        viewHolder.bind(model, null, null)
    }

    override fun getItemViewType(): Int = R.layout.item_photo

    override fun areItemsTheSame(oldItem: PhotoItem, newItem: PhotoItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PhotoItem, newItem: PhotoItem): Boolean = true

}

