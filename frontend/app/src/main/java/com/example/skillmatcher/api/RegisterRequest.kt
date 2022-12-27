package com.example.skillmatcher.api

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("email")
    var email: String,

    @SerializedName("password")
    var password: String
)