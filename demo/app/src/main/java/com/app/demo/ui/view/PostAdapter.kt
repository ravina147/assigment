package com.app.demo.ui.view

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.app.demo.api.model.Post
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * Created by Narola on 26/10/18.
 */

class PostAdapter(private val adapterItems: MutableList<Post>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val itemClickSubject: PublishSubject<Int> = PublishSubject.create()
    var itemClick: Observable<Int> = itemClickSubject.hide()



    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PostViewHolder(
            PostView(
                viewGroup.context
            ).apply {
                itemClick.subscribe { country ->
                    itemClickSubject.onNext(country)
                }
            })
    }

    override fun getItemCount(): Int {
        return adapterItems.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        (viewHolder.itemView as PostView).bind(adapterItems[position],position)
    }

    private class PostViewHolder(view: View) : RecyclerView.ViewHolder(view)


}