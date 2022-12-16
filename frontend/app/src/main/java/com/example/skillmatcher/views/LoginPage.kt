package com.example.skillmatcher

import androidx.compose.foundation.background
import androidx.compose.material.Scaffold
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.skillmatcher.destinations.IndividualSkillsPageDestination
import com.example.skillmatcher.ui.theme.Grey10
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import java.time.LocalDateTime
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.skillmatcher.data.User
import com.example.skillmatcher.destinations.SideBarDestination
import com.example.skillmatcher.ui.theme.Green30
import com.example.skillmatcher.ui.theme.SecondSidebarColor

@OptIn(ExperimentalMaterial3Api::class)
@RootNavGraph(start = true)
@Destination()
@Composable
fun LoginPage(navigator: DestinationsNavigator) {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {

        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                    Text(text = "Sign In")
                            },
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        titleContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
               )
            },
            content = {
                Column(
                    modifier = Modifier.fillMaxSize()
                        .background(brush = Brush.verticalGradient(colors = listOf(
                            Color(Green30.value), Color(
                                SecondSidebarColor.value)
                        ))),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Spacer(modifier = Modifier.height(4.dp))
                    val image: Painter = painterResource(id = R.drawable.lmulogo)
                    Image(painter = image, contentDescription = "", contentScale = ContentScale.FillBounds)

                    Spacer(modifier = Modifier.height(4.dp))
                    // to change color : colors = ButtonDefaults.buttonColors(Grey10)
                    // colors = ButtonDefaults.textButtonColors(Red30, Grey99) Second Color is the content Color
                    // In my case i define the Text Color by Text()
                    Button(onClick = {
                        navigator.navigate(
                            SideBarDestination(id = 1,
                                User(
                                    name = "Chris P. Bacon",
                                    id = "userid",
                                    created = LocalDateTime.now()
                                )
                            )
                        )
                    }) {
                        // define here Text-Color  color = Grey99
                        Text("Go to IndividualSkillsPage")
                    }
                }
            }
        )
    }
}

@Preview
@Composable
fun Welcome(){
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Grey10),
        color = MaterialTheme.colorScheme.background
    ) {
    }
}