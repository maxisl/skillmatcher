package com.example.skillmatcher.data

import android.graphics.Bitmap
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

// use this model for getAllProjects
data class Project(
    var name: String,
    var description: String,
    var maxAttendees: String,
    val startDate: String,
    val endDate: String,
    val image: Bitmap?,
    val requiredSkillsIds: List<Long>?
)