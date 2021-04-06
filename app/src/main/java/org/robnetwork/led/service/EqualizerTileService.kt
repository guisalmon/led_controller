package org.robnetwork.led.service

import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import org.robnetwork.led.model.ConfigJSONData
import org.robnetwork.led.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@RequiresApi(Build.VERSION_CODES.N)
class EqualizerTileService : TileService() {
    private val configLive: MutableLiveData<ConfigJSONData> = MutableLiveData()
    private val configObserver: Observer<ConfigJSONData> = Observer {
        qsTile.state = if (it.on) Tile.STATE_ACTIVE else Tile.STATE_INACTIVE
        qsTile.updateTile()
    }

    override fun onBind(intent: Intent?): IBinder? {
        RetrofitClient.api.config().enqueue(ConfigCallback(this))
        return super.onBind(intent)
    }

    override fun onClick() {
        super.onClick()
        if (configLive.value?.on == true) {
            RetrofitClient.api.eqOff().enqueue(ConfigCallback(this))
        } else if (configLive.value != null) {
            RetrofitClient.api.eqOn().enqueue(ConfigCallback(this))
        }
    }

    override fun onStartListening() {
        super.onStartListening()
        configLive.observeForever(configObserver)
    }

    override fun onStopListening() {
        super.onStopListening()
        configLive.removeObserver(configObserver)
    }

    private class ConfigCallback(val service: EqualizerTileService) : Callback<ConfigJSONData> {
        override fun onFailure(call: Call<ConfigJSONData>, t: Throwable) {
            Log.e(this.javaClass.simpleName, t.localizedMessage, t)
        }

        override fun onResponse(call: Call<ConfigJSONData>, response: Response<ConfigJSONData>) {
            response.body()?.let { config -> service.configLive.value = config }
        }
    }
}