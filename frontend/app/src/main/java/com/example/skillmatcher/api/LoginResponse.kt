package com.example.skillmatcher.api

import com.example.skillmatcher.data.User
import com.google.gson.annotations.SerializedName


data class LoginResponse (
    @SerializedName("status_code")
    var statusCode: Int,

    @SerializedName("auth_token")
    var authToken: String,

    @SerializedName("user")
    var user: User
)