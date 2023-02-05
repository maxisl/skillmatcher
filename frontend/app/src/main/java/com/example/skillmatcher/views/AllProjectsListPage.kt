package com.example.skillmatcher

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.Navigator
import com.example.skillmatcher.api.getAllProjects
import com.example.skillmatcher.api.getRequiredSkills
import com.example.skillmatcher.data.Project
import com.example.skillmatcher.destinations.OwnProjectOverviewPageDestination
import com.example.skillmatcher.ui.theme.SkillMatcherTheme
import com.example.skillmatcher.views.toBitmap
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

// TODO projects is empty



@Destination
@Composable
fun AllProjectsListPage(navigator: DestinationsNavigator) {
    val projectListResponse = remember {
        mutableStateOf(listOf(Project(0, "", "", "", "", "", null, listOf())))
    }
    try {
        getAllProjects(projectListResponse)
    } catch (e: Exception) {

    }
    val projects = projectListResponse.value
    SkillMatcherTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = // MaterialTheme.colorScheme.background
            (Color(Color.Black.value))
        ) {
            Column {
                Row {
                    ProjectsList(cardIcon = R.drawable.mern_icon, projects, navigator)
                }
            }
        }
    }
}


@Composable
private fun ProjectsList(cardIcon: Int, projects: List<Project>, navigator: DestinationsNavigator) {
    LazyColumn(
        modifier = Modifier
            // .weight(1f)
            .background(Color(Color.Black.value)),
    ) {

        projects.iterator().forEach { project ->
            item() {
                if (project.name.isNotEmpty()) {
                    ProjectCard(cardIcon, project = project, navigator)
                }
            }
        }
    }
}

@Composable
fun ProjectCard(cardIcon: Int, project: Project, navigator: DestinationsNavigator) {
    val projectName = project.name
    val projectAttendees = project.maxAttendees
    val projectDescription = project.description
    val projectStartDate = project.startDate
    val projectEndDate = project.endDate
    val projectImage = project.image
    val projectSkills = project.requiredSkillsIds

    // get required skills of each project
    val projectSkillsResponse = remember {
        mutableStateOf(listOf(com.example.skillmatcher.data.Skill(0,"", 0, false)))
    }
    getRequiredSkills(projectSkillsResponse, project.id)
    val requiredSkills = projectSkillsResponse.value

    // transform required skills into String List
    val skillNameList = requiredSkills.joinToString(", ") { it.name }


    Card(
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .clickable { }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
        ) {
            Row {
                if (checkForImageString(project.image)) {
                    val bitmap = project.image!!.toBitmap()
                    Image(
                       // bitmap = imageBitmapFromBytes(project.image!!.toByteArray()),
                        painter = BitmapPainter(bitmap!!.asImageBitmap()),
                        contentDescription = "Project_card",
                        modifier = Modifier
                            .height(90.dp)
                            .width(90.dp)
                    )
                } else {
                Image(
                    painter = painterResource(id = cardIcon),
                    contentDescription = "Project_card",
                    // adjust image size to fit card
                    modifier = Modifier
                        .height(90.dp)
                        .width(90.dp)
                )
                }

                Row(modifier = Modifier.padding(top = 2.dp, start = 10.dp)) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Project: $projectName",
                            style = TextStyle(
                                fontSize = 16.sp,
                            )
                        )
                        Text(
                            text = "Max. Attendees: $projectAttendees",
                            style = TextStyle(
                                fontSize = 16.sp,
                            )
                        )
                        Text(
                            text = "Start Date: $projectStartDate",
                            style = TextStyle(
                                fontSize = 16.sp,
                            )
                        )
                        Text(
                            text = "End Date: $projectEndDate",
                            style = TextStyle(
                                fontSize = 16.sp,
                            )
                        )
                    }
                    Column() {
                        Box(
                            modifier = Modifier
                                .background(
                                    color = MaterialTheme.colorScheme.background,
                                    shape = RoundedCornerShape(7.dp)
                                )
                        ) {
                            IconButton(onClick = {
                                // TODO add as soon as navigation is available on this page
                                navigator.navigate(
                                    OwnProjectOverviewPageDestination(project)
                                )
                            }){
                                Icon(
                                    Icons.Default.Add,
                                    tint = Color.White,
                                    modifier = Modifier.padding(10.dp),
                                    contentDescription = "Project_card_icon"
                                )
                            }
                        }
                    }
                }

            }
            Row(modifier = Modifier.padding(top = 20.dp)) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Description: $projectDescription",
                        style = TextStyle(
                            fontSize = 16.sp,
                        )
                    )

                    Spacer(modifier = Modifier.height(7.dp))
                    Divider(thickness = 1.dp)
                    Spacer(modifier = Modifier.height(7.dp))

                    Text(
                        text = "Req. Skills: $skillNameList",
                        style = TextStyle(
                            fontSize = 16.sp,
                        )
                    )
                }
            }
        }
    }
}
