package com.example.skillmatcher.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    //val name: String,
    val id: Long,
    val email: String,
   // val description: String,
    val skills: MutableList<Skill?>,
    var projects: MutableList<Project>,
    val image: String?
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

data class UserRegisterModel(
    var email: String,
    var password: String,
    var image: String?
)