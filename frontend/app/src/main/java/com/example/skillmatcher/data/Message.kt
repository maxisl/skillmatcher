package com.example.skillmatcher.data

import java.util.Calendar
//messages we dont need this, it was more for the SQL Chat instance
data class Message(
    var text:String?=null,
    var recipient_id: String,
    var time:Long = Calendar.getInstance().timeInMillis,
    var isOut:Boolean=false
)
val message_dummy= listOf(
    Message(text="Gut",
    recipient_id = "user",
    isOut = false),
    Message(text="bad",
        recipient_id = "user",
        isOut = false)
)
