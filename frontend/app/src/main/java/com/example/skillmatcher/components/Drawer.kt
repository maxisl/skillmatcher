package com.example.skillmatcher.components

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.material.Text
import com.example.skillmatcher.*
import com.example.skillmatcher.R
import com.example.skillmatcher.views.ProjectCreationPage
import com.ramcosta.composedestinations.DestinationsNavHost

// TODO: Routen hier festlegen
@Composable
fun NavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = ScreensRoute.SCREEN_1.name
    ) {
        composable(ScreensRoute.SCREEN_1.name) {
            DestinationsNavHost(navGraph = NavGraphs.root)
        }
        composable(ScreensRoute.SCREEN_2.name) {
            AllProjectsOverViewPage()
        }
        composable(ScreensRoute.SCREEN_PROFILE.name) {
            LandingPage()
        }
        composable(ScreensRoute.SCREEN_CREATE.name) {
            ProjectCreationPage()
        }
    }
}


@Composable
fun TopBar(
    titleResId: Int,
    modifier: Modifier = Modifier,
    openDrawer: () -> Unit
) {
    TopAppBar(
        title = {
            Text(text = stringResource(id = titleResId))
        },
        navigationIcon = {
            Icon(
                imageVector = Icons.Default.Menu,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .clickable {
                        openDrawer()
                    },
                contentDescription = null
            )
        },
        modifier = modifier
    )
}


@Composable
fun DrawerBody(
    menuItems: List<MenuItem>,
    scaffoldState: ScaffoldState,
    scope: CoroutineScope,
    modifier: Modifier = Modifier,
    onItemClick: (MenuItem) -> Unit
) {

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(colors = listOf(Color(0xFFF70A74), Color(0xFFF59118)))),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(vertical = 36.dp)
    ) {


        item {

            // user's image
            Image(
                modifier = Modifier
                    .size(size = 120.dp)
                    .clip(shape = CircleShape),
                painter = painterResource(id = R.drawable.android_icon),
                contentDescription = "Profile Image"
            )

            // user's name
            Text(
                modifier = Modifier
                    .padding(top = 12.dp),
                text = "Hermione",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            // user's email
            Text(
                modifier = Modifier.padding(top = 8.dp, bottom = 30.dp),
                text = "hermione@email.com",
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = Color.White
            )
        }


        items(menuItems) { item ->
            DrawerItem(
                item,
                modifier = modifier
            ) {
                scope.launch {
                    scaffoldState.drawerState.close()
                }
                onItemClick(item)
            }
        }
    }
}


@Composable
fun DrawerItem(menuItem: MenuItem, modifier: Modifier = Modifier, onItemClick: (MenuItem) -> Unit) {
    Column(
        modifier = Modifier.clickable {
            onItemClick(menuItem)
        }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .padding(8.dp)
        ) {
            // icon and unread bubble
            Box {

                Icon(
                    modifier = Modifier
                        .padding(all = if (menuItem.showUnreadBubble && menuItem.label == "Messages") 5.dp else 2.dp)
                        .size(size = if (menuItem.showUnreadBubble && menuItem.label == "Messages") 24.dp else 28.dp),
                    painter = menuItem.image,
                    contentDescription = null,
                    tint = Color.White
                )

                // unread bubble
                if (menuItem.showUnreadBubble) {
                    Box(
                        modifier = Modifier
                            .size(size = 8.dp)
                            .align(alignment = Alignment.TopEnd)
                            .background(color = Color(0xFF0FFF93), shape = CircleShape)
                    )
                }
            }

            Text(
                // text = stringResource(id = menuItem.textId),
                // fontSize = 25.sp,
                //modifier = Modifier.padding(horizontal = 10.dp)

                modifier = Modifier.padding(start = 16.dp),
                text = menuItem.label,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White,
            )
        }
        Divider()
    }
}

data class MenuItem(
    val id: ScreensRoute,

    val image: Painter,
    val label: String,
    val showUnreadBubble: Boolean = false,
)


// TODO: Add screen here
enum class ScreensRoute {
    SCREEN_1, SCREEN_2, SCREEN_PROFILE, SCREEN_CREATE;
}

// TODO: Add Item here
@Composable
fun navigationDrawerItemList(): List<MenuItem> {
    val itemsList = arrayListOf<MenuItem>()

    itemsList.add(
        MenuItem(
            image = painterResource(id = R.drawable.android_icon),
            label = "Home",
            id = ScreensRoute.SCREEN_1,
        )
    )
    itemsList.add(
        MenuItem(
            image = painterResource(id = R.drawable.android_icon),
            label = "Profile",
            showUnreadBubble = false,
            id = ScreensRoute.SCREEN_PROFILE,
        )
    )
    itemsList.add(
        MenuItem(
            image = painterResource(id = R.drawable.android_icon),
            label = "Create Project",
            showUnreadBubble = false,
            id = ScreensRoute.SCREEN_CREATE,
        )
    )
    itemsList.add(
        MenuItem(
            image = painterResource(id = R.drawable.android_icon),
            label = "All Projects",
            showUnreadBubble = true,
            id = ScreensRoute.SCREEN_2,
        )
    )

    return itemsList
}


