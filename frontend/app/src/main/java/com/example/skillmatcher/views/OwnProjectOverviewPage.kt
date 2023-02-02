package com.example.skillmatcher

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.foundation.BorderStroke
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skillmatcher.data.Project
import androidx.core.content.ContextCompat.startActivity
import com.example.skillmatcher.activity.ChannelListActivity
import com.example.skillmatcher.api.attendProject
import com.example.skillmatcher.api.getAttendees
import com.example.skillmatcher.api.leaveProject
import com.example.skillmatcher.data.User
import com.example.skillmatcher.destinations.AllProjectsListPageDestination
import com.example.skillmatcher.ui.theme.Black
import com.example.skillmatcher.ui.theme.LMUGreen
import com.example.skillmatcher.ui.theme.White
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.example.skillmatcher.views.toBitmap


@Destination
@Composable
fun OwnProjectOverviewPage(navigator: DestinationsNavigator, project: Project) {
    // context for pop up notifications
    val ctx = LocalContext.current

    val projectId = project.id

    var projectAttendeesResponse = remember {
        mutableStateOf(
            listOf(User(0, "", mutableListOf(), mutableListOf(), ""))
        )
    }
    getAttendees(projectAttendeesResponse, projectId)
    val projectAttendees = projectAttendeesResponse.value

    // transform attendees emails into String List
    val projectAttendeesList = projectAttendees.joinToString(", ") { it.email }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(Black.value))
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                HeadBar(
                    name = "Project Overview", modifier = Modifier
                        .padding(5.dp)
                )
                Divider(color = Color(White.value), thickness = 1.dp)
                //val imageBitmap = project.image?.toBitmap()
                LogoSection(project.image)
                NameSection(project.name)
                ExpandableCard(title = "Participants", description = projectAttendeesList)
                DescriptionSection(project.description)
                Divider(color = Color(White.value), thickness = 1.dp)
                ChatButton()
                AttendProjectButton(ctx, projectId)
                LeaveProjectButton(navigator, ctx, projectId)

            }
        }
    }
}

@Composable
fun HeadBar(
    name: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = modifier.fillMaxWidth()
    ) {
        androidx.compose.material3.Text(
            text = name,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            color = Color(LMUGreen.value)
        )

    }
}

@Composable
fun LogoSection(image: String?) {
    Column() {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ProjectLogo(image)
            Spacer(modifier = Modifier.width(16.dp))
        }
    }
}

@Composable
fun ProjectLogo(
    image: String?,
) {
    /*if (checkForImageString(image)) {
       // val bitmapImage: Bitmap? = image
        Image(
            modifier = Modifier.size(20.dp),
            bitmap = imageBitmapFromBytes(image!!.toByteArray()),
            // bitmap = ImageBitmap.imageResource(id = icon),
            contentDescription = "Project_card"
        )
    } else {*/
    Image(
        painter = painterResource(id = R.drawable.mern_icon),
        contentDescription = null,
        modifier = Modifier
            .clip(CircleShape)
            .border(BorderStroke(4.dp, Color.White), CircleShape)
    )
    //}
}

@Composable
fun NameSection(projectName: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.fillMaxWidth()
    ) {
        ProjectName(name = projectName)
    }
}

@Composable
fun ProjectName(
    name: String,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        androidx.compose.material3.Text(
            text = name,
            fontWeight = FontWeight.Bold,
            fontSize = 40.sp,
            color = Color(LMUGreen.value),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun DescriptionSection(description: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
    ) {
        ProjectDescription(fieldName = "Description:", description = description)
    }
}

@Composable
fun ProjectDescription(
    fieldName: String, description: String,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Spacer(modifier = Modifier.height(15.dp))
        androidx.compose.material3.Text(
            text = fieldName,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color(LMUGreen.value),
        )
        Spacer(modifier = Modifier.height(30.dp))

        androidx.compose.material3.Text(
            text = description,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color(LMUGreen.value),
        )
    }
}

@Composable
fun ChatButton() {
    val mContext = LocalContext.current
    Button(
        onClick = {
            startActivity(mContext, Intent(mContext, ChannelListActivity::class.java), null)

        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        androidx.compose.material3.Text(
            text = "GroupChat",
            color = Color.White,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun LeaveProjectButton(
    navigator: DestinationsNavigator?,
    ctx: Context,
    projectId: Long
) {
    Button(
        onClick = {
            navigator?.navigate(AllProjectsListPageDestination())
            leaveProject(ctx, projectId)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        androidx.compose.material3.Text(
            text = "Leave Project",
            color = Color.White,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun AttendProjectButton(
    ctx: Context,
    projectId: Long
) {
    Button(
        onClick = {
            attendProject(ctx, projectId)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        androidx.compose.material3.Text(
            text = "Attend Project",
            color = Color.White,
            modifier = Modifier.padding(8.dp)
        )
    }
}


//, wer teilnehmer sind, Chat Button





