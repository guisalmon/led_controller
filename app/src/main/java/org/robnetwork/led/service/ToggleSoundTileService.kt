package org.robnetwork.led.service

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import org.robnetwork.led.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@RequiresApi(Build.VERSION_CODES.N)
class ToggleSoundTileService : AbstractTileService() {
    override fun onClick() {
        super.onClick()
        RetrofitClient.api.toggleSound().enqueue(object : Callback<Any> {
            override fun onFailure(call: Call<Any>, t: Throwable) {
                Log.e(this.javaClass.simpleName, t.localizedMessage, t)
            }

            override fun onResponse(call: Call<Any>, response: Response<Any>) {}
        })
    }
}