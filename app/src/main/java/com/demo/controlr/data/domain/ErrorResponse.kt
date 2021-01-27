package com.demo.controlr.data.domain

import android.util.Log
import java.io.Serializable

class ErrorResponse(
       var status:Int = -1,
       var message:String,
       var noNetwork:Boolean = false
) : Serializable{
    companion object{
        fun createExceptionInternal(message: String? = null): ErrorResponse {
            Log.e("testingtwitter","$message")
            return ErrorResponse(500, message
                    ?: "Server is under maintenance, please try again few minute later")
        }

        fun noHaveNetwork(): ErrorResponse {
            return ErrorResponse(-1, "loses internet connection",true)
        }

        fun commonError(message: String? = null): ErrorResponse {
            return ErrorResponse(-1, message?:"Unknown")
        }

        fun requestTimeout(): ErrorResponse = ErrorResponse(-1, "Looks Like the server is taking to long respond, this can be caused by either poor connectivity or an error with our servers. Please try again in a while")
    }
}