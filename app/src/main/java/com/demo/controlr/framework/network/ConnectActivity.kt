package com.demo.controlr.framework.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class ConnectActivity(private val context: Context) {

    fun isHaveNetworkConnected():Boolean{
        val connectManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networks = connectManager.allNetworks
        var hasInternet = false
        if(networks.isNotEmpty()){
            for (network in networks){
                val networkCapabilities = connectManager.getNetworkCapabilities(network)
                if(networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true){
                    hasInternet = true
                }
            }
        }
        return hasInternet
    }
}