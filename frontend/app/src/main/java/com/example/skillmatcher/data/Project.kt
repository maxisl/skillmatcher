package com.example.skillmatcher.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

data class ProjectModel(
    var email: String,
    var password: String
)