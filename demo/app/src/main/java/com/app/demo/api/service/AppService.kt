package com.app.demo.api.service

import android.content.Context
import com.app.demo.BuildConfig
import com.app.demo.api.service.ApiService
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.*
import java.util.concurrent.TimeUnit

object AppService {

    fun createService(context: Context): ApiService {
        return setupRetrofit(context).create(ApiService::class.java)
    }

    fun setupOkHttp(context: Context): OkHttpClient {
        val cacheSize = 10 * 1024 * 1024 // 10 MiB
        val cacheDir = File(context.cacheDir, "HttpCache")
        val cache = Cache(cacheDir, cacheSize.toLong())
        //TODO Replace sample_certificate.pem with your server public certificate in raw resource and uncomment .setupNetworkSecurity(context)
        val builder = OkHttpClient.Builder()
                .readTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS)
                .cache(cache)
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(loggingInterceptor)
        }
        return builder.build()
    }

    private fun setupRetrofit(context: Context): Retrofit {
        return Retrofit.Builder()
                .baseUrl(" https://hn.algolia.com/api/v1/")
                .client(setupOkHttp(context))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync()) // Using create async means all api calls are automatically created asynchronously using OkHttp's thread pool
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().registerTypeAdapter(Date::class.java, JsonDeserializer { json, _, _ -> Date(json.asJsonPrimitive.asLong) })
                        .create()))
                .build()
    }
}