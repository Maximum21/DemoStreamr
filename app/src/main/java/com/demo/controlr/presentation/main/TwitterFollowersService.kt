package com.demo.controlr.presentation.dialog.main

import okhttp3.Response
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface TwitterFollowersService {
    @GET("/1.1/followers/list.json")
    fun show(
        @Query("user_id") userId: Long?,
        @Query("screen_name") `var`: String?,
        @Query("skip_status") var1: Boolean?,
        @Query("include_user_entities") var2: Boolean?,
        @Query("count") var3: Int?
    ): Call<Response?>?
}