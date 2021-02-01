package org.robnetwork.led.ui.fragment

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import org.robnetwork.led.R
import org.robnetwork.led.databinding.FragmentLevelsBinding
import org.robnetwork.led.databinding.ItemLevelBinding
import org.robnetwork.led.model.ConfigJSONData
import org.robnetwork.led.model.MainData
import org.robnetwork.led.ui.MainActivity
import org.robnetwork.led.ui.MainViewModel

class LevelsFragment : BaseFragment<FragmentLevelsBinding, MainData, MainViewModel>() {
    override val layoutRes: Int = R.layout.fragment_levels
    override val viewModelClass = MainViewModel::class.java
    override fun getModelStoreOwner() = activity as? MainActivity

    override fun updateUI(binding: FragmentLevelsBinding, data: MainData) {}

    override fun setupUI(binding: FragmentLevelsBinding, context: Context) {
        super.setupUI(binding, context)
        viewModel.getLevels { displayLevels(it.reverseLevels(), binding) }
    }

    private fun displayLevels(
        levels: Array<Array<Float>>,
        binding: FragmentLevelsBinding
    ) {
        binding.equalizer.removeAllViews()
        viewModel.data.value?.config?.let {
            for (i in 0..11) buildEq(i, levels[i], it, binding)
        }
    }

    private fun buildEq(
        index: Int,
        levels: Array<Float>,
        config: ConfigJSONData,
        binding: FragmentLevelsBinding
    ) {
        val min = 0f
        val max = 500f

        layoutInflater.inflate(R.layout.item_level, null).apply {
            DataBindingUtil.bind<ItemLevelBinding>(this)?.let { itemBinding ->
                itemBinding.max.text = "$max"
                itemBinding.min.text = "$min"
                layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                binding.equalizer.addView(this)
                for (lvl in levels)
                    buildStripe(itemBinding, lvl, min, max, binding.equalizer.height)

                val minY = (config.meanMinLvls[index] - min) / (max - min)
                Log.w(
                    javaClass.simpleName,
                    "min = $min, configMin = ${config.meanMinLvls[index]}, progressMin = $minY"
                )
                itemBinding.minSlider.setProgress(minY)
                itemBinding.minSlider.setOnSliderProgressChangeListener { progress ->
                    config.meanMinLvls[index] = (max * progress)
                    viewModel.update { it.copy(config = config) }
                }
                val maxY = (config.meanMaxLvls[index] - min) / (max - min)
                Log.w(
                    javaClass.simpleName,
                    "max = $max, configMax = ${config.meanMaxLvls[index]}, progressMax = $maxY"
                )
                itemBinding.maxSlider.setProgress(maxY)
                itemBinding.maxSlider.setOnSliderProgressChangeListener { progress ->
                    config.meanMaxLvls[index] = (max * progress)
                    viewModel.update { it.copy(config = config) }
                }
            }
        }
    }

    private fun buildStripe(
        itemBinding: ItemLevelBinding,
        lvl: Float,
        minLvl: Float,
        maxLvl: Float,
        eqHeight: Int
    ) {
        val stripe = View(context)
        stripe.setBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.colorPrimaryDark
            )
        )
        stripe.layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1)
        itemBinding.scale.addView(stripe)

        val y = eqHeight - ((lvl - minLvl) / (maxLvl - minLvl) * eqHeight)
        stripe.y = y
    }
}