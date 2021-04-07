package org.robnetwork.led.ui

import android.view.View
import org.robnetwork.led.R
import org.robnetwork.led.databinding.ActivityMainBinding
import org.robnetwork.led.model.MainData

class MainActivity : BaseActivity<ActivityMainBinding, MainData, MainViewModel>() {
    override val layoutRes: Int = R.layout.activity_main
    override val viewModelClass = MainViewModel::class.java

    override fun setupUI(binding: ActivityMainBinding) {
        super.setupUI(binding)
        viewModel.getConfig()
    }

    override fun updateUI(binding: ActivityMainBinding, data: MainData) {
        when(data.connexionStatus) {
            MainData.ConnexionStatus.UNREACHABLE -> {
                binding.downView.visibility = View.VISIBLE
                binding.downText.visibility = View.VISIBLE
                binding.downText.setText(R.string.server_down)
            }
            MainData.ConnexionStatus.OFF -> {
                binding.downView.visibility = View.VISIBLE
                binding.downText.visibility = View.VISIBLE
                binding.downText.setText(R.string.equalizer_offline)
            }
            MainData.ConnexionStatus.ON -> {
                binding.downView.visibility = View.GONE
                binding.downText.visibility = View.GONE
                binding.downText.setText(R.string.server_down)
            }
        }
        data.config?.let { config ->
            binding.toolbar.toolbarPower.setOnClickListener {
                if (config.on) viewModel.off() else viewModel.on()
            }
            binding.toolbar.toolbarSoundToggle.setOnClickListener { viewModel.toggleSound() }
        }
    }
}