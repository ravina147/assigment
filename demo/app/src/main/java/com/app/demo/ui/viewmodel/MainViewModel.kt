package com.app.demo.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.app.demo.api.service.ApiService
import com.app.demo.api.model.Hits
import com.app.demo.base.BaseViewModel
import com.app.demo.base.rxjava.autoDispose

import io.reactivex.android.schedulers.AndroidSchedulers

class MainViewModel(private val apiService: ApiService) : BaseViewModel() {
    val hits: MutableLiveData<Hits> = MutableLiveData()

    /*
    * For get all post list
    * @Param:offset(for load more)
    * */
    fun getPost(offset:Int) {
        apiService.getPost(offset)
                .doOnSubscribe {
                    isLoading.postValue(true)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ it->
                    hits.value = it
                }, { error ->
                    isError.value = error
                }).autoDispose(compositeDisposable)
    }
}