package com.example.basemvvm3.activities

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.basemvvm3.R
import com.example.basemvvm3.fragment.MainFragment2
import com.example.basemvvm3.helper.replaceFragment
import com.example.basemvvm3.helper.viewModelProvider
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var vm: MainActivityViewModel

    private lateinit var currentFragLabel: String

    //1. Call back patter
    interface OnDisplayName {
        fun onSelected(str: String)
    }

    private lateinit var callback: OnDisplayName

    private fun setOnSelectedListener(callback: OnDisplayName) {
        this.callback = callback
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            replaceFragment(
                R.id.frag_container2,
                MainFragment2.newInstance()
            )
        }
        vm = viewModelProvider(viewModelFactory)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        navController.addOnDestinationChangedListener { _, destination, _ ->
            currentFragLabel = destination.label.toString()
            if (::callback.isInitialized) {
                callback.onSelected(currentFragLabel)
            }
        }
    }

    override fun onAttachFragment(fragment: Fragment) {
        if (fragment is MainFragment2) {
            this.setOnSelectedListener(fragment)
        }
    }
}

