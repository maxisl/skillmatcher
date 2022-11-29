package com.example.skillmatcher
//All classes
sealed class Screen(val route: String){
    object UserLoginScreen : Screen("user_login_screen")
    object ProjectCreation : Screen("project_creation_screen")


    fun withArgs(vararg args: String): String{
        return buildString {
            append(route)
            args.forEach{ arg ->
                append("/$arg")
            }
        }
    }
}
