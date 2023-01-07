package com.example.skillmatcher.data

import android.os.Parcelable
import androidx.compose.runtime.MutableState
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime
import java.util.Date

data class ProjectModel(
    var name: String,
    var description: String,
    var startDate: String,
    var endDate: String,
    var attendees: Int
)