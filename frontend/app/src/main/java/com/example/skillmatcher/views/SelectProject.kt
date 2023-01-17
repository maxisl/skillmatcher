package com.example.skillmatcher.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chargemap.compose.numberpicker.NumberPicker
import com.example.skillmatcher.HeadBar
import com.example.skillmatcher.ui.theme.Black
import com.example.skillmatcher.ui.theme.LMUGreen

@Preview
@Composable
fun SelectProject(){

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(Black.value))
        ) {
            HeadBar(
                name = "Select Project", modifier = Modifier
                    .padding(5.dp)
            )
            //val projects= textInput()
        }

}

/**@Composable
fun textInput(): Int {
    var pickerValue by remember { mutableStateOf(null) }
    Row(){
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Text("Max. Attendees:", textAlign = TextAlign.Center)

        }
        Column(
        ) {

            NumberPicker(
                value = pickerValue,
                textStyle = TextStyle(Color.White),
                dividersColor = LMUGreen,
                range = 0..10,
                onValueChange = {
                    pickerValue = it
                }
            )
            //WheelTextPicker(texts = (1..100).map { it.toString() }, size = DpSize(70.dp, 70.dp),)
        }
    }
    return pickerValue
}*/

