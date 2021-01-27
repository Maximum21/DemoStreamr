package com.demo.controlr.presentation.connect

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.demo.controlr.data.domain.TwitterResponse
import com.demo.controlr.framework.Interactors
import com.demo.controlr.framework.base.BaseViewModel
import com.google.gson.Gson
import com.twitter.sdk.android.core.TwitterSession
import kotlinx.coroutines.launch

class ConnectViewModel (private val interactors: Interactors) : BaseViewModel() {
    val followersList = MutableLiveData<TwitterResponse>()
    fun getFollowers(id: Long,count:Int) {
        mScope.launch {
            interactors.fetchPolicyTerm.invoke(id, "",true, true, count,{ result->
                Log.e("testingtwitter","success response = ${Gson().toJson(result)}")
                followersList.postValue(result)

            },{ error->
                Log.e("testingtwitter","error: ${error.message}")
                mCommonErrorLive.postValue(error)
            })
        }
    }

    fun saveTwitterSession(twitterSession: TwitterSession) {
        mScope.launch {
            interactors.fetchPolicyTerm.invoke(twitterSession)
        }
    }

}