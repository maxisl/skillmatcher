package com.example.skillmatcher.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Skill (
    var name: String,
    var value: Int = 0        ) : Parcelable