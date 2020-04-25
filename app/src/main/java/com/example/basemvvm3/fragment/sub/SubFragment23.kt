package com.example.basemvvm3.fragment.sub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import com.example.basemvvm3.R
import com.example.basemvvm3.classes.data.PhotoItem
import com.example.basemvvm3.fragment.adapter.PhotoPagedListAdapter
import com.example.basemvvm3.helper.Result
import com.example.basemvvm3.helper.viewModelProvider
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.sub_fragment_2.*
import javax.inject.Inject

class SubFragment23: DaggerFragment(){

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var adapter: PhotoPagedListAdapter? = null

    private lateinit var vm: SubFragment23ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = viewModelProvider(viewModelFactory)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.sub_fragment_2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipeRefreshLayout.setOnRefreshListener {
            vm.refresh()
        }

        vm.netWorkState.observe(viewLifecycleOwner, Observer {

        })

        vm.refreshState.observe(viewLifecycleOwner, Observer {
            swipeRefreshLayout.isRefreshing = it == Result.Loading
        })

        vm.photoDataUI.observe(viewLifecycleOwner, Observer {
            showPhotoItem(recyclerview, it)
        })
    }

    private fun showPhotoItem(rv: RecyclerView, list: PagedList<PhotoItem>) {
        if (adapter == null) {
            adapter = PhotoPagedListAdapter()

            recyclerview.apply {
                adapter = this@SubFragment23.adapter
            }
        }
        if (rv.adapter == null) {
            rv.adapter = adapter
        }
        (rv.adapter as PhotoPagedListAdapter).submitList(list)

        loading.visibility = View.INVISIBLE
        recyclerview.visibility = View.VISIBLE
    }
}