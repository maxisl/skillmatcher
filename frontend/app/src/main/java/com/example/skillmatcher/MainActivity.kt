package com.example.skillmatcher


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItemDefaults.contentColor
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.skillmatcher.ui.theme.SkillMatcherTheme
import com.ramcosta.composedestinations.DestinationsNavHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
                MainScreen()
                SkillMatcherTheme() {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
            //LandingPage()
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(){
    val navController= rememberNavController()

    Scaffold(
        topBar = { TopBar()},
        bottomBar = { BottomNavigationBar(navController) }
    ) {
        Navigation(navController)
    }
}

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
            LandingPage()
        }
    }
}