package com.demo.controlr.data.datasource

import com.demo.controlr.data.domain.ErrorResponse
import com.demo.controlr.data.domain.PolicyTerm
import com.demo.controlr.data.domain.TwitterResponse
import com.twitter.sdk.android.core.Twitter
import com.twitter.sdk.android.core.TwitterSession


interface GeneralDataSource {
    suspend fun showFollowers(id: Long, name: String, status:Boolean, type:Boolean, count:Int, result: (TwitterResponse)->Unit, error: (ErrorResponse)->Unit)
    fun saveTwitterSession(twitterResponse: TwitterSession)
    fun getTwitterSession() : TwitterSession?
}