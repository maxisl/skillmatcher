package com.example.skillmatcher.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.skillmatcher.R
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.skillmatcher.destinations.LandingPageDestination
import com.example.skillmatcher.destinations.ProjectCreationPageDestination
import com.example.skillmatcher.ui.theme.Black
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@Destination
@Composable
fun IndividualSkillsPage(navigator: DestinationsNavigator){
    Surface {
        Column(
                modifier = Modifier.fillMaxSize(1f).background(Color(Black.value)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                    modifier = Modifier
                            .fillMaxWidth(1f)
                            .background(MaterialTheme.colorScheme.background),
                    horizontalArrangement = Arrangement.Center,
            ) {
                // LogoBanner(navigator)
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
                // have to specify material3 explicitly as of now? - not sure why
                Button(onClick = {
                    navigator.navigate(ProjectCreationPageDestination())
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
                //style = MaterialTheme.typography.h5,
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
    Surface {
        Column (modifier = Modifier.background((Color(Black.value)))){
            Text("Primary Skills: User 1", textAlign = TextAlign.Center)
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
fun SecondarySkills() {
    Surface {
        Column(modifier = Modifier.background((Color(Black.value)))) {
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpecifySkill(/*skill: String, onSkillChange: (String) -> Unit */) {
    var skill by remember { mutableStateOf("") }
    var color by remember { mutableStateOf(Color.White) }
    OutlinedTextField(
            value = skill,
            onValueChange = { skill = it},
            label = { Text("Skill") },
            shape = RoundedCornerShape(8.dp),
    )
    Spacer(modifier = Modifier.height(16.dp))
}
