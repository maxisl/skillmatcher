package com.example.skillmatcher.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class User(
    val name: String,
    val id: String,
    val created: LocalDateTime
): Parcelable


data class UserLoginModel(
    var email: String,
    var password: String,
)

data class UserModel(
    var email: String,
    var ytw: String,
    var projects: String,
)