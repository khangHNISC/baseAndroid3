package com.example.basemvvm3.fragment.adapter

import android.annotation.SuppressLint
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

internal class PhotoAdapter(
    private val eventListener: SubFragment2EventListener,
    private val aLiveData: LiveData<Boolean>? = null
) : ListAdapter<Any, PhotoGeneralViewHolder>(PhotoItemDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoGeneralViewHolder {
        //Timber.e("2. createVH $viewType")
        val inflater = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return when (viewType) {
            R.layout.item_photo -> PhotoGeneralViewHolder.PhotoViewHolder(inflater)
            R.layout.item_loading -> PhotoGeneralViewHolder.ProgressBarViewHolder(inflater)
            else -> throw IllegalStateException("Invalid viewType")
        }
    }

    override fun onBindViewHolder(holder: PhotoGeneralViewHolder, position: Int) {
        //Timber.e("3. bind data from Adapter to VH at $position to ${VH::class.java}")
        if (holder is PhotoGeneralViewHolder.PhotoViewHolder) {
            holder.bind(getItem(position) as PhotoItem, eventListener, aLiveData)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (val item = getItem(position)) {
            is PhotoItem -> R.layout.item_photo
            is PhotoLoading -> R.layout.item_loading
            else -> throw IllegalStateException("Unknown type: ${item::class.java.simpleName}")
        }
    }

    override fun onViewAttachedToWindow(holder: PhotoGeneralViewHolder) {
        super.onViewAttachedToWindow(holder)
        //Timber.e("4. Attach")
    }

    override fun onViewRecycled(holder: PhotoGeneralViewHolder) {
        super.onViewRecycled(holder)
    }

    override fun onViewDetachedFromWindow(holder: PhotoGeneralViewHolder) {
        super.onViewDetachedFromWindow(holder)
        //Timber.e("5. Detach")
    }
}

internal sealed class PhotoGeneralViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    class ProgressBarViewHolder(itemView: View) : PhotoGeneralViewHolder(itemView)

    class PhotoViewHolder(itemView: View) : PhotoGeneralViewHolder(itemView) {
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
}

object PhotoLoading

internal object PhotoItemDiff : DiffUtil.ItemCallback<Any>() {
    override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
        return when {
            oldItem === PhotoLoading && newItem === PhotoLoading -> true
            oldItem is PhotoItem && newItem is PhotoItem -> oldItem.id == newItem.id
            else -> false
        }
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
        return when {
            oldItem is PhotoItem && newItem is PhotoItem -> oldItem == newItem
            else -> true
        }
    }

}
