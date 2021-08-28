package org.robnetwork.led.service

import android.os.Build
import android.service.quicksettings.Tile
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import org.robnetwork.led.model.ConfigJSONData
import org.robnetwork.led.utils.RetrofitClient

@RequiresApi(Build.VERSION_CODES.N)
class EqualizerTileService : AbstractTileService() {
    override val configObserver: Observer<ConfigJSONData> = Observer {
        qsTile.state = if (it.on) Tile.STATE_ACTIVE else Tile.STATE_INACTIVE
        qsTile.updateTile()
    }

    override fun onClick() {
        super.onClick()
        if (configLive.value?.on == true) {
            RetrofitClient.api.eqOff().enqueue(ConfigCallback(this))
        } else if (configLive.value != null) {
            RetrofitClient.api.eqOn().enqueue(ConfigCallback(this))
        }
    }
}