package com.demo.controlr.connect

import android.content.Intent
import android.util.Log
import com.demo.controlr.R
import com.demo.controlr.framework.base.BaseActivity
import com.demo.controlr.presentation.connect.ConnectViewModel
import com.demo.controlr.presentation.home.HomeActivity
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.TwitterSession
import com.twitter.sdk.android.core.identity.TwitterAuthClient
import com.twitter.sdk.android.core.identity.TwitterLoginButton
import org.koin.android.ext.android.inject

class ConnectActivity : BaseActivity<ConnectViewModel>() {
    private lateinit var twitterAuthClient: TwitterAuthClient
    private lateinit var twitter_login: TwitterLoginButton

    override val mViewModel: ConnectViewModel by inject()


    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        // Pass the activity result to the login button.
        twitter_login.onActivityResult(requestCode, resultCode, data)
    }


    override fun setLayout(): Int {
        return R.layout.activity_connect
    }

    override fun initView() {
        twitter_login = findViewById<TwitterLoginButton>(R.id.twitter_login)
        twitter_login.callback = object : Callback<TwitterSession?>() {
            override fun success(result: Result<TwitterSession?>?) {
                result?.data?.let{
                    mViewModel.saveTwitterSession(it)
                    startActivity(Intent(this@ConnectActivity, HomeActivity::class.java))
//                    Log.e("testingtwitter","success = ${Gson().toJson(result)}")
//                    mViewModel.getFollowers(it.userId,100)
                }
            }

            override fun failure(exception: TwitterException?) {
                Log.e("testingtwitter","success = ${exception.toString()}")
            }
        }
    }

    override fun loadData() {

    }
}