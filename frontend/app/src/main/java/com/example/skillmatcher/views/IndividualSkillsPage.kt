package com.example.skillmatcher

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.navigation.compose.rememberNavController
import com.example.skillmatcher.components.DrawerBody
import com.example.skillmatcher.components.NavHost
import com.example.skillmatcher.components.navigationDrawerItemList
import com.example.skillmatcher.data.User
import com.example.skillmatcher.destinations.LandingPageDestination
import com.example.skillmatcher.destinations.ProjectCreationPageDestination
import com.example.skillmatcher.ui.theme.SkillMatcherTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import java.time.LocalDateTime
import com.example.skillmatcher.components.*
import com.ramcosta.composedestinations.annotation.RootNavGraph
import kotlinx.coroutines.launch

@Destination
@Composable
fun SideBar(id: Int, // <-- required navigation argument
            user: User?,
            navigator: DestinationsNavigator?
) {
    SkillMatcherTheme() {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {

            val scaffoldState = rememberScaffoldState()
            val scope = rememberCoroutineScope()
            val navController = rememberNavController()

            Scaffold(
                modifier = Modifier.fillMaxSize(),
                scaffoldState = scaffoldState,
                topBar = {
                    TopBar(
                        titleResId = R.string.app_name,
                        openDrawer =
                        {
                            scope.launch {
                                // Open the drawer with animation
                                // and suspend until it is fully
                                // opened or animation has been canceled
                                scaffoldState.drawerState.open()
                            }
                        }
                    )
                },
                drawerGesturesEnabled = true,
                drawerContent = {
                    DrawerBody(
                        menuItems = navigationDrawerItemList(),
                        scaffoldState,
                        scope
                    ) {
                        navController.navigate(it.id.name) {
                            popUpTo = navController.graph.startDestinationId
                            launchSingleTop = true
                        }
                    }
                },
                content = {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (navigator != null) {
                            NavHost(navController = navController,navigator)
                        }
                    }
                },
                floatingActionButton = {
                    //Create a floating action button in floatingActionButton parameter of scaffold
                    FloatingActionButton(

                        onClick = {
                            //When clicked open Snackbar
                            scope.launch {
                                when (scaffoldState.snackbarHostState.showSnackbar(
                                    message = "Snack Bar", //Message In the snackbar
                                    actionLabel = "Dismiss"
                                )) {
                                    SnackbarResult.Dismissed -> {
                                        //do something when snack bar is dismissed
                                    }

                                    SnackbarResult.ActionPerformed -> {
                                        //when it appears
                                    }

                                }
                            }
                        }) {

                        //Simple Text inside FAB
                        Text(text = "Add")
                    }
                }
            )
        }
    }
}

@Destination
@Composable
fun IndividualSkillsPage(navigator: DestinationsNavigator){
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
    Surface() {
        Column {
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
