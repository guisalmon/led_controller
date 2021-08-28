package org.robnetwork.led.service

import android.os.Build
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import androidx.annotation.RequiresApi
import org.robnetwork.led.model.ConfigJSONData
import org.robnetwork.led.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@RequiresApi(Build.VERSION_CODES.N)
abstract class AbstractTileService : TileService() {

    override fun onStartListening() {
        super.onStartListening()

        RetrofitClient.api.config().enqueue(object : Callback<ConfigJSONData> {
            override fun onFailure(p0: Call<ConfigJSONData>, p1: Throwable) {
                qsTile.state = Tile.STATE_UNAVAILABLE
            }

            override fun onResponse(p0: Call<ConfigJSONData>, p1: Response<ConfigJSONData>) {
                qsTile.state = Tile.STATE_INACTIVE
            }
        })
    }
}