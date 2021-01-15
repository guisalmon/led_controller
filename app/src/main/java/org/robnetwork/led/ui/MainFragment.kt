package org.robnetwork.led.ui

import android.content.Context
import android.widget.Button
import androidx.core.content.ContextCompat
import org.robnetwork.led.R
import org.robnetwork.led.databinding.FragmentMainBinding
import org.robnetwork.led.model.MainData

class MainFragment: BaseFragment<FragmentMainBinding, MainData, MainViewModel>() {
    override val layoutRes: Int = R.layout.fragment_main
    override val viewModelClass = MainViewModel::class.java

    override fun getModelStoreOwner() = activity as? MainActivity

    override fun updateUI(binding: FragmentMainBinding, data: MainData) {
        binding.dark.setColors(R.color.black, R.color.white)
        binding.white.setColors(R.color.black, R.color.white)
        binding.gradient.setColors(R.color.black, R.color.white)
        binding.color1.setColors(R.color.black, R.color.white)
        binding.color2.setColors(R.color.black, R.color.white)
        data.currentState?.let { state ->
            when (state) {
                MainData.State.DARK -> binding.dark.setColors(state.color, state.textColor)
                MainData.State.WHITE -> binding.white.setColors(state.color, state.textColor)
                MainData.State.GRADIENT -> binding.gradient.setColors(state.color, state.textColor)
                MainData.State.COLOR1 -> binding.color1.setColors(state.color, state.textColor)
                MainData.State.COLOR2 -> binding.color2.setColors(state.color, state.textColor)
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
    }

    private fun Button.setColors(bgColor: Int, fgColor: Int) {
        background = ContextCompat.getDrawable(context, bgColor)
        setTextColor(fgColor)
    }
}