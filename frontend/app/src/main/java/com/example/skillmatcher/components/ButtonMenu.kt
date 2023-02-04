package com.example.skillmatcher.components

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.skillmatcher.LandingPage
import com.example.skillmatcher.R

@Composable
fun TopBar(){
    TopAppBar(
        title ={ Text(text="Bottom Navigation", fontSize=18.sp)},
        backgroundColor = Color.Green,
        contentColor = Color.Black
    )
}

@Composable
fun BottomNavigationBar(navController: NavController){
    val items =listOf(
        NavigationItems.List,
        NavigationItems.Add,
        NavigationItems.Profile
    )
    BottomNavigation (
        backgroundColor = Color.Green,
        contentColor = Color.Black
    ){
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute= navBackStackEntry?.destination?.route

        items.forEach{
                items ->
            BottomNavigationItem(
                icon= {Icon(painter = painterResource(id=items.icon), contentDescription = items.title)},
                label={},
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.Black.copy(0.4f),
                alwaysShowLabel = true,
                selected = currentRoute==items.route,
                onClick = {
                    navController.navigate(items.route){
                        navController.graph.startDestinationRoute?.let{
                                route -> popUpTo(route =route){
                            saveState =true
                        }
                        }
                        launchSingleTop= true
                        restoreState =true
                    }
                }
            )
        }
    }
}

@Composable
fun Navigation(navController: NavHostController){
    NavHost(navController, startDestination = NavigationItems.List.route){
        composable(NavigationItems.List.route){
            // nochmal klären mit dem destinationNavigator
        }
        composable(NavigationItems.Add.route){
            // nochmal klären mit dem destinationNavigator
        }
        composable(NavigationItems.Profile.route){

        }
    }
}

sealed class NavigationItems(var route:String, var icon: Int, var title:String){
    object List: NavigationItems("list", R.drawable.ic_list, "All Projects")
    object Add: NavigationItems("add", R.drawable.ic_new, "New Project")
    object Profile: NavigationItems("profile", R.drawable.ic_person, "Profile")
}
