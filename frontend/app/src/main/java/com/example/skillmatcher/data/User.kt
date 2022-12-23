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

// for get all users in REST controller - this declaration fits the model defined in backend(model/ApiUser), above User does not!
@Parcelize
data class ApiUser(
    val id: String,
    val email: String
): Parcelable


data class UserLoginModel(
    var email: String,
    var password: String,
)

/*data class UserModel(
    var email: String,
    var ytw: String,
    var projects: String,
)*/

data class UserModel(
    var jwt: String,
    var token_value: String
)