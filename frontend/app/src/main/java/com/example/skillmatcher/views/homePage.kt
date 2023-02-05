package com.example.skillmatcher

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skillmatcher.api.getProjectsByUserEmail
import com.example.skillmatcher.data.Project
import com.example.skillmatcher.destinations.OwnProjectOverviewPageDestination
import com.example.skillmatcher.views.toBitmap
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@Destination
@Composable
fun HomePage(
    navigator: DestinationsNavigator,
) {
    // TODO loading icon until getProjects succeeded

    val projectResponse = remember {
        mutableStateOf(listOf(Project(0, "", "", "", "", "", null, listOf())))
    }

    var userProjects: List<Project> = emptyList()
    try {
        getProjectsByUserEmail(projectResponse)
        userProjects = projectResponse.value
    } catch (e: Exception) {
        Log.d("ExceptionInHome: ", e.toString())
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(Color.Black.value)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item {
            drawYourProjectsHeadline()
        }
        item {
            drawYourProjects(userProjects, navigator)
        }
        item {
            drawNewestProjectsHeadline()
        }
        item {
            //val sortedByDateList = userProjects.sortedWith(compareBy { formatStringToDate(it.startDate) })
            val sortedByDateList = userProjects.reversed()
            drawYourProjects(sortedByDateList, navigator)
        }
        item {
            drawBiggestProjectsHeadline()
        }
        item {
            val sortedBySizeList = userProjects.sortedWith(compareBy { it.maxAttendees })
            drawYourProjects(sortedBySizeList, navigator)
        }
    }
}

@Composable
fun drawYourProjectsHeadline() {
    Row(
        modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 20.dp, top = 40.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Your Projects",
            style = TextStyle(
                fontSize = 18.sp,
                //fontFamily = FontFamily(Font(R.font.helvetica_neue_bold))
            ),
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun drawNewestProjectsHeadline() {
    Row(
        modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 20.dp, top = 40.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Newest Projects",
            style = TextStyle(
                fontSize = 18.sp,
                //fontFamily = FontFamily(Font(R.font.helvetica_neue_bold))
            ),
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun drawBiggestProjectsHeadline() {
    Row(
        modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 20.dp, top = 40.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Biggest Projects",
            style = TextStyle(
                fontSize = 18.sp,
                //fontFamily = FontFamily(Font(R.font.helvetica_neue_bold))
            ),
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun drawYourProjects(userProjects: List<Project>, navigator: DestinationsNavigator) {
    LazyRow(
        modifier = Modifier.fillMaxWidth()
    ) {
        userProjects.iterator().forEach { project ->
            item() {
                if (project.name.isNotEmpty()) {
                    projectCard(cardIcon = R.drawable.mern_icon, project, navigator)
                }
            }
        }
    }
}

@Composable
fun projectCard(
    cardIcon: Int,
    userProject: Project,
    navigator: DestinationsNavigator
) { //Project: Projects

    Card(
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier
            .padding(10.dp)
            .width(180.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
        ) {
            if (checkForImageString(userProject.image)) {
                val bitmap = userProject.image!!.toBitmap()
                Image(
                    // bitmap = imageBitmapFromBytes(project.image!!.toByteArray()),
                    painter = BitmapPainter(bitmap!!.asImageBitmap()),
                    contentDescription = "Project_card",
                    modifier = Modifier.size(140.dp)
                )
            } else {
                Image(
                    painter = painterResource(id = cardIcon),
                    contentDescription = "Project_card",
                    // adjust image size to fit card
                    modifier = Modifier.size(140.dp)

                )
            }
        }
        Row(modifier = Modifier.padding(top = 20.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = userProject.name,
                    style = TextStyle(
                        //color = gray,
                        fontSize = 16.sp,
                        //fontFamily = FontFamily(Font(R.font.helvetica_neue_medium))
                    )
                )
            }
            Box(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.background,
                        shape = RoundedCornerShape(10.dp)
                    )
            ) {
                IconButton(onClick = {
                    navigator.navigate(
                        OwnProjectOverviewPageDestination(userProject)
                    )
                }) {
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

fun checkForImage(image: Bitmap?): Boolean {
    return image != null
}

fun checkForImageString(image: String?): Boolean {
    if (image != null) {
        if (image.isNotEmpty()) {
            return true
        }
    }
    return false
}

fun imageBitmapFromBytes(encodedImageData: ByteArray): ImageBitmap {
    if (encodedImageData.isNotEmpty()) {
        return BitmapFactory.decodeByteArray(encodedImageData, 0, encodedImageData.size)
            .asImageBitmap()
    } else {
        // if decoded String is null, create empty Bitmap
        return Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888).asImageBitmap()
    }
}
