package com.example.skillmatcher.data

data class InputCheck(
    val error: Boolean,
    val notifications: MutableList<String> = mutableListOf<String>()
)
