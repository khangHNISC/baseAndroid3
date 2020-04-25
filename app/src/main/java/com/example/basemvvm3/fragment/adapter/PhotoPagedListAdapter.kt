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
import com.example.basemvvm3.helper.Result
import kotlinx.android.synthetic.main.item_loading.view.*
import kotlinx.android.synthetic.main.item_photo.view.*

class PhotoPagedListAdapter(
    private val retry: () -> Unit
) :
    PagedListAdapter<PhotoItem, RecyclerView.ViewHolder>(JustPhotoItemDiff) {
    private var networkState: Result<Unit>? = null

    //requires to get a PagedList (collection load data Async) of Item from VM
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return when (viewType) {
            R.layout.item_photo -> PhotoViewHolder(inflater)
            R.layout.item_loading -> ProgressBarViewHolder(inflater, retry)
            else -> throw IllegalArgumentException("unknown view type $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.item_photo -> (holder as PhotoViewHolder).bind(getItem(position))
            R.layout.item_loading -> (holder as ProgressBarViewHolder).bind(networkState)
        }
    }

    private fun hasExtraRow() = networkState != null && networkState != Result.Success(Unit)

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.item_loading
        } else {
            R.layout.item_photo
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    fun setNetworkState(newNetworkState: Result<Unit>) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()

        if (hadExtraRow != hasExtraRow) {
            //LOADED -> others or others -> LOADED
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            //LOADING -> FAILED or FAILED -> LOADING
            notifyItemChanged(itemCount - 1)
        }
    }
}

class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(item: PhotoItem?) {
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

class ProgressBarViewHolder(itemView: View, private val retry: () -> Unit) : RecyclerView.ViewHolder(itemView) {
    fun bind(networkState: Result<Unit>?) {
        itemView.apply {
            progressBar.visibility = toVisibility(networkState is Result.Loading)
            retryBtn.visibility = toVisibility(networkState is Result.Error)
            retryBtn.setOnClickListener {
                retry()
            }
        }
    }

    companion object {
        fun toVisibility(constraint : Boolean): Int {
            return if (constraint) {
                View.VISIBLE
            } else {
                View.GONE
            }
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
