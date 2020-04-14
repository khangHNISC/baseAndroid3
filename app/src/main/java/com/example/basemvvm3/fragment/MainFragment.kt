package com.example.basemvvm3.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.example.basemvvm3.R
import com.example.basemvvm3.activities.MainActivityViewModel
import com.example.basemvvm3.fragment.dialog.GeneralBottomDialog
import com.example.basemvvm3.fragment.dialog.GeneralCenterDialogWith2Buttons
import com.example.basemvvm3.helper.MainNavigationFragment
import com.example.basemvvm3.helper.viewModelProvider
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject

//share MainFragmentVM with Fragment2 scope activity
class MainFragment : MainNavigationFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var mainVm: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainVm = requireActivity().run {
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

        btn_dialog_center.setOnClickListener {
            GeneralCenterDialogWith2Buttons.newInstance()
                .show(this.childFragmentManager, CENTER_DIALOG)
        }

        btn_dialog_bottom.setOnClickListener {
            GeneralBottomDialog.newInstance().show(this.childFragmentManager, BOTTOM_DIALOG)
        }

        prepareToolBar(toolbar)
    }

    private fun prepareToolBar(toolbar: Toolbar) {
        //toolbar.setNavigationIcon(R.drawable.ic_home)
        toolbar.inflateMenu(R.menu.sample_action_menu)

        val menu = toolbar.menu
        when (AppCompatDelegate.getDefaultNightMode()) {
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM -> menu.findItem(R.id.menu_night_mode_system).isChecked =
                true
            AppCompatDelegate.MODE_NIGHT_YES -> menu.findItem(R.id.menu_night_mode_night).isChecked =
                true
            AppCompatDelegate.MODE_NIGHT_NO -> menu.findItem(R.id.menu_night_mode_day).isChecked =
                true
            AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY -> menu.findItem(R.id.menu_night_mode_auto).isChecked =
                true
            else -> menu.findItem(R.id.menu_night_mode_auto).isChecked = true
        }

        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_night_mode_system -> setNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                R.id.menu_night_mode_day -> setNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                R.id.menu_night_mode_night -> setNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                R.id.menu_night_mode_auto -> setNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
            }
            true
        }
    }

    private fun setNightMode(@AppCompatDelegate.NightMode nightMode: Int) {
        AppCompatDelegate.setDefaultNightMode(nightMode)

        if (Build.VERSION.SDK_INT >= 11) {
            requireActivity().recreate()
        }
    }

    //first time add: OnAttach -> OnCreate -> OnCreateView -> OnViewCreated -> OnActivityCreated
    //                                                  -> onViewStateRestored -> OnStart -> OnResume

    //   OnActivityCreated: After onActivityCreated and OnFragmentViewCreated

    //detach: onPause, onStop, onDestroyView
    //reattach: onCreateView -> OnViewCreated -> OnActivityCreated -> OnStart -> OnResume
    //destroy: onPause onStop onDestroyView onDestroy onDetach

    companion object {
        private const val CENTER_DIALOG = "center_dialog"
        private const val BOTTOM_DIALOG = "bottom_dialog"
    }
}