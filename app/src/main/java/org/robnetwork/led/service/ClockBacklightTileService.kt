package org.robnetwork.led.service

import android.os.Build
import android.service.quicksettings.Tile
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import org.robnetwork.led.model.ConfigJSONData
import org.robnetwork.led.utils.RetrofitClient

@RequiresApi(Build.VERSION_CODES.N)
class ClockBacklightTileService : AbstractTileService() {
    override val configObserver: Observer<ConfigJSONData> = Observer {
        qsTile.state = if (it.clock_backlight) Tile.STATE_ACTIVE else Tile.STATE_INACTIVE
        qsTile.updateTile()
    }

    override fun onClick() {
        super.onClick()
        RetrofitClient.api.backlightToggle().enqueue(ConfigCallback(this))
    }
}