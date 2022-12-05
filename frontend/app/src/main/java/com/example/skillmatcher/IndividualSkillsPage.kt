package com.example.skillmatcher

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.skillmatcher.destinations.AllProjectsOverViewPageDestination.style
import com.example.skillmatcher.destinations.LandingPageDestination
import com.example.skillmatcher.destinations.ProjectCreationPageDestination
import com.example.skillmatcher.ui.theme.SkillMatcherTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import java.time.LocalDateTime

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SkillMatcherTheme() {
        Surface(
            modifier = Modifier.fillMaxHeight(1f),
            color = Color.White
        ) {
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
    navigator: DestinationsNavigator?
) {
    Column(
        modifier = Modifier.fillMaxSize(1f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(1f)
                .background(MaterialTheme.colors.primary),
            horizontalArrangement = Arrangement.Center,
        ) {
            LogoBanner(navigator)
        }
        Row(modifier = Modifier.padding(top = 25.dp)) {
            SkillScreen()
        }
        Row(modifier = Modifier.padding(top = 25.dp)) {
            SkillScreen2()
        }
        Row(
            verticalAlignment = Alignment.Bottom
        ) {
            Button(onClick = {
                navigator?.navigate(ProjectCreationPageDestination())
            }) {
                Text("Go to ProjectCreationPage")
            }
        }
    }
}

@Composable
fun LogoBanner(navigator: DestinationsNavigator?) {
    Row(
        modifier = Modifier.padding(25.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Specify Your Individual Skills",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h5,
            color = Color.White,
        )
        // TODO add onclick functionality
        IconButton(onClick = { navigator?.navigate(LandingPageDestination) }) {
            Icon(
                Icons.Rounded.AccountCircle,
                contentDescription = stringResource(id = R.string.profile_icon_content_desc),
                modifier = Modifier.padding(start = 10.dp),
                tint = Color.White
            )
        }

    }
}

@Composable
fun SkillScreen() {
    /* TODO implement state?
    var skill1State = remember { mutableStateOf("") }
    val skill2State = remember { mutableStateOf("") }*/

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("Primary Skills: User 1", textAlign = TextAlign.Center)
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text("Skill 1") },
            shape = RoundedCornerShape(8.dp)
        )
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text("Skill 2") },
            shape = RoundedCornerShape(8.dp)
        )
        Button(onClick = {}) {
            Text("Submit")
        }
        // SpecifySkill1(skill = skill1State.value, onSkillChange = { skill1State.value = it })
    }
}

@Composable
fun SkillScreen2() {
    /*var skill1State = remember { mutableStateOf("") }
    val skill2State = remember { mutableStateOf("") }*/

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("Secondary Skills: User 1", textAlign = TextAlign.Center)
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text("Skill 1") },
            shape = RoundedCornerShape(8.dp)
        )
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text("Skill 2") },
            shape = RoundedCornerShape(8.dp)
        )
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