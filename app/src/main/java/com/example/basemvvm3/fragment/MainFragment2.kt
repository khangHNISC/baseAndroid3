package com.example.basemvvm3.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.basemvvm3.R
import com.example.basemvvm3.fragment.sub.SubFragment2
import com.example.basemvvm3.fragment.sub.SubFragment21
import com.example.basemvvm3.fragment.sub.SubFragment22
import com.example.basemvvm3.helper.MainNavigationFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_main_2.*
import javax.inject.Inject

class MainFragment2 : MainNavigationFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

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

        fab.setOnClickListener {
            Snackbar.make(view, "Here's a SnackBar", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    private fun setupViewPager() {
        //childFragmentManager here to stop fragments to recreate when switching tabs
        //FragmentStatePagerAdapter : better for manages ram (kills unused fragment children)
        viewpager.adapter = object : FragmentPagerAdapter(
            childFragmentManager,
            BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        ) {
            override fun getCount() = FRAG_LIST.size

            override fun getItem(position: Int) = FRAG_LIST[position]()

            override fun getPageTitle(position: Int) = FRAG_TITLES[position]
        }
        //viewpager.offscreenPageLimit = FRAG_LIST.size - 1
        //---- require for FragmentState
        tabs.setupWithViewPager(viewpager)

        //Meant for VP 2
        /*viewpager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int = FRAG_LIST.size

            override fun createFragment(position: Int): Fragment = FRAG_LIST[position]()
        }

        TabLayoutMediator(tabs, viewpager) { tab, position ->
            tab.text = FRAG_TITLES[position]
            //can set tag Icon here
        }.attach()*/
    }

    companion object {
        private val FRAG_TITLES = arrayOf(
            "SIMPLE RV",
            "MULTI RV",
            "PAGE LIST"
        )

        //weird shit but works if recreate this fragment
        private val FRAG_LIST = arrayOf(
            { SubFragment2() },
            { SubFragment21() },
            { SubFragment22() }
        )
    }
}

//childFragmentManager:
//  Return a private FragmentManager for placing and managing Fragments
//     inside of this Fragment.

//parentFragmentManager:
//  Return the FragmentManager for interacting with fragments associated
//     with this fragment's activity