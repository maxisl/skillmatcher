package com.example.skillmatcher.data

import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

data class ProjectModel(
    var id: UUID,
    var description: String,
    var max_attendees: Int,
    var name: String,
    var startDate: String,
    var endDate: String,
    var owner_id: UUID,
    var image: Bitmap?
)

// use this model to get all available information of a project
@Parcelize
data class Project(
    var id: Long,
    var name: String,
    var description: String,
    var maxAttendees: String,
    val startDate: String,
    val endDate: String,
    val image: String?,
    val requiredSkillsIds: List<Long>?
) : Parcelable

// required model for a project creation request: only if we do not pass an id, it can be generated
// in the backend
@Parcelize
data class ProjectRequest(
    val name: String,
    val description: String,
    val maxAttendees: String,
    val startDate: String,
    val endDate: String,
    var image: String?,
    val requiredSkillsIds: List<Long>?
) : Parcelable