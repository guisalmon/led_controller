package org.robnetwork.led.ui

import android.content.Context
import android.util.Log
import android.widget.Button
import androidx.core.content.ContextCompat
import com.github.dhaval2404.colorpicker.ColorPickerDialog
import org.robnetwork.led.R
import org.robnetwork.led.databinding.FragmentMainBinding
import org.robnetwork.led.model.MainData

class MainFragment: BaseFragment<FragmentMainBinding, MainData, MainViewModel>() {
    override val layoutRes: Int = R.layout.fragment_main
    override val viewModelClass = MainViewModel::class.java

    override fun getModelStoreOwner() = activity as? MainActivity

    override fun updateUI(binding: FragmentMainBinding, data: MainData) {
        binding.dark.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.black))
        binding.dark.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        binding.white.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.black))
        binding.white.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        binding.gradient.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.black))
        binding.gradient.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        binding.color1.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.black))
        binding.color1.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        binding.color2.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.black))
        binding.color2.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        binding.pickColor1.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.black))
        binding.pickColor1.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        binding.pickColor2.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.black))
        binding.pickColor2.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        binding.moreLight.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.black))
        binding.moreLight.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        binding.lessLight.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.black))
        binding.lessLight.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        binding.on.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.black))
        binding.on.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        binding.off.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.black))
        binding.off.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))

        data.currentState?.let { state ->
            when (state) {
                MainData.State.DARK -> state.colorButton(binding.dark)
                MainData.State.WHITE -> state.colorButton(binding.white)
                MainData.State.GRADIENT -> state.colorButton(binding.gradient)
                MainData.State.COLOR1 -> state.colorButton(binding.color1)
                MainData.State.COLOR2 -> state.colorButton(binding.color2)
            }
        }
    }

    override fun setupUI(binding: FragmentMainBinding, context: Context) {
        super.setupUI(binding, context)
        binding.dark.setOnClickListener { viewModel.dark() }
        binding.white.setOnClickListener { viewModel.white() }
        binding.gradient.setOnClickListener { viewModel.gradient() }
        binding.color1.setOnClickListener { viewModel.color1() }
        binding.color2.setOnClickListener { viewModel.color2() }
        binding.moreLight.setOnClickListener { viewModel.moreLight() }
        binding.lessLight.setOnClickListener { viewModel.lessLight() }
        binding.on.setOnClickListener { viewModel.on() }
        binding.off.setOnClickListener { viewModel.off() }
        binding.pickColor1.setOnClickListener {
            ColorPickerDialog.Builder(context)
                .setPositiveButton("Apply")
                .setNegativeButton("Cancel")
                .setColorListener { i, s ->
                    binding.color1.text = s
                    binding.color1.setBackgroundColor(i)
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
                    binding.color2.text = s
                    binding.color2.setBackgroundColor(i)
                    Log.d(javaClass.simpleName, "Color : $i")
                    viewModel.changeColor2(s)
                }
                .show()
        }
    }

    private fun Button.setColors(bgColor: Int, fgColor: Int) {
        background = ContextCompat.getDrawable(context, bgColor)
        setTextColor(ContextCompat.getColor(context, fgColor))
    }
}