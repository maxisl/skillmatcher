package com.example.skillmatcher.views

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skillmatcher.R
import com.example.skillmatcher.ui.theme.SkillMatcherTheme
import com.ramcosta.composedestinations.annotation.Destination


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Destination
@Composable
fun ProjectPage() {
    SkillMatcherTheme {
        Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
        ) {
            androidx.compose.material.Scaffold(
                    content = {
                        Row(modifier = Modifier
                                // .weight(1f)
                                .background(Color(Color.Black.value))
                                .fillMaxHeight()
                        ) {
                            Project()

                        }
                    }
            )
        }
    }
}


@Composable
private fun Project() { //Project: Projects
    Card(
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .clickable { }
    ) {
        Column(
                modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
        ) {

            Row {
                Image(
                        modifier = Modifier.size(80.dp),
                        painter = painterResource(id = R.drawable.python_icon),
                        // bitmap = ImageBitmap.imageResource(id = icon),
                        contentDescription = "Project_card"
                )

                Row(modifier = Modifier.padding(top = 4.dp, start = 10.dp)) {
                    Column(modifier = Modifier.weight(1f)) {

                        Row {
                            Column {
                                Text(
                                        text = "Title:",
                                        style = TextStyle(
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.Bold
                                        )
                                )
                            }
                            Column {
                                Text(
                                        text = "MERN Meme Generator",
                                        style = TextStyle(
                                                fontSize = 16.sp,
                                        )
                                )
                            }
                        }
                        Row {
                            Column {
                                Text(
                                        text = "Date Created:",
                                        style = TextStyle(
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.Bold
                                        )
                                )
                            }
                            Column {
                                Text(
                                        text = "10.01.2023",
                                        style = TextStyle(
                                                fontSize = 16.sp,
                                        )
                                )
                            }
                        }
                        Row {
                            Column {
                                Text(
                                        text = "Date Start:",
                                        style = TextStyle(
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.Bold
                                        )
                                )
                            }
                            Column {
                                Text(
                                        text = "20.01.2023",
                                        style = TextStyle(
                                                fontSize = 16.sp,
                                        )
                                )
                            }
                        }
                    }
                }

            }
            Row(modifier = Modifier.padding(top = 20.dp)) {
                Column(modifier = Modifier.weight(1f)) {
                    Row {
                        Text(
                                text = "Description: ",
                                style = TextStyle(
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold
                                )
                        )


                        Text(
                                text = "Build a meme Generator based on the MERN Stack. After the creation Memes should be saved on a Server. Build a meme Generator based on the MERN Stack. After the creation Memes should be saved on a Server. Build a meme Generator based on the MERN Stack. After the creation Memes should be saved on a Server.",
                                style = TextStyle(
                                        fontSize = 16.sp,
                                )
                        )

                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                            text = "Skills Needed:",
                            style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                            )
                    )
                    Text(
                            text = "Express (JS), React (JS), MongoDB",
                            style = TextStyle(
                                    fontSize = 16.sp,
                            )
                    )

                    Spacer(modifier = Modifier.height(14.dp))


                    Text(
                            text = "Attendees: (6 von 10)",
                            style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                            )
                    )
                    Text(
                            text = "- Peter Mayer (Express)",
                            style = TextStyle(
                                    fontSize = 16.sp,
                            )
                    )
                    Text(
                            text = "- Lisa Huber (React)",
                            style = TextStyle(
                                    fontSize = 16.sp,
                            )
                    )
                    Text(
                            text = "- Markus Mayer (React)",
                            style = TextStyle(
                                    fontSize = 16.sp,
                            )
                    )
                    Text(
                            text = "- Peter Mayer (Express)",
                            style = TextStyle(
                                    fontSize = 16.sp,
                            )
                    )
                    Text(
                            text = "- Lisa Huber (React)",
                            style = TextStyle(
                                    fontSize = 16.sp,
                            )
                    )
                    Text(
                            text = "- Markus Mayer (React)",
                            style = TextStyle(
                                    fontSize = 16.sp,
                            )
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                        Column {
                            Button(
                                    onClick = {
                                        //your onclick code
                                    },
                                    border = BorderStroke(1.dp, Color.White),
                                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Green)
                            ) {
                                Text(text = "Attend Project", color = Color.White,
                                        style = TextStyle(
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.Bold
                                        )
                                )
                            }
                        }
                        Column {
                            Button(
                                    onClick = {
                                        //your onclick code
                                    },
                                    border = BorderStroke(1.dp, Color.White),
                                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Green)
                            ) {
                                Text(text = "Mark Project", color = Color.White, style = TextStyle(
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold
                                ))
                            }
                        }
                    }


                }
            }
        }
    }
}
