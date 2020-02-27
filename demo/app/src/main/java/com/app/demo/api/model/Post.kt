package com.app.demo.api.model

import com.google.gson.annotations.SerializedName

data class Hits(
    @SerializedName("hits")
    var arrayPost:MutableList<Post>
)

data class Post(
    @SerializedName("created_at")
    var created_at:String="" ,
    @SerializedName("title")
    var title:String,

    var isCheck:Boolean=false
)