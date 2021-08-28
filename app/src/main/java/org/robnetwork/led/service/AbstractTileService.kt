package org.robnetwork.led.service

import android.os.Build
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
abstract class AbstractTileService : TileService() {
    protected val configLive: MutableLiveData<ConfigJSONData> = MutableLiveData()
    protected abstract val configObserver: Observer<ConfigJSONData>

    override fun onStopListening() {
        super.onStopListening()
        configLive.removeObserver(configObserver)
    }

    override fun onStartListening() {
        super.onStartListening()
        configLive.observeForever(configObserver)
        RetrofitClient.api.config().enqueue(ConfigCallback(this))
    }

    protected class ConfigCallback(val service: AbstractTileService) : Callback<ConfigJSONData> {
        override fun onFailure(call: Call<ConfigJSONData>, t: Throwable) {
            this.service.qsTile.state = Tile.STATE_UNAVAILABLE
            this.service.qsTile.updateTile()
            Log.e(this.javaClass.simpleName, t.localizedMessage, t)
        }

        override fun onResponse(call: Call<ConfigJSONData>, response: Response<ConfigJSONData>) {
            response.body()?.let { config -> service.configLive.value = config }
        }
    }
}