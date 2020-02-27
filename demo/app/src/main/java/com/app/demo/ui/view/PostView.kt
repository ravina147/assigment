package com.app.demo.ui.view

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.app.demo.api.model.Post
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.custom_post_cell.view.*
import android.widget.CheckBox
import com.app.demo.R


/**
 * Created by Narola on 26/10/18.
 */
class PostView(context: Context) : FrameLayout(context) {

    private val itemClickSubject: PublishSubject<Int> = PublishSubject.create()
    var itemClick: Observable<Int> = itemClickSubject.hide()
    private var lastCheckedPos = 0
    private var lastChecked: CheckBox? = null

    private val compositeDisposable = CompositeDisposable()

    init {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        View.inflate(context, R.layout.custom_post_cell, this)
    }

    fun bind(post: Post, position: Int) {
        textCreatedAt.text = post.created_at
        textTitle.text = post.title

        checkSelect.isChecked = post.isCheck


        checkSelect.setOnClickListener {
            itemClickSubject.onNext(position)
        }

    }


}