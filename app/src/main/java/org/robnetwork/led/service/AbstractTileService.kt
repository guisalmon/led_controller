package org.robnetwork.led.service

import android.os.Build
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import androidx.annotation.RequiresApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.robnetwork.led.utils.NetworkUtils

@RequiresApi(Build.VERSION_CODES.N)
abstract class AbstractTileService : TileService() {
    private val ioScope = CoroutineScope(Dispatchers.IO + Job())

    override fun onStartListening() {
        super.onStartListening()

        ioScope.launch {
            qsTile.state =
                if (NetworkUtils.isHostReachable("raspi4.local", 5000, 500))
                    Tile.STATE_INACTIVE else Tile.STATE_UNAVAILABLE
            qsTile.updateTile()
        }.start()
    }
}