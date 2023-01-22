package com.example.skillmatcher.data

data class Skill(
    var id: Long?,
    var name: String,
)

data class SkillModel(
    var name: String,
    var value: Int = 0
)