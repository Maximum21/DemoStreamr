package com.demo.controlr.presentation.home

import android.animation.ObjectAnimator
import android.content.Intent
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.demo.controlr.R
import com.demo.controlr.framework.base.BaseActivity
import com.demo.controlr.presentation.exchnage.ExchangeActivity
import com.demo.controlr.presentation.menu.MenuActivity
import com.google.gson.Gson
import com.streamr.client.StreamrClient
import com.streamr.client.authentication.AuthenticationMethod
import com.streamr.client.authentication.EthereumAuthenticationMethod
import com.streamr.client.rest.Stream
import io.reactivex.ObservableOnSubscribe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import java.util.*


class HomeActivity : BaseActivity<HomeViewModel>() {

    private lateinit var exchnage: ImageView
    private lateinit var options_iv: ImageView
    override val mViewModel: HomeViewModel by inject()


    override fun setLayout(): Int {
        return R.layout.activity_home
    }

    override fun initView() {
//        mViewModel.startRequest()

        CoroutineScope(Dispatchers.IO).launch {
//            val method: AuthenticationMethod = EthereumAuthenticationMethod("52441225ded8527aa5f486c59aebc3c54b91059b0920b83b4a7f350c67620fa5")

            val client = StreamrClient(EthereumAuthenticationMethod("private_key"))
            val stream: Stream =
                client.getStream("0x2dc2b5ad437c93f8008a9fa2a827079f0319010c/twitterIds")

            val msg: MutableMap<String, Any> = LinkedHashMap()
            msg["foo"] = "bar"
            msg["random"] = Math.random()

            client.publish(stream, msg)
        }
        options_iv = findViewById<View>(R.id.options_iv) as ImageView
        options_iv.setOnClickListener {
            startActivity(Intent(this,MenuActivity::class.java))
        }
        exchnage = findViewById<View>(R.id.exchnageIconIv) as ImageView
        exchnage.setOnClickListener {
            startActivity(Intent(this,ExchangeActivity::class.java))
        }

    }
    override fun loadData() {

    }
}