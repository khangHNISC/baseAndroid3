package com.example.basemvvm3.fragment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.basemvvm3.R
import com.example.basemvvm3.classes.data.PhotoItem
import kotlinx.android.synthetic.main.item_photo.view.*

class PhotoPagedListAdapter :
    PagedListAdapter<PhotoItem, PhotoViewHolder>(JustPhotoItemDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return PhotoViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_photo
    }
}


class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(
        item: PhotoItem?
    ) {
        itemView.apply {
            text1.text = item?.title
            val imgUrl =
                "https://moodle.fhgr.ch/pluginfile.php/124614/mod_page/content/4/example.jpg"
            Glide.with(itemView.context)
                .load(imgUrl)
                .apply(RequestOptions().fitCenter())
                .into(avatar)
        }
    }
}

object JustPhotoItemDiff : DiffUtil.ItemCallback<PhotoItem>() {
    override fun areItemsTheSame(oldItem: PhotoItem, newItem: PhotoItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PhotoItem, newItem: PhotoItem): Boolean {
        return oldItem == newItem
    }
}
