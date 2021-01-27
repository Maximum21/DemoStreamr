package com.demo.controlr.presentation.moreoptions

import android.content.Intent
import android.widget.ImageView
import com.demo.controlr.R
import com.demo.controlr.connect.ConnectActivity
import com.demo.controlr.framework.base.BaseActivity
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterSession
import org.koin.android.ext.android.inject

class MoreOptionsActivity : BaseActivity<MoreOptionsViewModel>() {

    private lateinit var img_connect: ImageView
    override val mViewModel: MoreOptionsViewModel by inject()




    override fun setLayout(): Int {
        return R.layout.activity_more_options
    }

    override fun initView() {
        img_connect = findViewById<ImageView>(R.id.img_connect)
        img_connect.setOnClickListener {
            startActivity(Intent(this@MoreOptionsActivity, ConnectActivity::class.java))
        }
    }

    private fun getFollowers(result: Result<TwitterSession?>) {


    }

    override fun loadData() {

    }
}