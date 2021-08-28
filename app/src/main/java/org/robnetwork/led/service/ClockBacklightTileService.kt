package org.robnetwork.led.service

import android.os.Build
import android.service.quicksettings.Tile
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import org.robnetwork.led.model.ConfigJSONData
import org.robnetwork.led.utils.RetrofitClient

@RequiresApi(Build.VERSION_CODES.N)
class ClockBacklightTileService : AbstractTileService() {
    override val configObserver: Observer<ConfigJSONData> = Observer {
        qsTile.state = Tile.STATE_INACTIVE
    }

    override fun onClick() {
        super.onClick()
        RetrofitClient.api.backlightToggle().enqueue(ConfigCallback(this))
    }
}