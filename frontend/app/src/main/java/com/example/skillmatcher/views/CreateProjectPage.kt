package com.example.skillmatcher.views

import android.app.DatePickerDialog
import android.widget.DatePicker
import android.widget.NumberPicker
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.commandiron.wheel_picker_compose.WheelTextPicker
import com.ramcosta.composedestinations.annotation.Destination
import java.util.*


@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun ProjectCreationPage() { //openDrawer: () -> Unit
    Column(modifier = Modifier
            .fillMaxSize()
            .background(Color(Color.Black.value))) {
//        TopBar(
//            title = "Home",
//            buttonIcon = Icons.Filled.Menu,
//            onButtonClicked = { openDrawer() }
//        )
        Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {

            Spacer(modifier = Modifier.height(4.dp))

            textInput()

            Spacer(modifier = Modifier.height(7.dp))

            startDate()
            endDate()

            Spacer(modifier = Modifier.height(7.dp))

            Column {
                var projectName by rememberSaveable { mutableStateOf("") }
                OutlinedTextField(
                        value = projectName,
                        onValueChange = { projectName = it },
                        label = { Text("Max. Attendees") },
                )
            }

            Spacer(modifier = Modifier.height(7.dp))

            Column {
                var projectName by rememberSaveable { mutableStateOf("") }
                OutlinedTextField(
                        value = projectName,
                        onValueChange = { projectName = it },
                        label = { Text("Skills Needed") },
                        modifier = Modifier.defaultMinSize(minHeight = 100.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                    horizontalArrangement = Arrangement.Center) {

                Button(
                        onClick = {
                            //your onclick code
                        },
                        border = BorderStroke(1.dp, Color.Green),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Green)
                ) {
                    Text(text = "Create Project", color = Color.White,
                            style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                            )
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun textInput() {
    Column()
    {
        var projectName by rememberSaveable { mutableStateOf("") }
        OutlinedTextField(
                value = projectName,
                onValueChange = { projectName = it },
                label = { Text("Project Title") }
        )
        //Text("The textfield has this text: " + textState)


        var projectDescription by rememberSaveable { mutableStateOf("") }
        OutlinedTextField(
                value = projectDescription,
                onValueChange = { projectDescription = it },
                label = { Text("Description") },
                modifier = Modifier.defaultMinSize(minHeight = 200.dp)
        )

        //Start Date
        //End Data

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun startDate() {
    // Fetching the Local Context
    val mContext = LocalContext.current

    // Declaring integer values
    // for year, month and day
    val mYear: Int
    val mMonth: Int
    val mDay: Int

    // Initializing a Calendar
    val mCalendar = Calendar.getInstance()

    // Fetching current year, month and day
    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()

    // Declaring a string value to
    // store date in string format
    val mDate = remember { mutableStateOf("") }

    // Declaring DatePickerDialog and setting
    // initial values as current values (present year, month and day)
    val mDatePickerDialog = DatePickerDialog(
            mContext,
            { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
                mDate.value = "$mDayOfMonth/${mMonth + 1}/$mYear"
            }, mYear, mMonth, mDay
    )

    Column(
    )
    {
        OutlinedTextField(
                value = "Start Date: ${mDate.value}",
                onValueChange = {},
                label = { Text("Start Date") },
                modifier = Modifier
                        .clickable { mDatePickerDialog.show() },
                enabled = false
                //  colors = TextFieldDefaults.textFieldColors(textColor = White)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun endDate() {
    // Fetching the Local Context
    val mContext = LocalContext.current

    // Declaring integer values
    // for year, month and day
    val mYear: Int
    val mMonth: Int
    val mDay: Int

    // Initializing a Calendar
    val mCalendar = Calendar.getInstance()

    // Fetching current year, month and day
    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()

    // Declaring a string value to
    // store date in string format
    val mDate = remember { mutableStateOf("") }

    // Declaring DatePickerDialog and setting
    // initial values as current values (present year, month and day)
    val mDatePickerDialog = DatePickerDialog(
            mContext,
            { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
                mDate.value = "$mDayOfMonth/${mMonth + 1}/$mYear"
            }, mYear, mMonth, mDay
    )

    Column(
    )
    {
        OutlinedTextField(
                value = "End Date: ${mDate.value}",
                onValueChange = {},
                label = { Text("End Date") },
                modifier = Modifier
                        .clickable { mDatePickerDialog.show() },
                enabled = false
        )

        NumberPicker(mContext)
    }
}

@Composable
fun numberInput() {
    Row {
        Column(
                verticalArrangement = Arrangement.Center
        ) {
            Text("Max. Attendees:", textAlign = TextAlign.Center)

        }
        Column {
            WheelTextPicker(texts = (1..100).map { it.toString() }, size = DpSize(70.dp, 70.dp))
        }
    }
}



