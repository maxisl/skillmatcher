package com.example.skillmatcher.activity

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.content.ContextCompat
import io.getstream.chat.android.compose.ui.channels.ChannelsScreen
import io.getstream.chat.android.compose.ui.theme.ChatTheme

class ChannelListActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent{
            ChatTheme {
                ChannelsScreen(
                    title ="Channel List",
                    isShowingSearch = true,
                    onItemClick = {
                            channel ->
                        startActivity(
                            MessageActivity.getIntent(
                                this,
                                channelId = channel.cid
                            )
                        )

                    },
                    onBackPressed = {finish()}
                )

            }
        }
    }
}