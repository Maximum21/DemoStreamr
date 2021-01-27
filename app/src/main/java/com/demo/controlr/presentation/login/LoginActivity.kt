package com.demo.controlr.presentation.dialog.login

import android.content.Intent
import android.widget.TextView
import com.demo.controlr.R
import com.demo.controlr.framework.base.BaseActivity
import com.demo.controlr.presentation.home.HomeActivity
import com.demo.controlr.presentation.login.LoginViewModel
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterSession
import org.koin.android.ext.android.inject

class LoginActivity : BaseActivity<LoginViewModel>() {

    override val mViewModel: LoginViewModel by inject()


    private lateinit var loginButton: TextView


    override fun setLayout(): Int {
        return R.layout.activity_login
    }

    override fun initView() {

        loginButton = findViewById<TextView>(R.id.connect_btn)
        loginButton.setOnClickListener {
            startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
        }
    }

    private fun getFollowers(result: Result<TwitterSession?>) {


    }

    override fun loadData() {

    }
}