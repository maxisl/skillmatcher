package com.example.skillmatcher.activity

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*


import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.skillmatcher.ui.theme.LMUGreen
import dagger.hilt.android.AndroidEntryPoint
import io.getstream.chat.android.compose.ui.channels.ChannelsScreen
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.compose.ui.theme.StreamColors

//@AndroidEntryPoint
class ChannelListActivity : ComponentActivity() {

    //val viewModel:ChannelListViewModel by viewModels()

    //Hiermit wird die ChannelList aufgerufen, welche wiederum beim klicken die MessageAchtivity aufruft
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent{
            ChatTheme (colors= StreamColors.defaultColors().copy(
                textHighEmphasis = Color(LMUGreen.value), appBackground = Color.Black,
                primaryAccent = Color.White)){
                /**var showDialog: Boolean by remember {
                    mutableStateOf(false)
                }
                if (showDialog){
                    CreateChannelDialog(
                        dismiss= {
                            channelName ->
                            viewModel.createChannel(channelName)
                            showDialog =false
                        }
                    )
                }*/
                ChannelsScreen(
                    title ="Project List",
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
                    onBackPressed = {finish()},
                   // onHeaderActionClick = {
                     //   showDialog=true
                   // }
                )

            }
        }
    }
    /**
  @Composable
    private fun CreateChannelDialog(dismiss: (String) -> Unit) {
        var channelName by remember {
            mutableStateOf("")
        }

      AlertDialog(
          onDismissRequest = { dismiss(channelName) },
          title = {
          Text(text= "Enter Channel Name")
      }, text= {
          TextField(value = channelName, onValueChange ={channelName =it} )
          },
         buttons= {
        Row(modifier= Modifier.padding(all =8.dp), 
        horizontalArrangement = Arrangement.Center) {
            Button(
                modifier= Modifier.fillMaxWidth(),
                onClick = {  dismiss(channelName)}) {
                Text(text = "Create Channel")
            }
        }
         })
    }*/

}