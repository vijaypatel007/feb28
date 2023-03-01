package com.example.feb28.dataClass

import com.google.gson.annotations.SerializedName

data class ApiDemo(
    @SerializedName("page") var page: String? = null,
    @SerializedName("per_page") var per_page: String? = null,
    @SerializedName("total") var total: String? = null,
    @SerializedName("total_pages") var total_pages: String? = null,
    @SerializedName("data") var dataList: ArrayList<Data>? = null
)