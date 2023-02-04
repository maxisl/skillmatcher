package com.example.skillmatcher.components

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.skillmatcher.*
import com.example.skillmatcher.data.Project
import com.example.skillmatcher.data.User
import com.example.skillmatcher.views.IndividualSkillsPage
import com.example.skillmatcher.views.ProjectCreationPage
import com.example.skillmatcher.views.ProjectPage
import com.example.skillmatcher.views.SelectProject
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

// TODO: Routen hier festlegen
@Composable
fun NavHost(navController: NavHostController, navigator: DestinationsNavigator
) {
    androidx.navigation.compose.NavHost(
        navController = navController,
        startDestination = ScreensRoute.SCREEN_HOME.name
    ) {
        composable(ScreensRoute.SCREEN_HOME.name) {
            HomePage(navigator)
        }
        composable(ScreensRoute.SCREEN_ALL_PROJECTS.name) {
            AllProjectsListPage(navigator)
        }
        composable(ScreensRoute.SCREEN_PROFILE.name) {
            LandingPage(navigator)
        }
        composable(ScreensRoute.SCREEN_CREATE_PROJECT.name) {
            ProjectCreationPage()
        }
        composable(ScreensRoute.TEST.name) {
            ProjectPage()
        }
        composable(ScreensRoute.SCREEN_SELECT_PROJECT.name) {
            SelectProject(navigator = navigator)
        }
    }
}