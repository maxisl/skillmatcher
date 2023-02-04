package com.example.skillmatcher.data

import android.os.Parcelable
import android.text.BoringLayout
import kotlinx.parcelize.Parcelize

@Parcelize
data class Skill (
    var id: Long,
    var name: String,
    var value: Int = 0 ,
    var isSelected: Boolean = false
    ) : Parcelable


data class SkillModel(
    var name: String,
    var value: Int = 0
)