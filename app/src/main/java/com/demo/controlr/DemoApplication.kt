package com.demo.controlr

import android.app.Application
import android.content.Context
import android.os.StrictMode
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import com.demo.controlr.framework.appModule
import com.demo.controlr.services.PreferenceService
import com.twitter.sdk.android.core.DefaultLogger
import com.twitter.sdk.android.core.Twitter
import com.twitter.sdk.android.core.TwitterAuthConfig
import com.twitter.sdk.android.core.TwitterConfig
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class DemoApplication : Application() {

    companion object {
        private const val NAME_DATABASE = "EASY_GOLF_DATABASE.realm"
        private const val VERSION_DATABASE: Long = 8
    }

    override fun onCreate() {
        super.onCreate()

        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())

        PreferenceService.getInstance().mPreferences =  getSharedPreferences(PreferenceService.PREFS_NAME, Context.MODE_PRIVATE)
        val config = TwitterConfig.Builder(this)
            .logger(DefaultLogger(Log.DEBUG))
            .twitterAuthConfig(TwitterAuthConfig(resources.getString(R.string.twitter_api_key), resources.getString(R.string.twitter_api_secret)))
            .debug(true)
            .build()
        Twitter.initialize(config)
        //        for font style
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        startKoin {
            androidContext(this@DemoApplication)
            modules(appModule)
        }
    }
}