package com.example.skillmatcher.components

import androidx.compose.ui.graphics.painter.Painter

data class MenuItem(
    val id: ScreensRoute,

    val image: Painter,
    val label: String,
    val showUnreadBubble: Boolean = false,
)