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

// have to use this model for getAllProjects until backend contains all attributes from ProjectModel
data class Project(
    var name: String,
    var description: String,
    var maxAttendees: String,
)