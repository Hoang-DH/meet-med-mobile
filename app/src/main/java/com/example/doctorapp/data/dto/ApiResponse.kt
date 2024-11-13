package com.example.doctorapp.data.dto

import com.example.doctorapp.utils.Define
import com.google.gson.annotations.SerializedName

class ApiResponse<T> {
    @SerializedName(Define.Fields.DATA)
    var data: T? = null

    @SerializedName(Define.Fields.STATUS_CODE)
    var statusCode: Define.HttpResponseCode? = null

    @SerializedName(Define.Fields.MESSAGE)
    var message: String? = null
}