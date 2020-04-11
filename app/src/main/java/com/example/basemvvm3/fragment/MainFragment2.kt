package com.example.basemvvm3.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.basemvvm3.R
import com.example.basemvvm3.fragment.sub.SubFragment2
import com.example.basemvvm3.helper.MainNavigationFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_main_2.*

class MainFragment2 : MainNavigationFragment() {

    private val vm2: MainFragment2ViewModel by viewModels {
        object : AbstractSavedStateViewModelFactory(this, null) {
            override fun <T : ViewModel?> create(
                key: String,
                modelClass: Class<T>,
                handle: SavedStateHandle
            ): T {
                @Suppress("UNCHECKED_CAST")
                return MainFragment2ViewModel(handle) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main_2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewPager()

        fab.setOnClickListener(View.OnClickListener { view ->
            Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        })
    }

    private fun setupViewPager() {
        viewpager.adapter = object :
            FragmentStatePagerAdapter(childFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            private val mFragments = arrayListOf<Fragment>()
            private val mFragmentTitles = arrayListOf<String>()

            fun addFragment(fragment: Fragment, title: String) {
                mFragments.add(fragment)
                mFragmentTitles.add(title)
            }

            override fun getItem(position: Int) = mFragments[position]

            override fun getCount() = mFragments.size

            override fun getPageTitle(pos: Int): String = mFragmentTitles[pos]
        }.also {
            it.addFragment(SubFragment2(), "SUB 1")
            it.addFragment(SubFragment2(), "SUB 2")
            it.addFragment(SubFragment2(), "SUB 3")
        }

        tabs.setupWithViewPager(viewpager)
    }

    companion object {

        fun newInstance(): MainFragment2 {
            return MainFragment2()
        }
    }
}

//childFragmentManager:
//  Return a private FragmentManager for placing and managing Fragments
//     inside of this Fragment.

//parentFragmentManager:
//  Return the FragmentManager for interacting with fragments associated
//     with this fragment's activity