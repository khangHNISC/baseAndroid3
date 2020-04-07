
package com.example.basemvvm3.activities

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.basemvvm3.R
import com.example.basemvvm3.fragment.MainFragment2
import com.example.basemvvm3.helper.replaceFragment
import com.example.basemvvm3.helper.viewModelProvider
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(), MainFragment2.OnSelectedSthListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var vm: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            replaceFragment(R.id.frag_container2,
                MainFragment2.newInstance())
        }

        vm = viewModelProvider(viewModelFactory)
    }

    override fun onAttachFragment(fragment: Fragment) {
        //not apply for navigation
        //currently the only way to communicate between fragment -> activity is VM
        if(fragment is MainFragment2){
            fragment.setOnSelectedListener(this)
        }
    }

    override fun onSelected() {

    }
}

