package com.example.skillmatcher.views

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skillmatcher.api.*
import com.example.skillmatcher.data.Project
import com.example.skillmatcher.data.Skill
import com.example.skillmatcher.data.User
import com.example.skillmatcher.destinations.RegisterPageDestination
import com.example.skillmatcher.destinations.SideBarDestination
import com.example.skillmatcher.ui.theme.*
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@RootNavGraph(start = true)
@Destination()
@Composable
fun LoginPage(navigator: DestinationsNavigator) {

    SkillMatcherTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {

            Scaffold(


                content = {
                    postData(navigator)
                    /*TODO deactivated due to error: "Multiple Destinations with 'individual_skills_page' as their route name"*/
                    /*Button(onClick = {
                        navigator.navigate(
                            SideBarDestination(
                                id = 1,
                                User(
                                    name = "Chris P. Bacon",
                                    id = "userid",
                                    created = LocalDateTime.now()
                                )
                            )
                        )
                    }) {
                        Text("Go to IndividualSkillsPage")
                    }*/

                    /**
                    Column(
                    modifier = Modifier.fillMaxSize().background(Color(Black.value)),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                    Spacer(modifier = Modifier.height(8.dp))
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
                     */


                    /**
                    Column(
                    modifier = Modifier.fillMaxSize().background(Color(Black.value)),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                    Spacer(modifier = Modifier.height(8.dp))
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
                     */
                }
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun postData(navigator: DestinationsNavigator) {
    val ctx = LocalContext.current

    val userName = remember {
        mutableStateOf(TextFieldValue())
    }
    val job = remember {
        mutableStateOf(TextFieldValue())
    }
    val response = remember {
        mutableStateOf("")
    }
    var passwordVisibility by remember { mutableStateOf(false) }

    val userResponse = remember {
        mutableStateOf(User(0,"", mutableListOf(),mutableListOf(),""))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight()
            .fillMaxWidth()
            .background((Color(Black.value))),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Login",
            fontSize = 60.sp,
            fontWeight = FontWeight.Bold, textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(15.dp))

        OutlinedTextField(
            value = userName.value,
            onValueChange = { userName.value = it },
            placeholder = { Text(text = "Enter Email") },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(5.dp))

        OutlinedTextField(
            value = job.value,
            onValueChange = { job.value = it },
            placeholder = { Text(text = "Enter Password") },
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            singleLine = true,
            trailingIcon = {
                val image = if (passwordVisibility)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                val description = if (passwordVisibility) "Hide password" else "Show password"

                IconButton(onClick = {
                    passwordVisibility = !passwordVisibility
                }) {
                    Icon(imageVector = image, description)
                }
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {

                postLoginUserData(
                    ctx, userName, job, response
                )
                suspend {
                    try {
                        getUser(userResponse)
                    } catch (e: Exception) {
                        Log.d("ExceptionInHome: ", e.toString())
                    }
                }
                val user = userResponse.value
                navigator.navigate(
                    SideBarDestination(id = 1,user)
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Login", modifier = Modifier.padding(8.dp))
        }

        Button(
            onClick = {
                // registerUser(ctx, userName.value.text, job.value.text, response)

                navigator.navigate(
                    RegisterPageDestination(

                    )
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Register", modifier = Modifier.padding(8.dp))
        }

        Text(
            text = response.value,
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold, modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(20.dp))

        // TODO remove after testing: Button to test function
        Button(
            onClick = {
                getAllUsers()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Get All Users", modifier = Modifier.padding(8.dp))
        }
    }
}