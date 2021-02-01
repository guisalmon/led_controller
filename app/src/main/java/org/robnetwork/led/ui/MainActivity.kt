package org.robnetwork.led.ui

import android.view.View
import org.robnetwork.led.R
import org.robnetwork.led.databinding.ActivityMainBinding
import org.robnetwork.led.model.MainData

class MainActivity: BaseActivity<ActivityMainBinding, MainData, MainViewModel>() {
    override val layoutRes: Int = R.layout.activity_main
    override val viewModelClass = MainViewModel::class.java

    override fun updateUI(binding: ActivityMainBinding, data: MainData) {
        binding.toolbar.toolbarSave.setOnClickListener { viewModel.updateConfig() }
    }
}