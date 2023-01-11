package com.example.skillmatcher.views

import android.app.DatePickerDialog
import android.util.Log
import android.widget.DatePicker
import android.widget.NumberPicker
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import com.commandiron.wheel_picker_compose.WheelTextPicker
import com.example.skillmatcher.ui.theme.White
import java.util.*
import com.chargemap.compose.numberpicker.NumberPicker
import com.example.skillmatcher.api.createProject
import com.example.skillmatcher.api.getAllProjects
import com.example.skillmatcher.data.ProjectModel
import com.example.skillmatcher.data.Project
import com.example.skillmatcher.ui.theme.LMUGreen


@Destination
@Composable
fun ProjectCreationPage( ) { //openDrawer: () -> Unit
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color(Color.Black.value))) {
//        TopBar(
//            title = "Home",
//            buttonIcon = Icons.Filled.Menu,
//            onButtonClicked = { openDrawer() }
//        )

        LazyColumn(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally){


            item{
                Spacer(modifier = Modifier.height(4.dp))
                val name = projectName()
                val description = projectDescription()

                Spacer(modifier = Modifier.height(7.dp))

                val startDate = startDate()
                val endDate = endDate()

                Spacer(modifier = Modifier.height(7.dp))

                Spacer(modifier = Modifier.height(7.dp))
                val attendees = numberInput()

                Spacer(modifier = Modifier.height(7.dp))

                // Spacer(modifier = Modifier.height(200.dp))
                saveButton(name,description,startDate,endDate,attendees)
            }
        }

            Spacer(modifier = Modifier.height(200.dp))
        }
    }



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun projectName(): String {
    var projectName by rememberSaveable { mutableStateOf("") }

    Column()
    {

        OutlinedTextField(
            value = projectName,
            onValueChange = { projectName = it },
            label = { Text("Name") }
        )
    }
    return projectName
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun projectDescription(): String {
    var projectDescription by rememberSaveable { mutableStateOf("") }

    Column()
    {
        OutlinedTextField(
            value = projectDescription,
            onValueChange = { projectDescription = it },
            label = { Text("Description") },
            modifier = Modifier.defaultMinSize(minHeight = 200.dp)
        )

        //Start Date
        //End Data

    }
    return projectDescription
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun startDate(): String {
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
    return mDate.value
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun endDate(): String {
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
    val mDatePickerDialog = DatePickerDialog(mContext, { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
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
    return mDate.value
}

@Composable
fun numberInput(): Int {
    var pickerValue by remember { mutableStateOf(0) }
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
}

@Composable
fun saveButton(
    name: String,
    description: String,
    startDate: String,
    endDate: String,
    attendees: Int
){
    val ctx = LocalContext.current
    Column(
        verticalArrangement = Arrangement.Center
    ){
        Button(
            onClick = {
                // TODO id and owner_id generated in backend?
                var id = UUID.randomUUID()
                var owner_id = UUID.randomUUID()
                var newProject = ProjectModel(id,description,attendees,name,startDate,endDate,owner_id)
                println("name: " + newProject.name + " Description: " + description + " StartDate: " + startDate + " EndDate: "
                        + endDate + " Attendees: " + attendees.toString())
                // duplicate to fit data type of Project (not ProjectModel)
                createProject(ctx, name, description, attendees.toString())
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Create Project", modifier = Modifier.padding(8.dp))
        }
    }
}



