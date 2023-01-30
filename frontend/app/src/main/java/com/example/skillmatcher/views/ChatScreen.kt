package com.example.skillmatcher.views

import android.annotation.SuppressLint
import android.media.Image
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme


import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skillmatcher.R
import com.example.skillmatcher.data.message_dummy
import java.text.SimpleDateFormat
import java.util.Locale

//we dont need this
@Composable
@Preview
fun ChatScreen(){

    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ){
        TopBarSection(
            username="User",
            profile= painterResource(id = R.drawable.nice_cat),
            isOnline=true
        )
        ChatSection(Modifier.weight(1f))
        MessageSection()
    }
}


@Composable
fun TopBarSection(username: String, profile: Painter, isOnline: Boolean) {
    Card(
        modifier= Modifier
            .fillMaxWidth()
            .height(60.dp)
    ){
        Row(modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter= profile,
                contentDescription = "profile-image",
                modifier= Modifier
                    .size(42.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))

            Column {
                Text(text=username, fontWeight= FontWeight.SemiBold)
                Text(
                    text=if(isOnline) "Online" else "Offline",
                    fontSize=12.sp
                )

            }
        }
    }
}




@Composable
fun ChatSection(modifier: Modifier=Modifier) {
    val simpleDateFormat=SimpleDateFormat("h:mm a", Locale.GERMAN)
    LazyColumn(modifier= modifier
        .fillMaxSize()
        .padding(16.dp),
    reverseLayout = true){
        items(message_dummy){
                chat -> MessageItem(
            messageText=chat.text,
            time =simpleDateFormat.format(chat.time),
            isOut=chat.isOut,
        )

            Spacer(modifier = Modifier.height(8.dp))
        }
    }

}

@Composable
fun MessageItem(messageText: String?, time: String, isOut: Boolean) {
     val AuthorChatBubbleShape = RoundedCornerShape(8.dp, 0.dp, 8.dp, 8.dp)
     val BotChatBubbleShape = RoundedCornerShape(0.dp, 8.dp, 8.dp, 8.dp)
    Column(modifier = Modifier.fillMaxWidth(),
        horizontalAlignment= if(isOut) Alignment.End else Alignment.Start ) {
        if(messageText!=null){
            if(messageText!=""){
                Box(modifier= Modifier
                    .background(
                        Color.Black,
                        shape = if (isOut) AuthorChatBubbleShape else BotChatBubbleShape
                    )
                    .padding(
                        top = 8.dp,
                        bottom = 8.dp,
                        start = 16.dp,
                        end = 16.dp
                    )
                )
                {
                    Text(
                        text=messageText,
                        color=Color.White
                    )
                    }

            }
        }
        Text(
            text= time,
        fontSize = 12.sp,
        modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun MessageSection() {
    val message = mutableStateOf("")
    val context= LocalContext.current
    Card(modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            placeholder = {
          Text(text = "Message...")
        },
            value = message.value,
            onValueChange = {
            message.value=it
            },
            shape = RoundedCornerShape(25.dp),
            trailingIcon={
                Icon(
                    painter= painterResource(id = io.getstream.chat.android.compose.R.drawable.stream_compose_ic_send),
                    contentDescription = "send-icon",
                    modifier= Modifier.clickable { }

                )
            },
            modifier= Modifier.fillMaxWidth().padding(10.dp)
        )
    }
}

