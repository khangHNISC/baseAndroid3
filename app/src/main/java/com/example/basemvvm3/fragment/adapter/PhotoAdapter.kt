package com.example.basemvvm3.fragment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.basemvvm3.R
import com.example.basemvvm3.classes.data.PhotoItem
import com.example.basemvvm3.fragment.sub.SubFragment2EventListener
import kotlinx.android.synthetic.main.item_photo.view.*

class PhotoAdapter(
    private val eventListener: SubFragment2EventListener,
    private val aLiveData: LiveData<Boolean>? = null
) :
    ListAdapter<PhotoItem, VH>(PhotoItemDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val viewHolder = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return if (viewType == R.layout.item_photo)
            PhotoViewHolder(viewHolder)
        else ProgressBarViewHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        if (holder is PhotoViewHolder) {
            holder.bind(getItem(position), eventListener, aLiveData)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if ((getItem(position) as PhotoItem).id.isNotBlank()) R.layout.item_photo else R.layout.item_loading
    }
}

class PhotoViewHolder(itemView: View) : VH(itemView) {
    fun bind(
        item: PhotoItem,
        eventListener: SubFragment2EventListener?,
        aLiveData: LiveData<Boolean>?
    ) {
        itemView.apply {
            text1.text = item.title

            val imgUrl =
                "https://moodle.fhgr.ch/pluginfile.php/124614/mod_page/content/4/example.jpg"
            val options = RequestOptions()
            Glide.with(itemView.context)
                .load(imgUrl)
                .apply(options.fitCenter())
                .into(avatar)

            setOnClickListener {
                eventListener?.onItemClick(item)
            }

            //can also observe LiveData
            aLiveData?.observe(itemView.context as LifecycleOwner, Observer {
                //update item view base on this dude
            })
        }
    }
}

class ProgressBarViewHolder(itemView: View) : VH(itemView)

open class VH(itemView: View) : RecyclerView.ViewHolder(itemView)

object PhotoItemDiff : DiffUtil.ItemCallback<PhotoItem>() {

    override fun areItemsTheSame(oldItem: PhotoItem, newItem: PhotoItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PhotoItem, newItem: PhotoItem) = oldItem == newItem
}
