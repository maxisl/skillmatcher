package com.example.skillmatcher.components

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.skillmatcher.AllProjectsOverViewPage
import com.example.skillmatcher.IndividualSkillsPage
import com.example.skillmatcher.LandingPage
import com.example.skillmatcher.NavGraphs
import com.example.skillmatcher.views.ProjectCreationPage
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

// TODO: Routen hier festlegen
@Composable
fun NavHost(navController: NavHostController, navigator: DestinationsNavigator) {
    androidx.navigation.compose.NavHost(
        navController = navController,
        startDestination = ScreensRoute.SCREEN_HOME.name
    ) {
        composable(ScreensRoute.SCREEN_HOME.name) {
            IndividualSkillsPage(navigator)  //TODO: we dont have a home view jet
        }
        composable(ScreensRoute.SCREEN_ALL_PROJECTS.name) {
            AllProjectsOverViewPage()
        }
        composable(ScreensRoute.SCREEN_PROFILE.name) {
            LandingPage()
        }
        composable(ScreensRoute.SCREEN_CREATE_PROJECT.name) {
            ProjectCreationPage()
        }
    }
}