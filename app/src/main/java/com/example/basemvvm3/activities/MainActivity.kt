
package com.example.basemvvm3.activities

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.basemvvm3.R
import com.example.basemvvm3.helper.viewModelProvider
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var vm: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        vm = viewModelProvider(viewModelFactory)
    }
}

/**
 * object : AbstractSavedStateViewModelFactory(this, null) {
override fun <T : ViewModel?> create(
key: String,
modelClass: Class<T>,
handle: SavedStateHandle
): T {
@Suppress("UNCHECKED_CAST")
return MainActivityViewModel(repo, handle) as T
}
}
 */
