package com.example.skillmatcher

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.skillmatcher.destinations.FeedbackPageDestination
import com.example.skillmatcher.destinations.LandingPageDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun DocumentSharingPage(
    navigator: DestinationsNavigator
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Post Screen")
        Button(onClick = {
            navigator.navigate(FeedbackPageDestination)
        }) {
            Text("Go to FeedbackPage")
        }
    }
}