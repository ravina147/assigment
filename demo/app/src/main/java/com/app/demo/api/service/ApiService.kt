package com.app.demo.api.service

import com.app.demo.api.model.Hits
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("search_by_date?tags=story")
    fun getPost(@Query("page") page:Int): Single<Hits>
}