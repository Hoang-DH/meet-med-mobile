package com.example.doctorapp.data.dto

import com.example.doctorapp.constant.Define
import com.google.gson.annotations.SerializedName

class ApiArrayResponse<T> {
    @SerializedName(Define.Fields.DATA)
    var data: List<T>? = null

    @SerializedName(Define.Fields.STATUS_CODE)
    var statusCode: Int? = null

    @SerializedName(Define.Fields.MESSAGE)
    var message: String? = null

    @SerializedName(Define.Fields.SUCCESS)
    var success: Boolean? = null

}