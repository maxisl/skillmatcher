package com.example.skillmatcher

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.example.skillmatcher.destinations.ProjectCreationPageDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun IndividualSkillsPage(
    id: Int, // <-- required navigation argument
    user: User,
    navigator: DestinationsNavigator
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("IndividualSkills: $user", textAlign = TextAlign.Center)
        Button(onClick = {
             navigator.navigate(ProjectCreationPageDestination())
        }) {
            Text("Go to ProjectCreationPage")
        }
    }
}