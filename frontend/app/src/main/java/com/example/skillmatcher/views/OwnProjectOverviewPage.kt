package com.example.skillmatcher

import android.content.Context
import androidx.compose.foundation.BorderStroke
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skillmatcher.data.Project
import androidx.core.content.ContextCompat.startActivity
import com.example.skillmatcher.activity.ChannelListActivity
import com.example.skillmatcher.api.*
import com.example.skillmatcher.data.InputCheck
import com.example.skillmatcher.data.Skill
import com.example.skillmatcher.data.User
import com.example.skillmatcher.destinations.SideBarDestination
import com.example.skillmatcher.ui.theme.Black
import com.example.skillmatcher.ui.theme.LMUGreen
import com.example.skillmatcher.ui.theme.White
import com.example.skillmatcher.views.createSKillCards
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.SizeMode
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.models.Channel


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

    val getUserSkillsResponse = remember {
        mutableStateOf(listOf(Skill(0,"", 0, false)))
    }
    val userSkillsList = getUserSkillsResponse.value
    getUserSkills(getUserSkillsResponse)

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
                requiredSkillsSection(userSkillsList)
                Divider(color = Color(White.value), thickness = 1.dp)
                ChatButton()
                AttendProjectButton(ctx, projectId, project.name)
                LeaveProjectButton(navigator, ctx, projectId, project.name)

            }
            // TODO add required skills of project
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
    projectId: Long,
    projectName: String
) {
    val channelClient = ChatClient.instance()
    val getUserResponse = remember {
        mutableStateOf(User(0, "", mutableListOf(), mutableListOf(), ""))
    }
    val loadingResponse = remember {
        mutableStateOf(false)}
        getUser(getUserResponse, loadingResponse)
    val user = getUserResponse.value
    val email= user.email
    val uname2= email.replace(".", "")

    Button(
        onClick = {
            navigator?.navigate(SideBarDestination(id = 1))
            leaveProject(ctx, projectId)
            Log.d("Projectname", projectName)
            val nameProject=projectName.replace(" ", "")
            channelClient.removeMembers("messaging", nameProject, listOf(uname2), null).enqueue { result ->
                if (result.isSuccess) {
                    val channel: Channel = result.data()
                    Log.d(" removed", "User is not an attendee anymore. ")
                } else {
                    Log.d("not removed", "User is still an attendee. ")
                }
            }

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
    projectId: Long,
    projectName: String
) {
    val channelClient = ChatClient.instance()
    val getUserResponse = remember {
        mutableStateOf(User(0, "", mutableListOf(), mutableListOf(), ""))
    }

    val loadingResponse = remember {
        mutableStateOf(false)}
    getUser(getUserResponse, loadingResponse)
    val user = getUserResponse.value
    val email= user.email
    val uname2= email.replace(".", "")
    Button(
        onClick = {
            attendProject(ctx, projectId)
            val nameProject=projectName.replace(" ", "")
            channelClient.addMembers("messaging", nameProject, listOf(uname2), null).enqueue { result ->
                if (result.isSuccess) {
                    val channel: Channel = result.data()
                    Log.d("add", "User is now an attendee of that project.")
                } else {
                    Log.d("not add", "User couldn't add to that project. ")
                }
            }
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

@Composable
fun requiredSkillsSection(SkillList: List<Skill>){
    Column(modifier = Modifier
        .fillMaxWidth(2f)
        .padding(10.dp)
    ){
        Text(
            text = "List of requiredSkills",
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(10.dp)
        )
        ownProjectSKillCards(SkillList)
    }
}

@Composable
fun ownProjectSKillCards(skillList: List<Skill>){
    Box {
        FlowRow(
            mainAxisSize = SizeMode.Expand,
            crossAxisSpacing = 10.dp,
            mainAxisSpacing = 10.dp
        ) {
            if (skillList.isNotEmpty()) {
                for (skill in skillList) {
                    Surface(
                        modifier = Modifier.wrapContentSize(),
                        shape = RoundedCornerShape(5.dp),
                    ) {
                        Text(
                            text = skill.name,
                            style = TextStyle(fontSize = 20.sp),
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }
            } else {
                Surface(
                    modifier = Modifier.wrapContentSize(),
                    shape = RoundedCornerShape(5.dp),
                ) {
                    Text(
                        text = "No Skills added so far",
                        style = TextStyle(fontSize = 20.sp),
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }
        }
    }
}
