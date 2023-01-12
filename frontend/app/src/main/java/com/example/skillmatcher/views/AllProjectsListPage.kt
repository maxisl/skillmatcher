package com.example.skillmatcher

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skillmatcher.api.getAllProjects
import com.example.skillmatcher.data.Project
import com.example.skillmatcher.ui.theme.SkillMatcherTheme
import com.ramcosta.composedestinations.annotation.Destination

// TODO projects is empty


@Preview
@Destination
@Composable
fun AllProjectsListPage(
) {
    val response = remember { mutableStateOf(listOf(Project("", "", ""))) }
    getAllProjects(response)
    val projects = response.value
    SkillMatcherTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = // MaterialTheme.colorScheme.background
            (Color(Color.Black.value))
        ) {
            Column {
                Row {
                    ProjectsList(cardIcon = R.drawable.mern_icon, projects)
                }
            }
        }
    }
}


@Composable
private fun ProjectsList(cardIcon: Int, projects: List<Project>) {
    LazyColumn(
        modifier = Modifier
            // .weight(1f)
            .background(Color(Color.Black.value)),
    ) {
        items(1) {
            projects.forEach { Project ->
                ProjectCard(cardIcon, project = Project)
                // ProjectCard(cardIcon)
            }
        }
    }
}
/*
@Composable
private fun ProjectCard(cardIcon: Int) { //Project: Projects
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
                Image(
                        modifier = Modifier.size(80.dp),
                        painter = painterResource(id = cardIcon),
                        // bitmap = ImageBitmap.imageResource(id = icon),
                        contentDescription = "Project_card"
                )

                Row(modifier = Modifier.padding(top = 2.dp, start = 10.dp)) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                                text = "Title: MERN Meme Generator",
                                style = TextStyle(
                                        fontSize = 16.sp,
                                )
                        )
                        Text(
                                text = "Member: 10",
                                style = TextStyle(
                                        fontSize = 16.sp,
                                )
                        )
                        Text(
                                text = "Date: 10.01.2023",
                                style = TextStyle(
                                        fontSize = 16.sp,
                                )
                        )
                    }
                }

            }
            Row(modifier = Modifier.padding(top = 20.dp)) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                            text = "Description: Build a meme Generator based on the MERN Stack. After the creation Memes should be saved on a Server",
                            style = TextStyle(
                                    fontSize = 16.sp,
                            )
                    )

                    Spacer(modifier = Modifier.height(7.dp))
                    Divider(thickness = 1.dp)
                    Spacer(modifier = Modifier.height(7.dp))

                    Text(
                            text = "Skills Needed: Express (JS), React (JS)",
                            style = TextStyle(
                                    fontSize = 16.sp,
                            )
                    )
                }
            }
        }
    }
}
*/

@Composable
fun ProjectCard(cardIcon: Int, project: Project) { //Project: Projects
    val projectName = project.name
    val projectAttendees = project.maxAttendees
    val projectDescription = project.description

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
                Image(
                    modifier = Modifier.size(80.dp),
                    painter = painterResource(id = cardIcon),
                    // bitmap = ImageBitmap.imageResource(id = icon),
                    contentDescription = "Project_card"
                )

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
                            text = "Date: 10.01.2023",
                            style = TextStyle(
                                fontSize = 16.sp,
                            )
                        )
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
                        text = "Skills Needed: Express (JS), React (JS)",
                        style = TextStyle(
                            fontSize = 16.sp,
                        )
                    )
                }
            }
        }
    }
}
