package com.demo.controlr.framework.network.retrofit

import android.util.Log
import com.demo.controlr.data.domain.ErrorResponse
import com.demo.controlr.framework.network.ConnectActivity
import com.google.gson.Gson
import org.json.JSONObject
import retrofit2.*
import java.io.IOException
import java.lang.Exception
import java.net.SocketTimeoutException

class ResponseRequest<R>(private val call: Call<R>,private val connectActivity: ConnectActivity) {

    fun run(onSuccess:(R)->Unit,onError: (ErrorResponse)->Unit){
        if(connectActivity.isHaveNetworkConnected()) {
            try {
                call.enqueue(object : Callback<R> {
                    override fun onFailure(call: Call<R>, t: Throwable) {
                        t.printStackTrace()
                        onError(ErrorResponse.commonError(t.localizedMessage))
                    }

                    override fun onResponse(call: Call<R>, response: Response<R>) {
                        handleResponse(response, onSuccess, onError)
                    }
                })

            } catch (t: IOException) {
                t.printStackTrace()
                return if (t is SocketTimeoutException) {
                    onError(ErrorResponse.requestTimeout())
                } else {
                    onError(ErrorResponse.createExceptionInternal(t.localizedMessage))
                }
            }
        }else{
            onError(ErrorResponse.noHaveNetwork())
        }
    }

    private fun handleResponse(response: Response<R>,onSuccess:(R)->Unit,onError: (ErrorResponse)->Unit){
        Log.e("testingtwitter","===3333== ${response.raw().request.url.toString()}")
        if(response.isSuccessful){
            onSuccess(response.body()!!)
        }else{
            if(response.code() in 400..511){
                if(response.errorBody() != null){
                    val gson = Gson()
                    val errorResponse:ErrorResponse =
                            try {
                                response.errorBody()?.string()?.let { jsonError->
                                    val json = JSONObject(jsonError)
                                    if(json.has(ErrorResponse::message.name)){
                                        val errorResult = gson.fromJson(jsonError,
                                                ErrorResponse::class.java)
                                        errorResult.status = response.code()
                                        errorResult
                                    }else{
                                        val message = json.get("error") as String
                                        ErrorResponse.commonError(message)
                                    }
                                }?:  ErrorResponse.commonError()


                            }catch (e:Exception){
                                ErrorResponse.createExceptionInternal()
                            }

                    onError(errorResponse)

                }else{
                    onError(ErrorResponse.createExceptionInternal(response.message()))
                }
            }else{
                onSuccess(response.body()!!)
            }
        }
    }
}