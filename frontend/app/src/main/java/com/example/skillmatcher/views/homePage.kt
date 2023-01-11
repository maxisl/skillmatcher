package com.example.skillmatcher

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.Image
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.ui.res.painterResource
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@Destination
@Composable
fun HomePage(navigator: DestinationsNavigator
) {

    LazyColumn(
        modifier = Modifier.fillMaxSize()
            .background(Color(Color.Black.value)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item {
            Row(
                modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 20.dp, top=40.dp),
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
                Text(
                    text = "View All",
                )
            }
        }
        item {
            ProjectsListHorizontal(cardIcon=R.drawable.mern_icon)
        }



        item {
            Row(
                modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 20.dp, top=40.dp),
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
                Text(
                    text = "View All",
                )
            }
        }
        item {
            ProjectsListHorizontal(cardIcon=R.drawable.java_icon)
        }
        item {
            Row(
                    modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 20.dp, top=40.dp),
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
                Text(
                        text = "View All",
                )
            }
        }
        item {
            ProjectsListHorizontal(cardIcon=R.drawable.python_icon)
        }
    }
}


@Composable
private fun ProjectsListHorizontal(cardIcon: Int) {
    LazyRow(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(10) {
            ProjectCard(cardIcon)
        }
    }
}

@Composable
fun ProjectCard(cardIcon:Int) { //Project: Projects
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

            Image(
                modifier = Modifier.size(140.dp),
                painter = painterResource(id = cardIcon),
                // bitmap = ImageBitmap.imageResource(id = icon),
                contentDescription = "Project_card"
            )
            Row(modifier = Modifier.padding(top = 20.dp)) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Name",
                        style = TextStyle(
                            //color = gray,
                            fontSize = 16.sp,
                            //fontFamily = FontFamily(Font(R.font.helvetica_neue_medium))
                        )
                    )
                    Text(
                        text = "Beschreibung",
                        style = TextStyle(
                            //color = colorPrimary,
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
