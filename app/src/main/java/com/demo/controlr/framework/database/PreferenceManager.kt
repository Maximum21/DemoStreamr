package com.demo.controlr.framework.database

import android.content.Context
import com.demo.controlr.utils.Contains
import com.google.gson.Gson
import com.twitter.sdk.android.core.TwitterSession

class PreferenceManager(context: Context) {

    companion object{
        private const val PREFS_NAME = "prefs_easy_golf"
    }

    private val mPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)


    fun getToken(): String? {
        return mPreferences.getString(Contains.VALUE_AUTHORIZATION,null)
    }

    fun setToken(token: String): Boolean {
        return mPreferences.edit()?.putString(Contains.VALUE_AUTHORIZATION,token)?.commit() ?: false
    }

    fun removeToken(): Boolean {
        return mPreferences.edit()?.clear()?.commit() ?: return false
    }

    fun saveTwitterSession(twitterResponse: TwitterSession) {
        mPreferences.edit()?.putString(Contains.VALUE_TWITTER_SESSION, Gson().toJson(twitterResponse))?.apply()
    }

    fun getTwitterSession(): TwitterSession? {
        val data = mPreferences.getString(Contains.VALUE_TWITTER_SESSION,null)
        data?.let{
            return Gson().fromJson(data,TwitterSession::class.java)
        }
        return null
    }
}