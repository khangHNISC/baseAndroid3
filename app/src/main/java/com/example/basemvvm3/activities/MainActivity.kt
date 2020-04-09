package com.example.basemvvm3.activities

import android.content.res.Resources
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.basemvvm3.R
import com.example.basemvvm3.fragment.FragmentInfo
import com.example.basemvvm3.helper.replaceFragment
import com.example.basemvvm3.helper.viewModelProvider
import com.google.android.material.navigation.NavigationView
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var vm: MainActivityViewModel

    private lateinit var appBarConfiguration: AppBarConfiguration

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
                FragmentInfo.newInstance()
            )
        }

        vm = viewModelProvider(viewModelFactory)

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment? ?: return

        val navController = host.navController

        appBarConfiguration = AppBarConfiguration(navController.graph, drawer_layout)

        setupActionBarWithNavController(navController, appBarConfiguration)

        (nav_view as NavigationView).setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val dest: String = try {
                resources.getResourceName(destination.id)
            } catch (e: Resources.NotFoundException) {
                destination.id.toString()
            }

            if (::callback.isInitialized) {
                callback.onSelected(dest)
            }
        }
    }

    override fun onAttachFragment(fragment: Fragment) {
        if (fragment is FragmentInfo) {
            this.setOnSelectedListener(fragment)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.my_nav_host_fragment).navigateUp(appBarConfiguration)
    }
}

