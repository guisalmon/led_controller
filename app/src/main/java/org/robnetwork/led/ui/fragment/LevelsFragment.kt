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
import org.robnetwork.led.model.MainData
import org.robnetwork.led.ui.MainActivity
import org.robnetwork.led.ui.MainViewModel

class LevelsFragment: BaseFragment<FragmentLevelsBinding, MainData, MainViewModel>() {
    override val layoutRes: Int = R.layout.fragment_levels
    override val viewModelClass = MainViewModel::class.java
    override fun getModelStoreOwner() = activity as? MainActivity

    override fun updateUI(binding: FragmentLevelsBinding, data: MainData) {
        data.levels?.let { levels ->
            val revLevels = Array(12) { Array(levels.memSize) { 0f } }
            levels.levels.forEachIndexed { index, list ->
                list.forEachIndexed { column, lvl ->
                    revLevels[column][index] = lvl
                }
            }

            binding.equalizer.removeAllViews()
            for (i in 0..11) {
                val item = layoutInflater.inflate(R.layout.item_level, null)
                DataBindingUtil.bind<ItemLevelBinding>(item)?.let { itemBinding ->
                    itemBinding.max.text = "${revLevels[i].max()}"
                    itemBinding.min.text = "${revLevels[i].min()}"
                    item.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT)
                    binding.equalizer.addView(item)
                    for (lvl in revLevels[i]) {
                        val stripe = View(context)
                        stripe.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark))
                        stripe.layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1)
                        itemBinding.scale.addView(stripe)

                        revLevels[i].min()?.let { min ->
                            revLevels[i].max()?.let { max ->
                                val y = ((lvl - min) / (max - min)) * binding.equalizer.height
                                Log.e(javaClass.simpleName, "min $min, max $max, y $y, height ${binding.equalizer.height}")
                                stripe.y = y
                            }
                        }
                    }
                }
            }
        }
    }

    override fun setupUI(binding: FragmentLevelsBinding, context: Context) {
        super.setupUI(binding, context)
        viewModel.getLevels()
    }
}