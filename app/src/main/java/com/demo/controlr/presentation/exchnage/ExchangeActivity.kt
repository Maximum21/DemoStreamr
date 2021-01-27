package com.demo.controlr.presentation.exchnage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.demo.controlr.R
import com.demo.controlr.framework.base.BaseActivity
import com.demo.controlr.presentation.menu.MenuViewModel
import org.koin.android.ext.android.inject

class ExchangeActivity : BaseActivity<ExchangeViewModel>() {

    override val mViewModel: ExchangeViewModel by inject()


    override fun setLayout(): Int {
        return R.layout.activity_exchnage
    }

    override fun initView() {

    }
    override fun loadData() {

    }
}