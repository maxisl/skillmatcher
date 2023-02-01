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

import androidx.compose.foundation.Image
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.ui.res.painterResource

@Preview
@Destination
@Composable
fun AllProjectsOverViewPage(
) {

    LazyColumn(
        modifier = Modifier.fillMaxSize()
            .background(Color(Color.Black.value)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            SlidingBanner()
        }
        item {
            CategoryView()
        }
        item {
            Row(
                modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Popular Projects",
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
            PopularFlowersList()
        }
        item {
            Row(
                modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 20.dp),
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
            PopularFlowersList()
        }
    }
}
@Composable
private fun SlidingBanner() {
    Text(
        text = "",
        style = TextStyle(
            //color = colorPrimary,
            fontSize = 16.sp,
            //fontFamily = FontFamily(Font(R.font.helvetica_neue_medium))
        )
    )


}

@Composable
private fun CategoryView() {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(20.dp)) {
        RoundedCornerIconButton(
            modifier = Modifier.weight(1f),
            1
        )
        Spacer(modifier = Modifier.size(10.dp))
        RoundedCornerIconButton(
            modifier = Modifier.weight(1f),
            2
        )
        Spacer(modifier = Modifier.size(10.dp))
        RoundedCornerIconButton(
            modifier = Modifier.weight(1f),
            3
        )
        Spacer(modifier = Modifier.size(10.dp))
        RoundedCornerIconButton(
            modifier = Modifier.weight(1f),
            4
        )
    }
}

@Composable
fun RoundedCornerIconButton(modifier: Modifier, icon: Int) {
    Box {
        IconButton(
            onClick = { }, modifier = Modifier
                .align(Alignment.Center)
                .padding(14.dp)
        ) {
            Image(
                //bitmap = ImageBitmap.imageResource(id = icon),
                painter = painterResource(id = R.drawable.lmulogo),
                contentDescription = "rounded_corner_icon_button"
            )
        }
    }
}

@Composable
private fun PopularFlowersList() {
    LazyRow(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(10) {
            FlowerCard()
        }
    }
}

@Composable
private fun FlowerCard() { //flower: Flowers
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
                painter = painterResource(id = R.drawable.android_icon),
                // bitmap = ImageBitmap.imageResource(id = icon),
                contentDescription = "flower_card"
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
                        contentDescription = "flower_card_icon"
                    )
                }
            }
        }
    }
}
