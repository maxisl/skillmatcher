package com.example.skillmatcher.components

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import com.ramcosta.composedestinations.annotation.Destination

import com.ramcosta.composedestinations.navigation.DestinationsNavigator

import androidx.compose.foundation.layout.*
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.navigation.compose.rememberNavController
import com.example.skillmatcher.R
import com.example.skillmatcher.api.getUser
import com.example.skillmatcher.ui.theme.SkillMatcherTheme
import com.example.skillmatcher.components.*
import com.example.skillmatcher.data.Project
import com.example.skillmatcher.data.User
import com.example.skillmatcher.destinations.ProjectCreationPageDestination
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun SideBar(id: Int,
            navigator: DestinationsNavigator?
) {
    val userResponse = remember {
        mutableStateOf(User(0,"", mutableListOf(),mutableListOf(),""))
    }

    val loadingResponse = remember {
        mutableStateOf(false)
    }
    var user: User = User(0,"", mutableListOf(),mutableListOf(),"")

    try{
    getUser(userResponse,loadingResponse)
    user = userResponse.value}
    catch (e :Exception){

    }

    SkillMatcherTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = androidx.compose.material3.MaterialTheme.colorScheme.background
        ) {

            val scaffoldState = rememberScaffoldState()
            val scope = rememberCoroutineScope()
            val navController = rememberNavController()


            Scaffold(
                modifier = Modifier.fillMaxSize(),
                scaffoldState = scaffoldState,
                topBar = {
                    TopBar(
                        titleResId = R.string.app_name,
                        openDrawer =
                        {
                            scope.launch {
                                // Open the drawer with animation
                                // and suspend until it is fully
                                // opened or animation has been canceled
                                scaffoldState.drawerState.open()
                            }
                        }
                    )
                },
                drawerGesturesEnabled = true,
                drawerContent = {
                    DrawerBody(
                        user,
                        menuItems = navigationDrawerItemList(),
                        scaffoldState,
                        scope
                    ) {
                        navController.navigate(it.id.name) {
                            popUpTo = navController.graph.startDestinationId
                            launchSingleTop = true
                        }
                    }
                },
                content = {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (navigator != null) {
                            NavHost(navController = navController,navigator)
                        }
                    }
                },
                floatingActionButton = {
                    //Create a floating action button in floatingActionButton parameter of scaffold
                    FloatingActionButton(

                        onClick = {
                            navigator?.navigate(ProjectCreationPageDestination())
                        }
                    ) {

                        //Simple Text inside FAB
                        Text(text = "Add")
                    }
                }
            )
        }
    }
}
