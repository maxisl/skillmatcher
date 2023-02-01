package com.example.skillmatcher.views

import android.util.Size
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.chargemap.compose.numberpicker.NumberPicker
import com.example.skillmatcher.HeadBar
import com.example.skillmatcher.OwnProjectOverviewPage
import com.example.skillmatcher.api.attendProject
import com.example.skillmatcher.api.getAttendees
import com.example.skillmatcher.api.leaveProject
import com.example.skillmatcher.data.User
import com.example.skillmatcher.destinations.AllProjectsListPageDestination
import com.example.skillmatcher.destinations.OwnProjectOverviewPageDestination
import com.example.skillmatcher.ui.theme.Black
import com.example.skillmatcher.ui.theme.LMUGreen
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
fun SelectProject(navigator: DestinationsNavigator?) {
    // empty response variable that will hold user list after successful API call
    val response = remember {
        mutableStateOf(listOf(User(0, "", mutableListOf(), mutableListOf(), "")))
    }

    // call API and get response
    // TODO projectId is hardcoded, should dynamically be set to project you want to get attendees of
    getAttendees(response, 3)

    // create new variable that contains API response as List<User>
    val userList = response.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(Black.value))
    ) {
        Text("Attendees of project with id 3: ")
        for (user in userList){
            Text(text = user.email)
    }

        /**HeadBar(
        name = "Choose Project", modifier = Modifier
        .padding(5.dp)
        )*/
        //val projects= textInput()

        Spacer(modifier = Modifier.height(4.dp))
        dropDownMenu(navigator)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun dropDownMenu(navigator: DestinationsNavigator?) {
    var expanded by remember { mutableStateOf(false) }
    var list = listOf("Kotlin", "Java", "Phyton", "C++")
    var selectedItem by remember {
        mutableStateOf("")
    }
    var textFieldSize by remember { mutableStateOf(androidx.compose.ui.geometry.Size.Zero) }
    val icon = if (expanded) {
        Icons.Filled.KeyboardArrowUp

    } else {
        Icons.Filled.KeyboardArrowDown
    }
    Column(modifier = Modifier.padding(20.dp)) {
        OutlinedTextField(
            value = selectedItem, onValueChange = { selectedItem = it },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    textFieldSize = coordinates.size.toSize()
                },

            label = { Text(text = "Select Project...", color = Color.White) },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(LMUGreen.value),
                unfocusedBorderColor = Color.White
            ),
            trailingIcon = {
                Icon(
                    icon, "", Modifier.clickable { expanded = !expanded },
                    tint = Color.White
                )

            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.width(with(LocalDensity.current) { textFieldSize.width.toDp() })
        ) {
            /*list.forEach{label ->
                androidx.compose.material.DropdownMenuItem(onClick = {
                    selectedItem= label
                    expanded= false
                    navigator?.navigate(OwnProjectOverviewPageDestination())}) {
                   Text(text=label)
                }
            }*/
        }
    }
}