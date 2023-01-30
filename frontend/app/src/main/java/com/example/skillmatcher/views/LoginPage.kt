package com.example.skillmatcher.views

// import com.example.skillmatcher.destinations.SideBarDestination

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skillmatcher.api.*
import com.example.skillmatcher.destinations.RegisterPageDestination
import com.example.skillmatcher.destinations.SideBarDestination
import com.example.skillmatcher.ui.theme.*
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.models.Channel
import java.util.*

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
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                postLoginUserData(
                    ctx, userName, job, response
                )
                navigator.navigate(
                    SideBarDestination(
                        id = 1,
                    )
                )

                val client = ChatClient.instance()

                val uname= userName.value.text
                Log.d("username", uname)
                val uname2= uname.replace(".", "")
                Log.d("username2", uname2)
               //val uname3 =uname2.replace("@", "")
                val user = io.getstream.chat.android.client.models.User(

                    id = uname2,
                    role= "admin",
                    name = userName.value.text,
                    image = "https://bit.ly/321RmWb",
                )

                client.updateUser(user)
                val token1= client.devToken(user.id)

                client.connectUser(
                    user = user,
                    token = token1
                ).enqueue { result ->
                    if (result.isSuccess) {
                        Log.d("Successful", "Successful")
                    } else {
                        Log.d("fail", "fail")
                    }
                }


                val channelClient = client.channel(channelType = "messaging", channelId = "NewId")

                channelClient.create(memberIds = listOf(user.id), extraData = emptyMap()).enqueue { result ->
                    if (result.isSuccess) {
                        val newChannel: Channel = result.data()

                        //Log.d("newChannel",newChannel)
                        Log.d("chanelle", "channel wurde erstellt")
                    } else {
                        Log.d("channel", "channel fail")
                    }
                }

                //channelClient.watch().enqueue()
                /**channelClient.addMembers(listOf("lmu")).enqueue { result ->
                    if (result.isSuccess) {
                        val channel: Channel = result.data()
                    } else {
                        // Handle result.error()
                    }
                }*/
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Login", modifier = Modifier.padding(8.dp))
        }

        Button(
            onClick = {
                registerUser(ctx,userName.value.text, job.value.text, response)
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