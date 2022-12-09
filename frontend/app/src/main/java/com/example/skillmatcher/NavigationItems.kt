package com.example.skillmatcher

sealed class NavigationItems(var route:String, var icon: Int, var title:String){
    object List: NavigationItems("list", R.drawable.ic_list, "All Projects")
    object Add: NavigationItems("add", R.drawable.ic_new, "New Project")
    object Profile: NavigationItems("profile", R.drawable.ic_person, "Profile")
}
