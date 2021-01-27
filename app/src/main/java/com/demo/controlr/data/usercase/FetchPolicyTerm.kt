package com.demo.controlr.data.usercase

import com.demo.controlr.data.domain.ErrorResponse
import com.demo.controlr.data.domain.PolicyTerm
import com.demo.controlr.data.domain.TwitterResponse
import com.demo.controlr.data.repository.GeneralRepository
import com.twitter.sdk.android.core.TwitterSession

class FetchPolicyTerm(private val generalRepository: GeneralRepository) {
    suspend operator fun invoke(id: Long, name: String, status:Boolean, type:Boolean, count:Int, result: (TwitterResponse)->Unit, error: (ErrorResponse)->Unit)
            = generalRepository.showFollowers(id,name,status,type,count,result, error)

    suspend operator fun invoke(twitterResponse: TwitterSession) = generalRepository.saveTwitterSession(twitterResponse)
    suspend operator fun invoke() : TwitterSession? = generalRepository.getTwitterSession()
}