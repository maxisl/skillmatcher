package com.example.skillmatcher.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.skillmatcher.R
import com.example.skillmatcher.ui.theme.SkillMatcherTheme

sealed class DrawerScreens(val title: String, val route: String) {
    object Home : DrawerScreens("Home", "home")
    object CreateProject : DrawerScreens("Create Project", "Create Project")
    object Help : DrawerScreens( "Help", "help")
}

private val screens = listOf(
    DrawerScreens.Home,
    DrawerScreens.CreateProject,
    DrawerScreens.Help
)

@Composable
fun Drawer(
    modifier: Modifier = Modifier,
    onDestinationClicked: (route: String) -> Unit
) {
    Column(
        modifier
            .fillMaxSize()
            .padding(start = 24.dp, top = 48.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.android_icon),
            contentDescription = "App icon"
        )
        screens.forEach { screen ->
            Spacer(Modifier.height(24.dp))
            Text(
                text = screen.title,
                style = MaterialTheme.typography.h4,
                modifier = Modifier.clickable {
                    onDestinationClicked(screen.route)
                }
            )
        }
    }
}

@Preview
@Composable
fun DrawerPreview() {
    SkillMatcherTheme {
        Drawer{}
    }
}