package com.example.skillmatcher

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skillmatcher.api.getUser
import com.example.skillmatcher.api.getUserMail
import com.example.skillmatcher.api.getUserSkills
import com.example.skillmatcher.data.Project
import com.example.skillmatcher.data.Skill
import com.example.skillmatcher.data.User
import com.example.skillmatcher.ui.theme.Black
import com.example.skillmatcher.ui.theme.LMUGreen
import com.example.skillmatcher.ui.theme.White
import com.example.skillmatcher.views.toBitmap
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.SizeMode
import com.ramcosta.composedestinations.annotation.Destination

@Preview
@Destination
@Composable
fun LandingPage() {
    // TODO pass all relevant attributes to different sections
    // TODO create co routine to load user info and avoid app crash

    val getUserResponse = remember {
        mutableStateOf(User(0, "", mutableListOf(), mutableListOf(), ""))
    }

    val loadingResponse = remember {
        mutableStateOf(false)
    }

    val getUserSkillsResponse = remember {
        mutableStateOf(listOf(Skill("", 0, false)))
    }

    getUser(getUserResponse, loadingResponse)
    getUserSkills(getUserSkillsResponse)

    val user = getUserResponse.value
    val userProjectsList = user.projects.map { it }
    val userSkillsList = getUserSkillsResponse.value


    Log.d("ProfilPage", "Skill List: $userSkillsList ")
    Log.d("ProfilPage", "User Infos: $user ")
    Log.d("user image", user.toString())

    val userImage = user.image

    if (userImage != null) {
        Log.d("user image", userImage)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(Color.Black.value)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            TopBar(
                name = "Profile", modifier = Modifier
                    .padding(10.dp)
            )
        }
        item {
            ProfileSection(user)
        }
        item {
            SkillSection(userSkillsList)
        }
        item {
            ProjectList(userProjectsList)
        }
    }
}


@Composable
fun TopBar(
    name: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = name,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold,
            fontSize = 60.sp,
            color = Color(LMUGreen.value)
        )
    }
    Spacer(modifier = Modifier.height(15.dp))
}

@Composable
fun ProfileSection(
    user: User
) {
    val modifier = Modifier
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            SetImage(user)

            Spacer(modifier = Modifier.width(16.dp))
            StatSection(modifier = Modifier.weight(7f))
        }
    }
    Spacer(modifier = Modifier.height(15.dp))
}

@Composable
fun SetImage(user: User) {
    val userImage = user.image
    if (userImage != null) {
        if (userImage.isNotEmpty()) {
            val bitmap = userImage.toBitmap()
            Image(
                painter = BitmapPainter(bitmap.asImageBitmap()),
                contentDescription = "Project_card"
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.mern_icon),
                contentDescription = "Project_card",
                modifier = Modifier
                    .height(90.dp)
                    .width(90.dp)
            )
        }
    } else {
        Image(
            painter = painterResource(id = R.drawable.mern_icon),
            contentDescription = "Project_card",
            modifier = Modifier
                .height(90.dp)
                .width(90.dp)
        )
    }
}

@Composable
fun RoundImage(
    image: Painter,
    modifier: Modifier = Modifier
) {
    Image(
        painter = image, contentDescription = null,
        modifier = modifier
            .aspectRatio(1f, matchHeightConstraintsFirst = true)
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = CircleShape
            )
            .padding(5.dp)
            .clip(CircleShape)
    )
}

@Composable
fun StatSection(modifier: Modifier = Modifier) {
    val response = remember {
        mutableStateOf("")
    }
    // dynamically retrieve mail of logged in user
    getUserMail(response)
    val userName = response.value
    // show retrieved mail in profile
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = modifier
    ) {
        ProfileStat(userName)
    }
}

@Composable
fun ProfileStat(
    name: String,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = name,
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
            color = Color(LMUGreen.value),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun SkillCards(skillList: List<Skill>) {
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
                        color = Color.DarkGray
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
                    color = Color.DarkGray
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

@Composable
fun SkillSection(SkillList: List<Skill>) {
    Column(
        modifier = Modifier
            .fillMaxWidth(2f)
            .padding(10.dp)
    ) {
        Text(
            text = "List of added skills:",
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(10.dp)
        )
        SkillCards(skillList = SkillList)
    }
    Spacer(modifier = Modifier.height(15.dp))
}

@Composable
fun ProjectList(projectList: List<Project>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Text(
            text = "Currently attending projects:",
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(10.dp)
        )
        if (projectList.isNotEmpty()) {
            for (project in projectList) {
                Surface(
                    modifier = Modifier.padding(10.dp),
                    shape = RoundedCornerShape(5.dp),
                    color = Color.DarkGray
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = project.name,
                            style = TextStyle(fontSize = 20.sp),
                            modifier = Modifier.padding(10.dp)
                        )
                        Text(
                            text = project.description,
                            style = TextStyle(fontSize = 15.sp),
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }
            }
        } else {
            Surface(
                modifier = Modifier.wrapContentSize(),
                shape = RoundedCornerShape(5.dp),
                color = Color.DarkGray
            ) {
                Text(
                    text = "Not attending any projects so far",
                    style = TextStyle(fontSize = 20.sp),
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
    }
}