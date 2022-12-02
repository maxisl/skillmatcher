package com.example.skillmatcher

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.skillmatcher.destinations.ProjectCreationPageDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
@Preview
fun IndividualSkillsPage(
    id: Int, // <-- required navigation argument
    user: User,
    navigator: DestinationsNavigator
) {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("IndividualSkills: $user", textAlign = TextAlign.Center)
        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Skill 1")})
        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Skill 2")})
        Button(onClick = {}) {
            Text("Submit")
        }
        Button(onClick = {
             navigator.navigate(ProjectCreationPageDestination())
        }) {
            Text("Go to ProjectCreationPage")
        }
    }
}

@Composable
fun ClickCounter(clicks: Int, onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text("I've been clicked $clicks times")
    }
}