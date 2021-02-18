package org.robnetwork.led.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import org.robnetwork.led.model.ConfigJSONData
import org.robnetwork.led.model.LevelsJSONData
import org.robnetwork.led.model.MainData
import org.robnetwork.led.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(public override val data: MutableLiveData<MainData> = MutableLiveData(MainData())) :
    BaseViewModel<MainData>() {
    fun dark() = RetrofitClient.api.dark()
        .enqueue(StateCallback { update { it.copy(currentState = MainData.State.DARK) } })

    fun white() = RetrofitClient.api.white()
        .enqueue(StateCallback { update { it.copy(currentState = MainData.State.WHITE) } })

    fun gradient() = RetrofitClient.api.gradient()
        .enqueue(StateCallback { update { it.copy(currentState = MainData.State.GRADIENT) } })

    fun color1() = RetrofitClient.api.color1()
        .enqueue(StateCallback { update { it.copy(currentState = MainData.State.COLOR1) } })

    fun color2() = RetrofitClient.api.color2()
        .enqueue(StateCallback { update { it.copy(currentState = MainData.State.COLOR2) } })

    fun equalizer() = RetrofitClient.api.equalizer().enqueue(StateCallback { })
    fun on() = RetrofitClient.api.on().enqueue(StateCallback { })
    fun off() = RetrofitClient.api.off().enqueue(StateCallback { })
    fun toggleSound() = RetrofitClient.api.toggleSound().enqueue(StateCallback { })
    fun noiseStart() = RetrofitClient.api.noiseStart().enqueue(StateCallback { })
    fun moreLight() = RetrofitClient.api.moreLight().enqueue(ConfigCallback(this))
    fun lessLight() = RetrofitClient.api.lessLight().enqueue(ConfigCallback(this))
    fun changeColor1(color: String) = RetrofitClient.api.color1(color).enqueue(ConfigCallback(this))
    fun changeColor2(color: String) = RetrofitClient.api.color2(color).enqueue(ConfigCallback(this))
    fun getConfig() = RetrofitClient.api.config().enqueue(ConfigCallback(this))
    fun toggleAutoLevels() = RetrofitClient.api.toggleAutoLevels().enqueue(ConfigCallback(this))
    fun increaseSensibility() = RetrofitClient.api.increaseSensibility().enqueue(ConfigCallback(this))
    fun resetSensibility() = RetrofitClient.api.resetSensibility().enqueue(ConfigCallback(this))
    fun toggleSource() = RetrofitClient.api.toggleSource().enqueue(ConfigCallback(this))
    fun updateConfig() = data.value?.config?.let {
        RetrofitClient.api.updateConfig(it).enqueue(ConfigCallback(this))
    } ?: Log.e(this.javaClass.simpleName, "No config")

    fun getLevels(updateLevels: (LevelsJSONData) -> Unit) =
        RetrofitClient.api.levels().enqueue(LevelsCallback(this, updateLevels))

    private class LevelsCallback(
        val viewModel: MainViewModel,
        val updateLevels: (LevelsJSONData) -> Unit
    ) : Callback<LevelsJSONData> {
        override fun onFailure(call: Call<LevelsJSONData>, t: Throwable) {
            Log.e(this.javaClass.simpleName, t.localizedMessage, t)
        }

        override fun onResponse(call: Call<LevelsJSONData>, response: Response<LevelsJSONData>) {
            response.body()?.let { levels ->
                viewModel.update { data -> data.copy(levels = levels) }
                updateLevels(levels)
            }
        }
    }

    private class ConfigCallback(val viewModel: MainViewModel) : Callback<ConfigJSONData> {
        override fun onFailure(call: Call<ConfigJSONData>, t: Throwable) {
            Log.e(this.javaClass.simpleName, t.localizedMessage, t)
        }

        override fun onResponse(call: Call<ConfigJSONData>, response: Response<ConfigJSONData>) {
            response.body()
                ?.let { config -> viewModel.update { data -> data.copy(config = config) } }
        }
    }

    private class StateCallback(val onSuccess: () -> Unit) : Callback<Any> {
        override fun onFailure(call: Call<Any>, t: Throwable) {
            Log.e(this@StateCallback.javaClass.simpleName, t.localizedMessage, t)
            t.cause?.printStackTrace()
        }

        override fun onResponse(call: Call<Any>, response: Response<Any>) {
            Log.d(this@StateCallback.javaClass.simpleName, response.message())
            onSuccess()
        }
    }
}