package com.pokeberry.app.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

object ConnectivityObserver {

    private val _noInternetVersion = MutableStateFlow(0)
    val noInternetVersion: StateFlow<Int> = _noInternetVersion

    private val _retrySignal = MutableSharedFlow<Unit>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val retrySignal: SharedFlow<Unit> = _retrySignal

    private var connectivityManager: ConnectivityManager? = null

    fun init(context: Context) {
        connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    fun isConnected(): Boolean {
        val cm = connectivityManager ?: return true
        val network = cm.activeNetwork ?: return false
        val capabilities = cm.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    fun notifyNoConnection() {
        _noInternetVersion.value++
    }

    fun signalRetry() {
        _retrySignal.tryEmit(Unit)
    }
}
