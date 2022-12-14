package com.example.skillmatcher


import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.skillmatcher.R
import com.example.skillmatcher.ui.theme.SkillMatcherTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

import com.example.skillmatcher.components.*

/*
You can use the following code for commercial purposes with some restrictions.
Read the full license here: https://semicolonspace.com/semicolonspace-license/
For more designs with source code, visit:
https://semicolonspace.com/jetpack-compose-samples/
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SkillMatcherTheme(darkTheme = false) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
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
                                NavHost(navController = navController) // !!!!!!!!!!!!!!!!!!!!!!!!!!!!
                            }
                        },
                        floatingActionButton = {
                            //Create a floating action button in floatingActionButton parameter of scaffold
                            FloatingActionButton(

                                onClick = {
                                    //When clicked open Snackbar
                                    scope.launch {
                                        when (scaffoldState.snackbarHostState.showSnackbar(
                                            message = "Snack Bar", //Message In the snackbar
                                            actionLabel = "Dismiss"
                                        )) {
                                            SnackbarResult.Dismissed -> {
                                                //do something when snack bar is dismissed
                                            }

                                            SnackbarResult.ActionPerformed -> {
                                                //when it appears
                                            }

                                        }
                                    }
                                }) {

                                //Simple Text inside FAB
                                Text(text = "Add")
                            }
                        }
                    )
                }
            }
        }
    }
}