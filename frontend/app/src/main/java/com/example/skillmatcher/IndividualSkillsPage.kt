package com.example.skillmatcher

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
    Surface {
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
                PrimarySkills()
            }
            Row(modifier = Modifier.padding(top = 25.dp)) {
                SecondarySkills()
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
fun PrimarySkills() {
    /* TODO implement state?
    var skill1State = remember { mutableStateOf("") }
    val skill2State = remember { mutableStateOf("") }*/
    Surface() {
        Column {
            Text("Primary Skills: User 1", textAlign = TextAlign.Center)
            /*var textFieldCount by rememberSaveable {
                mutableStateOf(1)
            }*/
            var skill by remember { mutableStateOf("") }
            val skills = remember { mutableStateListOf<String>() }
            OutlinedTextField(
                value = skill,
                onValueChange = { skill = it },
                label = { Text("Skill") },
                shape = RoundedCornerShape(8.dp)
            )

            Button(onClick = { skills.add(skill) }) {
                Text("Add")
            }

            for (addedSkill in skills) {
                Text(addedSkill)
            }
/*
            // lazy to enable scrolling
            LazyColumn {
                items(textFieldCount) {
                    SpecifySkill()
                }
            }
            Button(onClick = {
                textFieldCount++
            }) {
                Text("Add")
            }*/
        }
    }
}

@Composable
fun SecondarySkills() {
    /*var skill1State = remember { mutableStateOf("") }
    val skill2State = remember { mutableStateOf("") }*/
    Surface() {
        Column {
            Text("Secondary Skills: User 1", textAlign = TextAlign.Center)
            var textFieldCount by rememberSaveable {
                mutableStateOf(1)
            }
            // lazy to enable scrolling
            LazyColumn {
                items(textFieldCount) {
                    SpecifySkill()
                }
            }
            Button(onClick = {
                textFieldCount++
            }) {
                Text("Add")
            }
        }
    }
}


@Composable
fun SpecifySkill() {
    OutlinedTextField(
        value = "",
        onValueChange = {},
        label = { Text("Skill") },
        shape = RoundedCornerShape(8.dp)
    )
    Spacer(modifier = Modifier.height(16.dp))
}
