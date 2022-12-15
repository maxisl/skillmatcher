package com.example.skillmatcher


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.skillmatcher.ui.theme.SkillMatcherTheme
import kotlinx.coroutines.launch

import com.example.skillmatcher.components.*
import com.ramcosta.composedestinations.DestinationsNavHost

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
            SkillMatcherTheme() {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
    }
}