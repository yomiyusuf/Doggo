package com.yomi.doggo.util

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.lifecycle.LiveData

/**
 * Utility object to monitor internet connectivity.
 * Call the init method early in the app lifecycle and observe the LiveData where needed
 */
object InternetUtil : LiveData<Boolean>() {

    private var broadcastReceiver: BroadcastReceiver? = null
    private var application: Application? = null

    fun init(application: Application) {
        if (this.application == null) this.application = application
    }

    fun isInternetOn(): Boolean {
        val cm = application?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }

    //Register internet connectivity broadcast receiver when there is an observer on the LiveData
    override fun onActive() {
        registerBroadCastReceiver()
    }

    //Unregister the broadcast receiver when there are no observers on the LiveData
    override fun onInactive() {
        unRegisterBroadCastReceiver()
    }

    private fun registerBroadCastReceiver() {
        if (broadcastReceiver == null) {
            val filter = IntentFilter()
            filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)

            broadcastReceiver = object : BroadcastReceiver() {
                override fun onReceive(_context: Context, intent: Intent) {
                    val extras = intent.extras
                    val info = extras?.getParcelable<NetworkInfo>("networkInfo")
                    value = info?.state == NetworkInfo.State.CONNECTED
                }
            }

            application?.registerReceiver(broadcastReceiver, filter)
        }
    }

    private fun unRegisterBroadCastReceiver() {
        if (broadcastReceiver != null) {
            application?.unregisterReceiver(broadcastReceiver)
            broadcastReceiver = null
        }
    }
}