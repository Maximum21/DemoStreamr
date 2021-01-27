package com.demo.controlr.framework.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.demo.controlr.data.domain.ErrorResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext


open class BaseViewModel: ViewModel() {

    private val mParenJob = Job()
    private val mCoroutineContext: CoroutineContext
        get() =  mParenJob + Dispatchers.Main
    val mScope = CoroutineScope(mCoroutineContext)

    val mCommonErrorLive = MutableLiveData<ErrorResponse>()

    fun cancelAllRequest(){
       mCoroutineContext.cancel()
    }
}