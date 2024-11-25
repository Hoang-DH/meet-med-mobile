package com.example.doctorapp.data.model

import com.google.gson.annotations.SerializedName

data class Department(
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("description")
    var description: String? = null,
    @SerializedName("imageUrl")
    var imageUrl: String? = null
)