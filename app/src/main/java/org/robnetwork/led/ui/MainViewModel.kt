package org.robnetwork.led.ui

import androidx.lifecycle.MutableLiveData
import org.robnetwork.led.model.MainData

class MainViewModel(public override val data: MutableLiveData<MainData> = MutableLiveData(MainData())): BaseViewModel<MainData>() {
}