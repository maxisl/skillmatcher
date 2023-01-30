package com.example.skillmatcher.views

import android.annotation.SuppressLint
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.skillmatcher.data.User
import com.example.skillmatcher.destinations.RegisterPageDestination
import com.example.skillmatcher.destinations.SideBarDestination
import com.example.skillmatcher.ui.theme.*
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.models.Channel
import java.util.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
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
                val userLogin = userResponse.value
                navigator.navigate(
                    SideBarDestination(id = 1,userLogin)
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