package com.demo.controlr.framework.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import com.demo.controlr.R

abstract class DemoDialog(context: Context) : Dialog(context),View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(resContentView())

        window?.let { window->
            window.setBackgroundDrawableResource(android.R.color.transparent)
            window.setWindowAnimations(R.style.animDialog)
            window.setGravity(gravityWindow())
            val lp = WindowManager.LayoutParams()
            lp.gravity = Gravity.BOTTOM
            lp.flags = lp.flags and WindowManager.LayoutParams.FLAG_DIM_BEHIND.inv()
            lp.copyFrom(window.attributes)
            lp.width = WindowManager.LayoutParams.MATCH_PARENT
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT
            window.attributes = lp
        }

        setCanceledOnTouchOutside(cancelTouchOutside())
        findViewById<View>(R.id.btClose)?.setOnClickListener(this)

        initView()
    }

    protected abstract fun initView()
    protected abstract fun resContentView(): Int

    open fun gravityWindow():Int = Gravity.CENTER

    open fun cancelTouchOutside(): Boolean {
        return false
    }


    override fun onClick(v: View) {
        if (v.id == R.id.btClose) {
            dismiss()
        }
    }
}