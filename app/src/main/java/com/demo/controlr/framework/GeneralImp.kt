package com.demo.controlr.framework

import android.util.Log
import com.demo.controlr.data.datasource.GeneralDataSource
import com.demo.controlr.data.domain.ErrorResponse
import com.demo.controlr.data.domain.PolicyTerm
import com.demo.controlr.data.domain.TwitterResponse
import com.demo.controlr.framework.database.PreferenceManager
import com.twitter.sdk.android.core.TwitterSession

class GeneralImpl(private val preferenceManager: PreferenceManager,private val generalService: GeneralService) : GeneralDataSource {
    override suspend fun showFollowers(id: Long, name: String,status:Boolean, type:Boolean, count:Int,  result: (TwitterResponse) -> Unit, error: (ErrorResponse) -> Unit) {
        Log.e("testingtwitter","$id $name")
        generalService.show(id.toString()).run({
            result(it)
        },{
            error(it)
        })
    }

    override fun saveTwitterSession(twitterResponse: TwitterSession) {
        preferenceManager.saveTwitterSession(twitterResponse)
    }

    override fun getTwitterSession(): TwitterSession? {
        return preferenceManager.getTwitterSession()
    }


}