package com.example.skillmatcher.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Colors
import androidx.compose.ui.graphics.Color
import com.example.skillmatcher.ui.theme.LMUGreen
import io.getstream.chat.android.compose.ui.messages.MessagesScreen
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.compose.ui.theme.StreamColors

//ChannelID
class MessageActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val channelId= intent.getStringExtra(KEY_CHANNEL_ID)
        if(channelId==null){
            finish()
            return
        }
        setContent{
            ChatTheme (
                //Farbe geändert vom Chat bei dem Messages
                colors= StreamColors.defaultColors().copy(
                    appBackground = Color.Black,
                    ownMessagesBackground = Color(LMUGreen.value)

                )
                    ){
                MessagesScreen(channelId = channelId,
                messageLimit = 30,
                onBackPressed={finish() }
                )
            }
        }
    }

    companion object{
        private const val KEY_CHANNEL_ID ="channelId"

        fun getIntent(context: Context, channelId:String): Intent {
            return Intent(context, MessageActivity::class.java).apply{
                putExtra(KEY_CHANNEL_ID,channelId)
            }
        }
    }
}