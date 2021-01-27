package com.demo.controlr.framework.base

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

/*
 * Created by woz on 4/5/18.
 */ abstract class EndlessRecyclerViewScrollListener protected constructor(private val mLayoutManager: RecyclerView.LayoutManager) : RecyclerView.OnScrollListener() {
    private var mLoading = false
    private var mVisibleThreshold = 2
    private var mPreviousTotalItemCount = 0
    private fun getLastVisibleItem(lastVisibleItemPositions: IntArray): Int {
        var maxSize = 0
        val length = lastVisibleItemPositions.size
        if (length > 0) {
            maxSize = lastVisibleItemPositions[0]
        }
        for (i in 1 until length) {
            if (maxSize < lastVisibleItemPositions[i]) {
                maxSize = lastVisibleItemPositions[i]
            }
        }
        return maxSize
    }

    //    Callback method to be invoked when the RecyclerView has been scrolled.
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        var lastVisibleItemPosition = 0

//        total item in recycler view
        val totalItemCount = mLayoutManager.itemCount
        when (mLayoutManager) {
            is StaggeredGridLayoutManager -> {
                val lastVisibleItemPositions = mLayoutManager.findLastVisibleItemPositions(null)
                lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions)
            }
            is GridLayoutManager -> {
                lastVisibleItemPosition = mLayoutManager.findLastVisibleItemPosition()
            }
            is LinearLayoutManager -> {
                lastVisibleItemPosition = mLayoutManager.findLastVisibleItemPosition()
            }


//        if(mPreviousTotalItemCount == 0){
//            mPreviousTotalItemCount = totalItemCount;
//        }


//         If it’s still loading, we check to see if the data count has
//         changed, if so we conclude(make up one's mind) it has finished loading and update the current page
//         number and total item count.
        }


//        if(mPreviousTotalItemCount == 0){
//            mPreviousTotalItemCount = totalItemCount;
//        }
        if (totalItemCount < mPreviousTotalItemCount) {
            mPreviousTotalItemCount = totalItemCount
            if (totalItemCount == 0) {
                mLoading = true
            }
        }


//         If it’s still loading, we check to see if the data count has
//         changed, if so we conclude(make up one's mind) it has finished loading and update the current page
//         number and total item count.
        if (mLoading && totalItemCount > mPreviousTotalItemCount) {
            mLoading = false
            mPreviousTotalItemCount = totalItemCount
        }
        if (!mLoading && lastVisibleItemPosition + 1 + mVisibleThreshold >= totalItemCount) {
            mLoading = true
            onLoadMore()
        }
    }

    abstract fun onLoadMore()
    fun resetState() {
        mPreviousTotalItemCount = 0
        mLoading = true
    }

    init {
        if (mLayoutManager is StaggeredGridLayoutManager) {
            mVisibleThreshold *= mLayoutManager.spanCount
        } else if (mLayoutManager is GridLayoutManager) {
            mVisibleThreshold *= mLayoutManager.spanCount
        }
    }
}