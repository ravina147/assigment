package com.app.demo.ui.view

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.app.demo.R
import com.app.demo.api.model.Post
import com.app.demo.base.BaseActivity
import com.app.demo.base.extentions.getViewModel
import com.app.demo.ui.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import com.app.demo.base.rxjava.autoDispose
import com.app.demo.helper.PaginationScrollListener


class MainActivity : BaseActivity(), SwipeRefreshLayout.OnRefreshListener {
    private var isLastPage: Boolean = false
    private var isLoading: Boolean = false
    private lateinit var viewModel: MainViewModel
    private var arrayPost = mutableListOf<Post>()
    private var offset = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
    }

    override fun onRefresh() {
        refresh.isRefreshing = true
        offset=0
        viewModel.getPost(0)

        refresh.isRefreshing = false
    }

    private fun initView() {
        viewModel = getViewModel {
            MainViewModel(
                appService
            )
        }
        viewModel.getPost(0)

        refresh.setOnRefreshListener(this)

        var layoutManager = LinearLayoutManager(this)
        val postAdapter: PostAdapter =
            PostAdapter(arrayPost)
        recyclePostList.layoutManager = layoutManager
        recyclePostList.adapter = postAdapter


        /*
        * Get post list from API
        * */
        viewModel.hits.observe(this, Observer { hits ->
            if (offset == 0)
                arrayPost.clear()
            arrayPost.addAll(hits.arrayPost)
            postAdapter.notifyDataSetChanged()
            textPostCount.text = "Post Count:".plus(arrayPost.size.toString())

            isLoading=false
        })

        /*
        * Manage item click for select and deselect post
        * */

        postAdapter.itemClick.subscribe { it ->
            for (i in arrayPost) {
                i.isCheck = false
            }
            arrayPost[it].isCheck = true
            postAdapter.notifyDataSetChanged()
        }.autoDispose(compositeDisposable)


        /*
        *
        * Add scroll listener for manage load more
        * */
        recyclePostList?.addOnScrollListener(object : PaginationScrollListener(layoutManager) {
            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun loadMoreItems() {
                isLoading = true
                offset++
                viewModel.getPost(offset)
            }
        })

    }
}
