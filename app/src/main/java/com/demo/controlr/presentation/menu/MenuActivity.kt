package com.demo.controlr.presentation.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.demo.controlr.R
import com.demo.controlr.framework.base.BaseActivity
import com.demo.controlr.presentation.home.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MenuActivity  : BaseActivity<MenuViewModel>() {

    override val mViewModel: MenuViewModel by inject()


    override fun setLayout(): Int {
        return R.layout.activity_menu
    }

    override fun initView() {

    }
    override fun loadData() {

    }
}