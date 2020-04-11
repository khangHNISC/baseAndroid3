package com.example.basemvvm3.activities

import android.content.res.Resources
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.basemvvm3.R
import com.example.basemvvm3.fragment.FragmentInfo
import com.example.basemvvm3.helper.NavigationHost
import com.example.basemvvm3.helper.replaceFragment
import com.example.basemvvm3.helper.viewModelProvider
import com.google.android.material.navigation.NavigationView
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(), NavigationHost {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var vm: MainActivityViewModel

    private lateinit var navController: NavController

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

        /*if (savedInstanceState == null) {
            replaceFragment(
                R.id.frag_container2,
                FragmentInfo.newInstance()
            )
        }*/

        vm = viewModelProvider(viewModelFactory)

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment? ?: return

        navController = host.navController

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
        //attach listener here
    }

    override fun registerToolbarWithNavigation(toolbar: Toolbar) {
        val appBarConfiguration = AppBarConfiguration(TOP_LEVEL_DESTINATIONS, drawer_layout)
        toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    private fun setUpTranslucentStatusBar(){
        //+ enable container fitsSystemWindow = true
        //drawer_layout.setOnApplyWindowInsetsListener ()
        container.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION

    }

    companion object {
        private val TOP_LEVEL_DESTINATIONS = setOf(
            R.id.navigation_main_fragment,
            R.id.navigation_main_fragment_2
        )
    }
}

