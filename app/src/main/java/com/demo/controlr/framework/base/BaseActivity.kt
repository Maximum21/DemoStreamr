package com.demo.controlr.framework.base

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.demo.controlr.R
import com.demo.controlr.data.domain.ErrorResponse
import com.google.android.material.dialog.MaterialAlertDialogBuilder

abstract class BaseActivity<out T: BaseViewModel>: AppCompatActivity() {
    enum class StatusLoading{
        FIRST_LOAD,
        FINISH_LOAD,
        REFRESH_LOAD
    }

    var mStatusLoading = StatusLoading.FIRST_LOAD
    private var mRefreshLayout: SwipeRefreshLayout? = null

    abstract val mViewModel : T

    private var mViewMask:View? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(setLayout())

        setupViewMask()
        setupRefreshLayout()
        mViewModel.mCommonErrorLive.observe(this, Observer {
            errorResponseMessage(it)
            commonError()
        })
        initView()
        loadData()
    }



    private fun setupViewMask(){
        mViewMask = findViewById(R.id.viewMask)
        mViewMask?.setOnClickListener {  }
    }

    private fun setupRefreshLayout(){
        mRefreshLayout = findViewById(R.id.refreshLayout)

        mRefreshLayout?.setColorSchemeColors(
                ContextCompat.getColor(this,R.color.colorPrimary),
                ContextCompat.getColor(this,R.color.colorPullToRefresh),
                ContextCompat.getColor(this,R.color.colorPullToRefreshMore))

        mRefreshLayout?.setOnRefreshListener {
            onRefreshData()
        }
    }

    open fun onRefreshData(){
        mStatusLoading = StatusLoading.REFRESH_LOAD
    }

    open fun hideRefreshLoading(){
        mRefreshLayout?.isRefreshing = false
    }

    fun showCommonMessage(message:String, event: DialogInterface.OnClickListener? = null){
        MaterialAlertDialogBuilder(this)
                .setTitle(getString(R.string.title_no_network))
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(getString(android.R.string.ok),event)
                .show()
    }

    private fun showNoNetworkFoundDialog(){
        MaterialAlertDialogBuilder(this)
                .setTitle(getString(R.string.title_no_network))
                .setMessage(getString(R.string.content_no_network))
                .setCancelable(false)
                .setPositiveButton(getString(android.R.string.ok),null)
                .show()
    }

    private fun errorResponseMessage(errorResponse: ErrorResponse){
        if(errorResponse.noNetwork){
            showNoNetworkFoundDialog()
        }else{
            showCommonMessage(errorResponse.message)
        }
    }


    open fun viewMask(){
        mViewMask?.visibility = View.VISIBLE
    }

    open fun hideMask(){
        mViewMask?.visibility = View.GONE
    }

    fun toast(message: String){
        Toast.makeText(this,message, Toast.LENGTH_LONG).show()
    }


    fun hideSoftKeyboard() {
        currentFocus?.let { viewFocus->
            (getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.hideSoftInputFromWindow(viewFocus.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    open fun commonError(){
        hideMask()
    }

    abstract fun setLayout():Int

    abstract fun initView()

    abstract fun loadData()



    override fun onDestroy() {
        mViewModel.cancelAllRequest()
        super.onDestroy()
    }


    protected fun isHideSoftKeyBoardTouchOutSide(): Boolean {
        return true
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (isHideSoftKeyBoardTouchOutSide()) {
            val viewFocus = window.currentFocus
            (viewFocus as? EditText)?.let { focus->

                val screenLocation = IntArray(2)
                viewFocus.getLocationOnScreen(screenLocation)
                val x = event.rawX + focus.left - screenLocation[0]
                val y = event.rawY + focus.top - screenLocation[1]

                if (event.action == MotionEvent.ACTION_DOWN) {
                    if (x < focus.left || x >= focus.right
                            || y < focus.top || y >= focus.bottom
                    ) {
                        hideSoftKeyboard()
                        viewFocus.clearFocus()
                    }
                }
            }
        }

        return super.dispatchTouchEvent(event)
    }
}