package com.example.skillmatcher.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.example.skillmatcher.R

import androidx.compose.material3.*
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold


import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu


@ExperimentalMaterial3Api
@Composable
fun TopBar(
    titleResId: Int,
    modifier: Modifier = Modifier,
    openDrawer: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            Text(text = stringResource(id = titleResId))
        },
        navigationIcon = {
            Icon(
                imageVector = Icons.Filled.Menu,
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
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(vertical = 36.dp)
    ) {


        item {

            // user's image
            Image(
                modifier = Modifier
                    .size(size = 120.dp)
                    .clip(shape = CircleShape),
                painter = painterResource(id = R.drawable.lmulogo),
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

// TODO: Add screen here
enum class ScreensRoute {
    SCREEN_HOME, SCREEN_ALL_PROJECTS, SCREEN_PROFILE, SCREEN_CREATE_PROJECT, TEST;
}

// TODO: Add Item here
@Composable
fun navigationDrawerItemList(): List<MenuItem> {
    val itemsList = arrayListOf<MenuItem>()

    itemsList.add(
        MenuItem(
            image = painterResource(id = R.drawable.android_icon),
            label = "Home",
            id = ScreensRoute.SCREEN_HOME,
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
            id = ScreensRoute.SCREEN_CREATE_PROJECT,
        )
    )
    itemsList.add(
        MenuItem(
            image = painterResource(id = R.drawable.android_icon),
            label = "All Projects",
            showUnreadBubble = false,
            id = ScreensRoute.SCREEN_ALL_PROJECTS,
        )
    )
    itemsList.add(
            MenuItem(
                    image = painterResource(id = R.drawable.android_icon),
                    label = "Test",
                    showUnreadBubble = true,
                    id = ScreensRoute.TEST,
            )
    )

    return itemsList
}


