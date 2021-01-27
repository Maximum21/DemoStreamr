package com.demo.controlr.data.repository

import com.demo.controlr.data.datasource.GeneralDataSource
import com.demo.controlr.data.domain.ErrorResponse
import com.demo.controlr.data.domain.PolicyTerm
import com.demo.controlr.data.domain.TwitterResponse
import com.twitter.sdk.android.core.TwitterSession

class GeneralRepository(private val generalDataSource: GeneralDataSource) {
    suspend fun showFollowers(id: Long, name: String, status:Boolean, type:Boolean, count:Int, result: (TwitterResponse)->Unit, error: (ErrorResponse)->Unit)
            = generalDataSource.showFollowers(id,name,status,type,count,result, error)

    fun saveTwitterSession(twitterResponse: TwitterSession) = generalDataSource.saveTwitterSession(twitterResponse)
    fun getTwitterSession() : TwitterSession? = generalDataSource.getTwitterSession()
}