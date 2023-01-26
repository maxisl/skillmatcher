package com.example.skillmatcher

import android.graphics.Bitmap
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skillmatcher.data.Project
import com.example.skillmatcher.destinations.AllProjectsListPageDestination
import com.example.skillmatcher.ui.theme.Black
import com.example.skillmatcher.ui.theme.LMUGreen
import com.example.skillmatcher.ui.theme.White
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun OwnProjectOverviewPage(navigator: DestinationsNavigator, project: Project) {
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
                LogoSection(project.image)
                NameSection(project.name)
                ExpandableCard(title = "Participants", description = "navi, jason")
                DescriptionSection(project.description)
                ChatButton()
                LeaveProjectButton(navigator)
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
fun LogoSection(image: Bitmap?) {
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
    image: Bitmap?,
) {
    if (checkForImage(image)) {
        val bitmapImage: Bitmap? = image
        bitmapImage?.let { btm ->
            Image(
                bitmap = btm.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
            )
        }
    } else {
        Image(
            painter = painterResource(id = R.drawable.mern_icon),
            contentDescription = null,
            modifier = Modifier
                .clip(CircleShape)
                .border(BorderStroke(4.dp, Color.White), CircleShape)
        )
    }
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
    Button(
        onClick = {
            //kommt man zur Chatseite
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
fun LeaveProjectButton(navigator: DestinationsNavigator?) {
    Button(
        onClick = {
            navigator?.navigate(AllProjectsListPageDestination())
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


//, wer teilnehmer sind, Chat Button





