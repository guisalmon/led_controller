package org.robnetwork.led.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import android.view.ViewAnimationUtils
import androidx.annotation.StringRes
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
        when (data.connexionStatus) {
            MainData.ConnexionStatus.UNREACHABLE ->
                binding.takeIf { !it.downViewVisible() }?.revealDownView(R.string.server_down)
            MainData.ConnexionStatus.OFF ->
                binding.takeIf { !it.downViewVisible() }?.revealDownView(R.string.equalizer_offline)
            MainData.ConnexionStatus.ON ->
                binding.takeIf { it.downViewVisible() }?.hideDownView()
        }
        data.config?.let { config ->
            binding.toolbar.toolbarPower.setOnClickListener {
                if (config.on) viewModel.off() else viewModel.on()
            }
            binding.toolbar.toolbarSoundToggle.setOnClickListener { viewModel.toggleSound() }
            binding.toolbar.toolbarClock.setOnClickListener { viewModel.toggleClock() }
        }
    }

    private fun ActivityMainBinding.revealDownView(@StringRes textId: Int) {
        val powerHalfWidth = toolbar.toolbarPower.width / 2
        val cx = toolbar.toolbarPower.x.toInt() + powerHalfWidth
        val cy = toolbar.toolbarPower.y.toInt() + powerHalfWidth
        val finalRadius = (downView.height + powerHalfWidth).toFloat()
        ViewAnimationUtils
            .createCircularReveal(downView, cx, cy, powerHalfWidth.toFloat(), finalRadius)
            .setDuration(1000)
            .also {
                downView.visibility = View.VISIBLE
                it.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        downText.visibility = View.VISIBLE
                        downText.setText(textId)
                    }
                })
            }
            .start()
    }

    private fun ActivityMainBinding.hideDownView() {
        val powerHalfWidth = toolbar.toolbarPower.width / 2
        val cx = toolbar.toolbarPower.x.toInt() + powerHalfWidth
        val cy = toolbar.toolbarPower.y.toInt() + powerHalfWidth
        val radius = (downView.height + powerHalfWidth).toFloat()
        ViewAnimationUtils
            .createCircularReveal(downView, cx, cy, radius, powerHalfWidth.toFloat())
            .setDuration(1000)
            .also {
                downText.visibility = View.GONE
                it.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)
                        downView.visibility = View.GONE
                    }
                })
            }
            .start()
    }

    private fun ActivityMainBinding.downViewVisible(): Boolean = downView.visibility == View.VISIBLE
}