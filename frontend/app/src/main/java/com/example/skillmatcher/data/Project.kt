package com.example.skillmatcher.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

data class ProjectModel(
    var email: String,
    var password: String
)

data class Project(
    var name: String,
    var description: String,
    var maxAttendees: Int,
)