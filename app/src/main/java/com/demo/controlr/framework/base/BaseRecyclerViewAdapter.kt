package com.demo.controlr.framework.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demo.controlr.R
import java.util.ArrayList

abstract class BaseRecyclerViewAdapter<T>(protected val context: Context) : RecyclerView.Adapter<BaseRecyclerViewHolder>(){

    companion object{
        private const val TYPE_PROGRESS_LOAD_MORE = 0x0001
        const val TYPE_ITEM = 0x0002
    }
    private var mIsRegisterLoadMore:Boolean = false
    var mDataList = ArrayList<T>()

    private var mEndlessRecyclerViewScrollListener: EndlessRecyclerViewScrollListener? = null
    private var mIsReachEnd:Boolean = false

    protected abstract fun setLayout(viewType: Int): Int

    protected abstract fun setViewHolder(viewRoot: View, viewType: Int): BaseRecyclerViewHolder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecyclerViewHolder {
        return if (viewType == TYPE_PROGRESS_LOAD_MORE) {
            val viewRoot = LayoutInflater.from(context)
                    .inflate(R.layout.bottom_load_more, parent, false)
            LoadMoreViewHolder(viewRoot)
        }else {
            val viewRoot = LayoutInflater.from(context).inflate(setLayout(viewType), parent, false)
            setViewHolder(viewRoot,viewType)
        }
    }

    override fun getItemViewType(position: Int): Int = if (position == getBottomItemPosition() && mIsRegisterLoadMore) {
        TYPE_PROGRESS_LOAD_MORE
    }else customViewType(position)

    open fun customViewType(position: Int):Int = TYPE_ITEM

    override fun getItemCount(): Int = if (mIsRegisterLoadMore) calTotal() + 1 else calTotal()

    open fun calTotal() = mDataList.size

    fun getRealCount(): Int = mDataList.size

    override fun onBindViewHolder(holder: BaseRecyclerViewHolder, position: Int) {
        (holder as? BaseRecyclerViewAdapter<*>.LoadMoreViewHolder)?.mLayoutProgress?.visibility =
                if (mIsReachEnd) View.GONE else View.VISIBLE
    }


    fun registerLoadMore(
            layoutManager: RecyclerView.LayoutManager,
            recyclerView: RecyclerView, loadMoreCallback: ()->Unit) {

        mIsRegisterLoadMore = true
        mIsReachEnd = false

        if (layoutManager is GridLayoutManager) {
            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    when (getItemViewType(position)) {
                        TYPE_PROGRESS_LOAD_MORE -> return layoutManager.spanCount
                        TYPE_ITEM -> return 1
                    }
                    return 0
                }
            }
        }

        this.mEndlessRecyclerViewScrollListener =
                object : EndlessRecyclerViewScrollListener(layoutManager) {
                    override fun onLoadMore() {
                        if(!mIsReachEnd){
                            loadMoreCallback()
                        }
                    }
                }

        recyclerView.addOnScrollListener(this.mEndlessRecyclerViewScrollListener!!)
    }

    fun onReachEnd() {
        mIsReachEnd = true
        notifyItemChanged(getBottomItemPosition())
    }

    fun openReachEnd() {
        mEndlessRecyclerViewScrollListener?.resetState()
        mIsReachEnd = false
        notifyItemChanged(getBottomItemPosition())
    }


    private fun getBottomItemPosition(): Int {
        return itemCount - 1
    }
    open fun addListItem(itemList: List<T>) {
        mDataList.addAll(itemList)
        this.notifyDataSetChanged()
    }

    fun addItem(item: T) {
        mDataList.add(item)
        this.notifyItemInserted(mDataList.size)
    }

    fun addItemAt(item: T,position: Int) {
        mDataList.add(position,item)
        this.notifyItemInserted(position)
    }

    open fun removeItemAt(position: Int) {
        this.notifyItemRemoved(position)
        mDataList.removeAt(position)
    }

    fun getItem(position: Int) = mDataList[position]

    fun clearAll(){
        mDataList.clear()
        notifyDataSetChanged()
    }

    open fun setDataList(itemList: List<T>) {
        val clone = ArrayList(itemList)
        mDataList.clear()
        mDataList.addAll(clone)
        this.notifyDataSetChanged()
    }

    fun getData() = mDataList

    inner class LoadMoreViewHolder(itemView: View) : BaseRecyclerViewHolder(itemView){
        val mLayoutProgress: View = itemView.findViewById(R.id.layoutProgress)
    }
}