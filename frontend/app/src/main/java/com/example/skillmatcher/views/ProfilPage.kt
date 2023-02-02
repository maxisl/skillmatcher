package com.example.skillmatcher

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skillmatcher.api.getUser
import com.example.skillmatcher.api.getUserMail
import com.example.skillmatcher.data.Skill
import com.example.skillmatcher.data.User
import com.example.skillmatcher.ui.theme.Black
import com.example.skillmatcher.ui.theme.LMUGreen
import com.example.skillmatcher.ui.theme.SkillMatcherTheme
import com.example.skillmatcher.ui.theme.White
import com.example.skillmatcher.views.toBitmap
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

    getUser(getUserResponse, loadingResponse)
    val user = getUserResponse.value

    Log.d("user image", user.toString())

    val userImage = user.image

    if (userImage != null) {
        Log.d("user image", userImage)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(Black.value))
    ) {
        TopBar(
            name = "Profile", modifier = Modifier
                .padding(10.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        ProfileSection(user)
        Spacer(modifier = Modifier.height(4.dp))
        SkillSection()
        Spacer(modifier = Modifier.height(4.dp))
        FrontendHeader()
        Spacer(modifier = Modifier.height(4.dp))
        BackendHeader()
        Spacer(modifier = Modifier.height(4.dp))
        DatenbankHeader()
        Spacer(modifier = Modifier.height(4.dp))
        ProgrammiersprachenHeader()
        Spacer(modifier = Modifier.height(4.dp))
    }

    /*SkillMatcherTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = // MaterialTheme.colorScheme.background
            (Color(Color.Black.value))
        ) {
            Column {
                Row {
                     ProfileSection(user)
                }
            }
        }
    }*/

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
}

@Composable
fun SetImage(user: User) {
    val userImage = user.image
    if (userImage != null) {
        if(userImage.isNotEmpty()) {
            val bitmap = userImage.toBitmap()
            Image(
                painter = BitmapPainter(bitmap.asImageBitmap()),
                contentDescription = "Project_card"
            )
        }else {
            Image(
                painter = painterResource(id = R.drawable.mern_icon),
                contentDescription = "Project_card"
            )
        }
    } else {
        Image(
            painter = painterResource(id = R.drawable.mern_icon),
            contentDescription = "Project_card"
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
        ProfileStat(userName, studies = "Computer Science", graduateLevel = "Masters")
    }
}

@Composable
fun ProfileStat(
    name: String,
    studies: String,
    graduateLevel: String,
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
            color = Color(LMUGreen.value)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = studies,
            fontSize = 20.sp,
            color = Color(White.value)
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = graduateLevel,
            fontSize = 20.sp,
            color = Color(White.value)

        )
    }
}

@Composable
fun SkillSection(modifier: Modifier = Modifier, SkillList: List<Skill>) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = modifier.fillMaxWidth(2f)
    ) {
        Skill(name = "What are my skills?")
        /** Spacer(modifier = Modifier.height(4.dp))
        SkillOverview(frontend = "Frontend", backend = "Backend" , database = "Database")*/
    }
}

@Composable
fun SkillHeaders(modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = modifier
    ) {
        SkillOverview(frontend = "Frontend", backend = "Backend", database = "Database")
    }
}

@Composable
fun SkillOverview(
    frontend: String,
    backend: String,
    database: String,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = frontend,
            fontWeight = FontWeight.Bold,
            color = Color(LMUGreen.value),
            fontSize = 30.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = backend,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = database,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp
        )
    }
}

@Composable
fun Skill(
    name: String,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = name,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            color = Color(LMUGreen.value)
        )
    }
}

@Composable
fun FrontendHeader(modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = modifier.fillMaxWidth(2f)
    ) {
        //hier muss die Datenbankverknüpfung her
        FrontendOverview(frontend = "Frontend: ", text = "JavaFX, AndroidStudio, UI/UX Design")
    }
}


@Composable
fun FrontendOverview(
    frontend: String,
    text: String,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = frontend,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            color = Color(LMUGreen.value)
        )
    }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = text,
            color = Color(White.value),
            fontSize = 25.sp
        )
    }
}

@Composable
fun BackendHeader(modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = modifier
    ) {
        //hier muss die Datenbankverknüpfung her
        BackendOverview(backend = "Backend: ", text = "Gradle, Springboot")
    }
}


@Composable
fun BackendOverview(
    backend: String,
    text: String,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = backend,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            color = Color(LMUGreen.value)
        )
    }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = text,
            color = Color(White.value),
            fontSize = 25.sp
        )
    }
}

@Composable
fun DatenbankHeader(modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = modifier
    ) {
        //hier muss die Datenbankverknüpfung her
        DatenbankOverview(datenbank = "Datenbank: ", text = "Oracle")
        DatenbankOverview(datenbank = "", text = "MySQL")

    }
}


@Composable
fun DatenbankOverview(
    datenbank: String,
    text: String,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = datenbank,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            color = Color(LMUGreen.value)
        )

    }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = text,
            color = Color(White.value),
            fontSize = 25.sp
        )
    }
}


@Composable
fun ProgrammiersprachenHeader(modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = modifier.fillMaxWidth(2f)
    ) {
        //hier muss die Datenbankverknüpfung her
        ProgrammiersprachenOverview(
            programmiersprache = "Programmiersprache: ",
            text = "Java, Kotlin, Python"
        )
    }
}


@Composable
fun ProgrammiersprachenOverview(
    programmiersprache: String,
    text: String,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = programmiersprache,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            color = Color(LMUGreen.value)
        )
    }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = text,
            color = Color(White.value),
            fontSize = 25.sp,
        )
    }
}
