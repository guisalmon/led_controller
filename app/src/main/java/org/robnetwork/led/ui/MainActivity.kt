package org.robnetwork.led.ui

import org.robnetwork.led.R
import org.robnetwork.led.databinding.ActivityMainBinding
import org.robnetwork.led.model.MainData

class MainActivity : BaseActivity<ActivityMainBinding, MainData, MainViewModel>() {
    override val layoutRes: Int = R.layout.activity_main
    override val viewModelClass = MainViewModel::class.java

    override fun updateUI(binding: ActivityMainBinding, data: MainData) {
        data.config?.let { config ->
            binding.toolbar.toolbarPower.setOnClickListener {
                if (config.on) viewModel.off() else viewModel.on()
            }
            binding.toolbar.toolbarSoundToggle.setOnClickListener { viewModel.toggleSound() }
        }
    }
}