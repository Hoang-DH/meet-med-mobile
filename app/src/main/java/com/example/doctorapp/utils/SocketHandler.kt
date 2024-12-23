package com.example.doctorapp.utils

import android.util.Log
import com.example.doctorapp.constant.Define
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import io.socket.engineio.client.transports.WebSocket

object SocketHandler {

    private lateinit var mSocket: Socket

    fun initSocket(userId: String) {
        val options = IO.Options()
        options.transports = arrayOf(WebSocket.NAME)
        options.query = "userId=$userId"
        try {
            mSocket = IO.socket(Define.Socket.SOCKET_URL, options)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getSocket(): Socket {
        return mSocket
    }
}