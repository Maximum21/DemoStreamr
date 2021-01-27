package com.demo.controlr.presentation.home

import android.util.Log
import com.demo.controlr.framework.Interactors
import com.demo.controlr.framework.base.BaseViewModel
import com.google.gson.Gson
import com.streamr.client.StreamrClient
import com.streamr.client.authentication.AuthenticationMethod
import com.streamr.client.authentication.EthereumAuthenticationMethod
import com.streamr.client.rest.Stream
import kotlinx.coroutines.launch

class HomeViewModel (private val interactors: Interactors) : BaseViewModel() {
    fun startRequest() {
        mScope.launch {
            getStream()
        }
    }

    suspend fun getStream(){
        val method: AuthenticationMethod = EthereumAuthenticationMethod("52441225ded8527aa5f486c59aebc3c54b91059b0920b83b4a7f350c67620fa5")
//            Log.e("testingmosz","===${Gson().toJson(method)}")
//            val client = StreamrClient(method)
//            val stream: Stream =
//                client.getStream("0x2dc2b5ad437c93f8008a9fa2a827079f0319010c/twitterIds")
//
//            val msg: MutableMap<String, Any> = LinkedHashMap()
//            msg["foo"] = "bar"
//            msg["random"] = Math.random()
//
//            client.publish(stream, msg)
    }

}