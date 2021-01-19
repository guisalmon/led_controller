package org.robnetwork.led.ui

import org.robnetwork.led.databinding.FragmentLevelsBinding
import org.robnetwork.led.model.MainData

class LevelsFragment: BaseFragment<FragmentLevelsBinding, MainData, MainViewModel>() {
    override val layoutRes: Int = org.robnetwork.led.R.layout.fragment_levels
    override val viewModelClass = MainViewModel::class.java
    override fun getModelStoreOwner() = activity as? MainActivity

    override fun updateUI(binding: FragmentLevelsBinding, data: MainData) {
        TODO("Not yet implemented")
    }
}