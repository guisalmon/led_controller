package org.robnetwork.led.utils

import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.Socket

object NetworkUtils {
    fun isHostReachable(serverAddress: String, port: Int, timeoutMS: Int): Boolean {
        var connected = false
        try {
            Socket().also {
                it.connect(
                    InetSocketAddress(InetAddress.getByName(serverAddress), port),
                    timeoutMS
                )
            }.takeIf { it.isConnected }?.let {
                it.close()
                connected = true
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            return connected
        }
    }
}