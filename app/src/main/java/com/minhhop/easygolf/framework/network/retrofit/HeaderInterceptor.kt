package com.minhhop.easygolf.framework.network.retrofit

import com.demo.controlr.framework.database.PreferenceManager
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor(private val preferenceManager: PreferenceManager): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestTamp = chain.request().newBuilder()
                .addHeader("Content-Type","application/json")
                .addHeader("Authorization","OAuth oauth_consumer_key=\"Z74u4ENQyNMi6FUemgEcd4ASr\",oauth_token=\"1318451829106233344-O70HTahAB9szsb0uiaYz2DOZFwMMip\",oauth_signature_method=\"HMAC-SHA1\",oauth_timestamp=\"1611262215\",oauth_nonce=\"du1raozpdrs\",oauth_version=\"1.0\",oauth_signature=\"LD2uweGpokfSsc9mEnKhChuKLl0%3D\"")
//        preferenceManager.getToken()?.apply {
//            requestTamp.addHeader("Authorization",  "Bearer $this")
//        }
        val request = requestTamp.build()
        return chain.proceed(request)
    }

}