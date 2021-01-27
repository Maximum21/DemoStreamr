package com.demo.controlr.presentation.dialog

import android.content.Context
import com.demo.controlr.R
import com.demo.controlr.framework.base.DemoDialog

class CommonDialog(context: Context) : DemoDialog(context) {
    override fun initView() {
    }

    override fun resContentView(): Int = R.layout.dialog_common

    fun setContent(message:String?) = this

    fun addOnListener(commonDialogListener:CommonDialogListener?) = this

    interface CommonDialogListener{

    }
}