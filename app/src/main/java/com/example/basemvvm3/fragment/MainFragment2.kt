package com.example.basemvvm3.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.basemvvm3.R
import com.example.basemvvm3.classes.data.PersonItem
import com.example.basemvvm3.fragment.sub.SubFragment2
import com.example.basemvvm3.fragment.sub.SubFragment21
import com.example.basemvvm3.fragment.sub.SubFragment22
import com.example.basemvvm3.helper.MainNavigationFragment
import com.example.basemvvm3.helper.viewModelProvider
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_main_2.*
import javax.inject.Inject

class MainFragment2 : MainNavigationFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var vm: MainFragment2ViewModel

    /*private val vm2: MainFragment2ViewModel by viewModels {
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
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = requireActivity().viewModelProvider(viewModelFactory)
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

        fab.setOnClickListener{
            Snackbar.make(view, "Here's a SnackBar", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        vm.navigateToPersonDetail.observe(viewLifecycleOwner, Observer { personItem ->
            openPersonDetails(personItem)
        })
    }

    private fun setupViewPager() {
        viewpager.adapter = object : FragmentStatePagerAdapter(childFragmentManager) {
            //FragmentStatePagerAdapter for large number of tabs
            override fun getItem(position: Int) = FRAG_LIST[position]()

            override fun getCount() = FRAG_LIST.size

            override fun getPageTitle(pos: Int): String = FRAG_TITLES[pos]
        }
        viewpager.offscreenPageLimit = FRAG_LIST.size
        tabs.setupWithViewPager(viewpager)
    }

    companion object {

        private val FRAG_TITLES = arrayOf(
            "SIMPLE RV",
            "MULTI RV",
            "PAGE LIST"
        )

        private val FRAG_LIST = arrayOf(
            { SubFragment2() },
            { SubFragment21() },
            { SubFragment22() }
        )
    }

    private fun openPersonDetails(personItem: PersonItem) {
        val navController = findNavController()
        //Timber.d(navController.currentDestination?.displayName) --- get the display name of current navController
        val action =
            MainFragment2Directions.actionNavigationMainFragment2ToPersonDetailFragment2(personName = personItem.name)
        navController.navigate(action)
    }
}

//childFragmentManager:
//  Return a private FragmentManager for placing and managing Fragments
//     inside of this Fragment.

//parentFragmentManager:
//  Return the FragmentManager for interacting with fragments associated
//     with this fragment's activity