package com.example.movietest.util

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class LoadMoreItemsRV(private val visibleThreshold: Int) {
    private var loadMore: LoadMoreCallback? = null
    private var lastVisibleItem = 0
    private var totalItemCount = 0
    private var oldItemTotalCount = 0
    private var linearLayoutManager: LinearLayoutManager? = null
    private var gridLayoutManager: GridLayoutManager? = null
    private var isGridManager = false
    fun setView(recyclerView: RecyclerView) {
        val layoutManager = recyclerView.layoutManager
        if (layoutManager is GridLayoutManager) {
            gridLayoutManager = recyclerView.layoutManager as GridLayoutManager?
            isGridManager = true
        } else if (layoutManager is LinearLayoutManager) {
            linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
            isGridManager = false
        }
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(
                recyclerView: RecyclerView,
                dx: Int,
                dy: Int
            ) {
                super.onScrolled(recyclerView, dx, dy)
                if (linearLayoutManager != null && !isGridManager) {
                    totalItemCount = linearLayoutManager!!.itemCount
                    lastVisibleItem = linearLayoutManager!!.findLastVisibleItemPosition()
                } else if (gridLayoutManager != null && isGridManager) {
                    totalItemCount = gridLayoutManager!!.itemCount
                    lastVisibleItem = gridLayoutManager!!.findLastVisibleItemPosition()
                }
                val isItemAdded = totalItemCount != oldItemTotalCount
                if (isItemAdded && totalItemCount <= lastVisibleItem + visibleThreshold) {
                    oldItemTotalCount = totalItemCount
                    loadMore?.loadMore()
                }
            }
        })
    }

    fun setLoadMore(loadMore: LoadMoreCallback?) {
        this.loadMore = loadMore
    }

}

interface LoadMoreCallback {
    fun loadMore();
}
