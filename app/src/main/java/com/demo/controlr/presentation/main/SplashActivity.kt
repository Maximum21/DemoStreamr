package com.demo.controlr.presentation.dialog.main

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Path
import android.util.Base64
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.Group
import com.demo.controlr.framework.base.BaseActivity
import com.twitter.sdk.android.core.*
import com.demo.controlr.R
import com.demo.controlr.presentation.dialog.login.LoginActivity
import com.demo.controlr.presentation.moreoptions.MoreOptionsActivity
import org.koin.android.ext.android.inject
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*


class SplashActivity : BaseActivity<SplashViewModel>() {

    override val mViewModel: SplashViewModel by inject()


    private lateinit var loginButton: TextView
    private lateinit var joinNow: TextView
    private lateinit var groupSplash: Group
    private lateinit var img1: ImageView
    private lateinit var img2: ImageView
    private lateinit var img3: ImageView


    override fun setLayout(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        img1 = findViewById<ImageView>(R.id.img1)
        img2 = findViewById<ImageView>(R.id.img2)
        img3 = findViewById<ImageView>(R.id.img3)
        groupSplash = findViewById<Group>(R.id.group_splash)

        Timer().schedule(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    startPath(0,img2)
                    startPath(1,img1)
                    startPath(2,img3)
                }
            }
        }, 1000)

        Timer().schedule(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    groupSplash.visibility = View.VISIBLE
                }
            }
        }, 3500)

        joinNow = findViewById<TextView>(R.id.join_tv)
        loginButton = findViewById<TextView>(R.id.login_tv)
        loginButton.setOnClickListener {
            startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
        }
        joinNow.setOnClickListener {
            startActivity(Intent(this@SplashActivity, MoreOptionsActivity::class.java))
        }
    }

    private fun getFollowers(result: Result<TwitterSession?>) {


    }

    override fun loadData() {

    }
    fun startPath(tag:Int,img1: View){
        val path1 = Path()
        when(tag){
            0->{
                path1.moveTo( -dipToPixels(this,194f), -dipToPixels(this,150f))
                path1.lineTo( dipToPixels(this,100f), dipToPixels(this,594f))
                path1.lineTo( -dipToPixels(this,100f), -dipToPixels(this,82f))
            }
            1->{
                path1.moveTo( -dipToPixels(this,61f), dipToPixels(this,182f))
                path1.lineTo( dipToPixels(this,137f), -dipToPixels(this,70f))
                path1.lineTo( dipToPixels(this,344f), dipToPixels(this,238f))
            }
            2->{
                path1.moveTo( dipToPixels(this,182f), dipToPixels(this,472f))
                path1.lineTo( dipToPixels(this,277f), dipToPixels(this,115f))
                path1.lineTo( -dipToPixels(this,204f), dipToPixels(this,548f))
            }
        }
        startAnimation(img1,path1)
    }
    fun startAnimation(img1: View,path1: Path){
        val pathANimator : ValueAnimator = ObjectAnimator.ofFloat(img1,"translationX","translationY",path1)
        pathANimator.duration = 3000
        pathANimator.start()
    }
    fun dipToPixels(context: Context, dipValue: Float): Float {
        val metrics: DisplayMetrics = context.resources.displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics)
    }


    fun printHashKey(pContext: Context) {
        try {
            val info = pContext.packageManager
                .getPackageInfo(pContext.packageName, PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                var hash_key = String(Base64.encode(md.digest(), 0))
                Log.i("KeyHas", "printHashKey() Hash Key: " + hash_key);
            }
        } catch (e: NoSuchAlgorithmException) {
            Log.e("KeyHas", "printHashKey()$e")
        } catch (e: Exception) {
            Log.e("KeyHas", "printHashKey()$e")
        }
    }
}