package com.example.basemvvm3.fragment.sub

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.basemvvm3.R
import com.example.basemvvm3.classes.data.PhotoItem
import com.example.basemvvm3.fragment.adapter.PhotoAdapter
import com.example.basemvvm3.fragment.adapter.PhotoLoading
import com.example.basemvvm3.helper.Result
import com.example.basemvvm3.helper.checkAllMatched
import com.example.basemvvm3.helper.viewModelProvider
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.sub_fragment_2.*
import javax.inject.Inject

class SubFragment2 : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var vm: SubFragment2ViewModel

    private var adapter: PhotoAdapter? = null

    private lateinit var listPhoto: List<PhotoItem>

    private var isNotLoading: Boolean = true

    private var indexList: Int = 0 //for purpose of demo.

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
            vm.loadPhoto()
        }

        vm.isLoading.observe(viewLifecycleOwner, Observer {
            if(!it) {
                loading.visibility = View.INVISIBLE
                recyclerview.visibility = View.VISIBLE
            }
        })

        vm.photoDataUI.observe(viewLifecycleOwner, Observer { result ->
            listPhoto = result
            showPhotoItem(recyclerview, getSubList(listPhoto, true))
        })

        vm.errorMessage.observe(viewLifecycleOwner, Observer {

        })

        //vm.loadPhoto()
    }

    private fun showPhotoItem(rv: RecyclerView, list: List<Any>) {
        if (adapter == null) {
            adapter = PhotoAdapter(vm)

            recyclerview.apply {
                adapter = this@SubFragment2.adapter
                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        onScheduleScrolled(recyclerView)
                    }
                })
            }
        }
        if (rv.adapter == null) {
            rv.adapter = adapter
        }
        (rv.adapter as PhotoAdapter).submitList(list)

        if (!isNotLoading) isNotLoading = true
        swipeRefreshLayout.isRefreshing = false
    }

    /**
     * this is fake data
     */
    private fun getSubList(list: List<Any>, resetIndex: Boolean = false): List<Any> {
        if (resetIndex) indexList = 0
        indexList += 40
        val listAdapter = list.subList(0, indexList).toMutableList()
        if (indexList < list.size) {
            listAdapter.add(PhotoLoading)
        }
        return listAdapter
    }

    private fun onScheduleScrolled(rv: RecyclerView) {
        val layoutManager = (rv.layoutManager) as LinearLayoutManager
        val last = layoutManager.findLastVisibleItemPosition()
        val total = rv.adapter?.itemCount ?: 0
        if (last >= total - 1 && isNotLoading) {
            isNotLoading = false
            Handler().postDelayed({
                showPhotoItem(recyclerview, getSubList(listPhoto)) //vm.getPhoto()
            }, 2000)//purpose of demo
        }
    }
}