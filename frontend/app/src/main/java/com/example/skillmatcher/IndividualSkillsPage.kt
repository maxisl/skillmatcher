package com.example.skillmatcher

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.skillmatcher.destinations.AllProjectsOverViewPageDestination.style
import com.example.skillmatcher.destinations.ProjectCreationPageDestination
import com.example.skillmatcher.ui.theme.SkillMatcherTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import java.time.LocalDateTime

@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview() {
    SkillMatcherTheme() {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
            IndividualSkillsPage(
                id = 1,
                User(name = "", id = "", created = LocalDateTime.now()),
                navigator = EmptyDestinationsNavigator
            )
        }
    }
}

@Destination
@Composable
fun IndividualSkillsPage(
    id: Int, // <-- required navigation argument
    user: User?,
    // TODO move navigator to dedicated function?
    navigator: DestinationsNavigator?
) {
    SkillScreen()
    Button(onClick = {
        navigator?.navigate(ProjectCreationPageDestination())
    }) {
        Text("Go to ProjectCreationPage")
    }
}


@Composable
fun SkillScreen() {
    var skill1State = remember { mutableStateOf("") }
    val skill2State = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .background(Color.White)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text("IndividualSkills: empty", textAlign = TextAlign.Center)
        // OutlinedTextField(value = "", onValueChange = {}, label = { Text("Skill 1") })
        // OutlinedTextField(value = "", onValueChange = {}, label = { Text("Skill 2") })
        Button(onClick = {}) {
            Text("Submit")
        }
        // SpecifySkill1(skill = skill1State.value, onSkillChange = { skill1State.value = it })
    }
}


@Composable
fun SpecifySkill1(skill: String, onSkillChange: (String) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Specified Skill: $skill",
            style = MaterialTheme.typography.h5
        )
        OutlinedTextField(
            value = skill,
            onValueChange = onSkillChange,
            label = { Text("Skill1") }
        )
    }
}

@Composable
fun SpecifySkill2(skill: String, onSkillChange: (String) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = skill,
            style = MaterialTheme.typography.h5
        )
        OutlinedTextField(value = skill, onValueChange = onSkillChange,
            label = { Text("Skill1") })
    }
}