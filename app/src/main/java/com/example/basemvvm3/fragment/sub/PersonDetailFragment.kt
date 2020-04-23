package com.example.basemvvm3.fragment.sub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.basemvvm3.R
import com.example.basemvvm3.helper.MainNavigationFragment
import kotlinx.android.synthetic.main.detail_person.*
import timber.log.Timber

class PersonDetailFragment : MainNavigationFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.detail_person, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Timber.e(findNavController().currentDestination?.label.toString())
    }

    override fun onStart() {
        super.onStart()

        requireNotNull(arguments).apply {
            val personName = PersonDetailFragmentArgs.fromBundle(this).personName
            btn.text = personName
        }
    }
}