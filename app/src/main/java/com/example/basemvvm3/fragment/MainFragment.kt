package com.example.basemvvm3.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.basemvvm3.R
import com.example.basemvvm3.helper.viewModelProvider
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject

//MainFragment1 Call back to MainActivity and Main Activity shares MainActivityViewModel with MainFragment2
//MainFragment1 directly shares MainActivityViewModel with MainFragment2

class MainFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var vm: MainFragmentViewModel

    private val edit by lazy { arguments?.getBoolean(DATA, false) ?: false }

    //1. Call back
    interface OnSelectedSthListener {
        fun onSelected()
    }

    private lateinit var callback: OnSelectedSthListener

    fun setOnSelectedListener(callback: OnSelectedSthListener) {
        this.callback = callback
    }

    //somewhere in here will do callback.onSelected()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = requireActivity().run {
            viewModelProvider(viewModelFactory)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_press.setOnClickListener {
            callback.onSelected()
        }

        btn_press2.setOnClickListener {
            vm.setHello()
        }
    }

    companion object {
        private const val DATA = "DATA"

        fun newInstance(data: Boolean = false): MainFragment {
            return MainFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(DATA, data)
                }
            }
        }
    }
}