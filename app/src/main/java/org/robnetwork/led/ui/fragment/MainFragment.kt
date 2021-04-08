package org.robnetwork.led.ui.fragment

import android.content.Context
import android.graphics.Color
import android.util.Log
import com.github.dhaval2404.colorpicker.ColorPickerDialog
import com.google.android.material.slider.Slider
import org.robnetwork.led.R
import org.robnetwork.led.databinding.FragmentMainBinding
import org.robnetwork.led.model.MainData
import org.robnetwork.led.ui.MainActivity
import org.robnetwork.led.ui.MainViewModel

class MainFragment : BaseFragment<FragmentMainBinding, MainData, MainViewModel>() {
    override val layoutRes: Int = R.layout.fragment_main
    override val viewModelClass = MainViewModel::class.java
    override fun getModelStoreOwner() = activity as? MainActivity

    override fun updateUI(binding: FragmentMainBinding, data: MainData) {
        data.config?.let { config ->
            binding.pickColor1.text = config.color1
            config.color1?.let { binding.pickColor1.setBackgroundColor(Color.parseColor(it)) }
            binding.pickColor2.text = config.color2
            config.color2?.let { binding.pickColor2.setBackgroundColor(Color.parseColor(it)) }
            binding.sensibilityAuto.isChecked = config.autoMinMax
            binding.ambient.setOnClickListener {
                if (config.ambient) viewModel.ambientOff() else viewModel.ambientOn()
            }
            binding.brightnessSlider.value = config.brightness.toFloat()
        }
        data.currentState?.let {
            binding.color1.isChecked = false
            binding.color2.isChecked = false
            binding.dark.isChecked = false
            binding.gradient.isChecked = false
            binding.equalizer.isChecked = false

            when(it) {
                MainData.State.DARK -> binding.dark.isChecked = true
                MainData.State.GRADIENT -> binding.gradient.isChecked = true
                MainData.State.COLOR1 -> binding.color1.isChecked = true
                MainData.State.COLOR2 -> binding.color2.isChecked = true
                MainData.State.EQUALIZER -> binding.equalizer.isChecked = true
            }
        }
    }

    override fun setupUI(binding: FragmentMainBinding, context: Context) {
        super.setupUI(binding, context)
        binding.dark.setOnClickListener { viewModel.dark() }
        binding.gradient.setOnClickListener { viewModel.gradient() }
        binding.color1.setOnClickListener { viewModel.color1() }
        binding.color2.setOnClickListener { viewModel.color2() }
        binding.brightnessSlider.setLabelFormatter { it.toInt().toString() }
        binding.brightnessSlider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {}
            override fun onStopTrackingTouch(slider: Slider) {
                viewModel.brightness(slider.value.toInt())
            }
        })
        binding.equalizer.setOnClickListener { viewModel.equalizer() }
        binding.sensibilityAuto.setOnClickListener { viewModel.toggleAutoLevels() }
        binding.sensibilityPlus.setOnClickListener { viewModel.increaseSensibility() }
        binding.sensibilityMinus.setOnClickListener { viewModel.resetSensibility() }
        binding.pickColor1.setOnClickListener {
            ColorPickerDialog.Builder(context)
                .setPositiveButton("Apply")
                .setNegativeButton("Cancel")
                .setColorListener { i, s ->
                    binding.pickColor1.text = s
                    binding.pickColor1.setBackgroundColor(i)
                    Log.d(javaClass.simpleName, "Color : $i")
                    viewModel.changeColor1(s)
                }
                .show()
        }
        binding.pickColor2.setOnClickListener {
            ColorPickerDialog.Builder(context)
                .setPositiveButton("Apply")
                .setNegativeButton("Cancel")
                .setColorListener { i, s ->
                    binding.pickColor2.text = s
                    binding.pickColor2.setBackgroundColor(i)
                    Log.d(javaClass.simpleName, "Color : $i")
                    viewModel.changeColor2(s)
                }
                .show()
        }
        viewModel.getConfig()
    }
}