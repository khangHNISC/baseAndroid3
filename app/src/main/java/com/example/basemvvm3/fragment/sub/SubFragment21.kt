package com.example.basemvvm3.fragment.sub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.basemvvm3.R
import com.example.basemvvm3.classes.data.PersonItem
import com.example.basemvvm3.classes.data.PhotoItem
import com.example.basemvvm3.fragment.adapter.*
import kotlinx.android.synthetic.main.sub_fragment_2.*
import kotlin.random.Random

class SubFragment21: Fragment(){

    private var adapter : MultiViewItemAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        showItems(recyclerview, initList())
    }

    private fun initList(): List<Any>?{
        val l = mutableListOf<Any>()
        for((i, _) in (0..30).withIndex()){
            val chance = Random.nextInt(0,2)
            if(chance == 0){
                l.add(PersonItem(i,"MR$i", 20))
            }else{
                l.add(PhotoItem("","$i","MR$i", "",""))
            }
        }
        return l
    }

    //pass vm to adapter
    private fun showItems(recyclerView: RecyclerView, list: List<Any>?){
        if(adapter == null){
            val photoViewBinder = PhotoViewBinder()
            val personViewBinder = PersonViewBinder()
            @Suppress("UNCHECKED_CAST")
            val viewBinder = mapOf<ItemClass, MapTypeToVH>(
                photoViewBinder.modelClass to photoViewBinder as MapTypeToVH,
                personViewBinder.modelClass to personViewBinder as MapTypeToVH
            )
            adapter = MultiViewItemAdapter(viewBinder)
        }
        if(recyclerView.adapter == null){
            recyclerView.adapter = adapter
        }
        (recyclerView.adapter as MultiViewItemAdapter).submitList(list ?: emptyList())

        loading.visibility = View.INVISIBLE
        recyclerview.visibility = View.VISIBLE
    }
}