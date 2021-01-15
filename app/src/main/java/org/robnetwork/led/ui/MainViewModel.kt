package org.robnetwork.led.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import org.robnetwork.led.model.MainData
import org.robnetwork.led.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(public override val data: MutableLiveData<MainData> = MutableLiveData(MainData())): BaseViewModel<MainData>() {

    fun dark() = RetrofitClient.api.dark().enqueue(StateCallBack { update { it.copy(currentState = MainData.State.DARK) } })
    fun white() = RetrofitClient.api.white().enqueue(StateCallBack { update { it.copy(currentState = MainData.State.WHITE) } })
    fun gradient() = RetrofitClient.api.gradient().enqueue(StateCallBack { update { it.copy(currentState = MainData.State.GRADIENT) } })
    fun color1() = RetrofitClient.api.color1().enqueue(StateCallBack { update { it.copy(currentState = MainData.State.COLOR1) } })
    fun color2() = RetrofitClient.api.color2().enqueue(StateCallBack { update { it.copy(currentState = MainData.State.COLOR2) } })

    private class StateCallBack(val onSuccess: () -> Unit): Callback<Any> {
        override fun onFailure(call: Call<Any>, t: Throwable) {
            Log.e(this@StateCallBack.javaClass.simpleName, t.localizedMessage, t)
        }

        override fun onResponse(call: Call<Any>, response: Response<Any>) {
            Log.d(this@StateCallBack.javaClass.simpleName, response.message())
            onSuccess()
        }
    }
}