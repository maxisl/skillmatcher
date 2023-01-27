package com.example.skillmatcher


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.skillmatcher.ui.theme.SkillMatcherTheme
import kotlinx.coroutines.launch

import com.example.skillmatcher.components.*
import com.ramcosta.composedestinations.DestinationsNavHost
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.logger.ChatLogLevel
import io.getstream.chat.android.client.models.User
import io.getstream.chat.android.offline.model.message.attachments.UploadAttachmentsNetworkType
import io.getstream.chat.android.offline.plugin.configuration.Config
import io.getstream.chat.android.offline.plugin.factory.StreamOfflinePluginFactory

/*
You can use the following code for commercial purposes with some restrictions.
Read the full license here: https://semicolonspace.com/semicolonspace-license/
For more designs with source code, visit:
https://semicolonspace.com/jetpack-compose-samples/
 */
@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SkillMatcherTheme {
                val offlinePluginFactory = StreamOfflinePluginFactory(
                    config = Config(
                        backgroundSyncEnabled = true,
                        userPresence = true,
                        persistenceEnabled = true,
                        uploadAttachmentsNetworkType = UploadAttachmentsNetworkType.NOT_ROAMING,
                    ),
                    appContext = applicationContext,
                )
                val client = ChatClient.Builder("pkbgx74wqa9s", applicationContext)
                    .withPlugin(offlinePluginFactory)
                    .logLevel(ChatLogLevel.ALL) // Set to NOTHING in prod
                    .build()


                DestinationsNavHost(navGraph = NavGraphs.root)
                val user = User(
                    id = "lmu",
                    name = "lmu",
                    image = "https://bit.ly/321RmWb",
                )

                client.connectUser(
                    user = user,
                    token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoibG11In0.sJN3rNFhAUc_f7UTeeAAtYb5_A7o9H2cgvPMN_4X-mk"
                ).enqueue()

            }
        }

    }
}

//test