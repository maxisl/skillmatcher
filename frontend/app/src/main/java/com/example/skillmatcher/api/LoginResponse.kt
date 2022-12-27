package com.example.skillmatcher.api

import com.example.skillmatcher.data.User
import com.google.gson.annotations.SerializedName
import kotlin.String


data class LoginResponse (
    @SerializedName("status_code")
    var statusCode: Int,

    @SerializedName("auth_token")
    var jwt: String,

    @SerializedName("user")
    var user: User
)