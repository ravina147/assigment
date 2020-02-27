package com.app.demo.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.demo.api.service.ApiService
import com.app.demo.api.service.AppService
import com.app.demo.base.network.NetworkObserver

import io.reactivex.disposables.CompositeDisposable

open class BaseActivity : AppCompatActivity() {

    lateinit var networkObserver: NetworkObserver

    lateinit var appService: ApiService


    protected val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        networkObserver = NetworkObserver(application)
        appService = AppService.createService(this)
    }


    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}