package com.example.skillmatcher

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material3.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skillmatcher.api.registerUser
import com.example.skillmatcher.destinations.AllProjectsOverViewPageDestination
import com.example.skillmatcher.destinations.LoginPageDestination
import com.example.skillmatcher.destinations.ProjectCreationPageDestination
import com.example.skillmatcher.ui.theme.Black
import com.example.skillmatcher.ui.theme.LMUGreen
import com.example.skillmatcher.ui.theme.White
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun OwnProjectOverviewPage(navigator: DestinationsNavigator?){
        Column(
            modifier = Modifier
                    .fillMaxSize()
                    .background(Color(Black.value))
        ) {
            HeadBar(
                name = "Project Overview", modifier = Modifier
                    .padding(5.dp)
            )
            Divider(color = Color(White.value), thickness = 1.dp)
            LogoSection()
            NameSection()
            ExpandableCard(title = "Participants", description = "navi, jason")
            DescriptionSection()
            ChatButton()
            LeaveProjectButton(navigator)
        }
    }

@Composable
fun HeadBar(
        name:String,
        modifier: Modifier=Modifier
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
            modifier =modifier.fillMaxWidth()
        ){
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
fun LogoSection(
    modifier: Modifier= Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
        ) {
            ProjectLogo(
                image = painterResource(id = R.drawable.nice_cat), modifier = Modifier
                    .size(120.dp)
                    .weight(3f)
            )
            Spacer(modifier = Modifier.width(16.dp))
        }
    }
}
@Composable
fun ProjectLogo(
    image: Painter,
    modifier: Modifier =Modifier
){
    Image(painter = image, contentDescription = null,
        modifier= modifier
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
fun NameSection (modifier: Modifier= Modifier){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = modifier.fillMaxWidth()
    ){
        ProjectName(name = "Skillmatcher")
    }
}
@Composable
fun ProjectName(name:String,
                modifier: Modifier=Modifier
){
    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ){
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
fun DescriptionSection (modifier: Modifier= Modifier){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = modifier
    ){
        ProjectDescription(fieldName = "Description:", description = "Es handelt sich um " +
                "eine Eintragung der Skills von Studierenden App")
    }
}
@Composable
fun ProjectDescription(fieldName:String, description:String,
                modifier: Modifier=Modifier
){
    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ){
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
    Button(colors = ButtonDefaults.buttonColors(containerColor =Color(LMUGreen.value)),
        onClick = {
            //kommt man zur Chatseite
        },
        modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
    ) {
        androidx.compose.material3.Text(text = "GroupChat",  color = Color.White, modifier = Modifier.padding(8.dp))
    }
}

@Composable
fun LeaveProjectButton(navigator: DestinationsNavigator?) {
    Button(colors = ButtonDefaults.buttonColors(containerColor =Color(LMUGreen.value)),
            onClick = {
                navigator?.navigate(AllProjectsOverViewPageDestination())
            },
            modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
    ) {
        androidx.compose.material3.Text(text = "Leave Project",  color = Color.White, modifier = Modifier.padding(8.dp))
    }
}


//, wer teilnehmer sind, Chat Button





