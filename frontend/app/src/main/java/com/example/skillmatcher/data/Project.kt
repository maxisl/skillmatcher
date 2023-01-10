package com.example.skillmatcher.data

import android.os.Parcelable
import androidx.compose.runtime.MutableState
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime
import java.util.Date
import java.util.UUID

data class ProjectModel(
    var id: UUID,
    var description: String,
    var max_attendees: Int,
    var name: String,
    var startDate: String,
    var endDate: String,
    var owner_id: UUID
)