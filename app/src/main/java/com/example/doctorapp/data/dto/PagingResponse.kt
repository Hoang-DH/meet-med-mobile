package com.example.doctorapp.data.dto

import com.google.gson.annotations.SerializedName


class PagingResponse<T> {
    @SerializedName("totalElements")
    var totalElements: Int? = null

    @SerializedName("totalPages")
    var totalPages: Int? = null

    @SerializedName("currentPage")
    var pageSize: Int? = null

    @SerializedName("content")
    var content: List<T>? = null

}