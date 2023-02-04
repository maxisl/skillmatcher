package com.example.skillmatcher.views

import android.app.DatePickerDialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.DatePicker
import android.widget.NumberPicker
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chargemap.compose.numberpicker.NumberPicker
import com.example.skillmatcher.api.createProject
import com.example.skillmatcher.data.InputCheck
import com.example.skillmatcher.data.Skill
import com.example.skillmatcher.ui.theme.LMUGreen
import com.ramcosta.composedestinations.annotation.Destination
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

import android.util.Base64
import android.util.Log
import com.example.skillmatcher.api.getLocalUserEmail
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.models.Channel
import java.io.ByteArrayOutputStream


@Destination
@Composable
fun ProjectCreationPage() { //openDrawer: () -> Unit
    val ctx = LocalContext.current

    var listOfSelectedSkills = remember { mutableListOf<Skill?>() }

    val interactionSource = remember { MutableInteractionSource() }
    val interactions = remember { mutableStateListOf<Interaction>() }

    val response = remember {
        mutableStateOf(listOf(Skill(0, "", 0, false)))
    }

    //var imageBitmap by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(Color.Black.value))
    ) {

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            item {
                Spacer(modifier = Modifier.height(4.dp))
                var image = imagePicker()
                Spacer(modifier = Modifier.height(4.dp))
                val name = projectName()
                Spacer(modifier = Modifier.height(7.dp))
                val description = projectDescription()

                Spacer(modifier = Modifier.height(7.dp))

                val startDate = startDate()
                val endDate = endDate()

                Spacer(modifier = Modifier.height(7.dp))

                Spacer(modifier = Modifier.height(7.dp))
                val attendees = numberInput()

                Spacer(modifier = Modifier.height(7.dp))
                createSKillCards(
                    listOfSelectedSkills = listOfSelectedSkills,
                    ctx = ctx,
                    response = response
                )

                Spacer(modifier = Modifier.height(7.dp))
                saveButton(
                    interactionSource = interactionSource,
                    name,
                    description,
                    startDate,
                    endDate,
                    attendees,
                    image,
                    listOfSelectedSkills
                )

                LaunchedEffect(interactionSource) {
                    interactionSource.interactions.collect { interaction ->
                        when (interaction) {
                            is PressInteraction.Press -> {
                                interactions.add(interaction)
                            }
                        }
                    }
                }

                val lastInteraction = when (interactions.lastOrNull()) {
                    is PressInteraction.Press -> "Pressed"
                    else -> "No state"
                }

                var errorNotifications: InputCheck = checkIfInputIsCorrect(
                    name, description,
                    startDate, endDate, attendees, listOfSelectedSkills
                )
                if (lastInteraction.equals("Pressed") and errorNotifications.error) {
                    Text(
                        text = errorNotifications.notifications.joinToString(separator = " "),
                        color = Color.Red,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold, modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
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
    val mDatePickerDialog = DatePickerDialog(
        mContext, { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
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
    Row() {
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
fun imagePicker(): String {
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val context = LocalContext.current
    var base64Image by remember { mutableStateOf("") }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            launcher.launch("image/*")
        }) {
            Text(text = "Pick image")
        }

        imageUri?.let {
            if (Build.VERSION.SDK_INT < 28) {
                val bitmap = MediaStore.Images
                    .Media.getBitmap(context.contentResolver, it)
                base64Image = bitmap.toBase64()

            } else {
                val source = ImageDecoder
                    .createSource(context.contentResolver, it)
                val bitmap = ImageDecoder.decodeBitmap(source)
                base64Image = bitmap.toBase64()
            }

            val borderWidth = 4.dp
            Image(
                bitmap = base64Image.toBitmap().asImageBitmap(),
                contentDescription = null,
                modifier = Modifier
                    .size(150.dp)
                    .border(
                        BorderStroke(borderWidth, LMUGreen),
                        CircleShape
                    )
                    .padding(borderWidth)
                    .clip(CircleShape)
            )
        }
    }
    return base64Image
}

@Composable
fun saveButton(
    interactionSource: MutableInteractionSource,
    name: String,
    description: String,
    startDate: String,
    endDate: String,
    attendees: Int,
    image: String?,
    listOfSelectedSkills: MutableList<Skill?>
) {
    val ctx = LocalContext.current
    var error by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.Center
    ) {
        Button(interactionSource = interactionSource,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            onClick = {

                var errorNotification: InputCheck = checkIfInputIsCorrect(
                    name, description,
                    startDate, endDate, attendees, listOfSelectedSkills
                )
                error = errorNotification.error

                if (!error) {
                    // duplicate to fit data type of Project (not ProjectModel)
                    //TODO: Image und Skills müssen noch übergeben werden
                    val randomIDs = listOf<Long>(1, 2)

                    /*val stream = ByteArrayOutputStream()
                    image?.compress(Bitmap.CompressFormat.PNG, 90, stream)
                    val imageByteArray = stream.toByteArray()
                    val imageBase64 = Base64.encodeToString(imageByteArray, Base64.DEFAULT)*/

                    createProject(
                        ctx,
                        name,
                        description,
                        attendees.toString(),
                        startDate,
                        endDate,
                        image,
                        randomIDs
                    )

                    val client= ChatClient.instance()
                    val channelClient = client.channel(channelType = "messaging", channelId = name)
                    val userID= getLocalUserEmail().toString()
                    Log.d("userId", userID)
                    val uname2= userID.replace(".", "")

                    channelClient.create(memberIds = listOf(uname2), extraData = emptyMap()).enqueue { result ->
                        if (result.isSuccess) {
                            val newChannel: Channel = result.data()

                            //Log.d("newChannel",newChannel)
                            Log.d("channel erstellt", "channel wurde erstellt")
                        } else {
                            Log.d("channel", "channel fail")
                        }
                    }
                }

            }) {
            Text(text = "Create Project", modifier = Modifier.padding(8.dp))
            if (error) {
                Icon(Icons.Filled.Warning, "error", tint = Color.Red)
            }
        }
    }
}

private fun checkIfInputIsCorrect(
    name: String, description: String, startDate: String,
    endDate: String, attendees: Int, listOfSelectedSkills: MutableList<Skill?>
): InputCheck {

    var error: Boolean = false
    val notifications = mutableListOf<String>()
    var errorNotifications: InputCheck

    if (name.isEmpty()) {
        error = true
        notifications.add("Please add a name to your project")
    }
    if (description.isEmpty()) {
        error = true
        notifications.add("Please add a description to your project")
    }
    if (startDate.isEmpty()) {
        error = true
        notifications.add("Please select a start date")
    }
    if (endDate.isEmpty()) {
        error = true
        notifications.add("Please select an end date")
    }
    if (startDate.isNotEmpty() and endDate.isNotEmpty()) {
        val tmpStartDate = formatStringToDate(startDate)
        val tmpEndDate = formatStringToDate(endDate)
        if (tmpStartDate.isAfter(tmpEndDate)) {
            error = true
            notifications.add("Start date has to be before end date")
        }
    }

    if (attendees < 1) {
        error = true
        notifications.add("There has to be at least one possible attendee")
    }
    if (listOfSelectedSkills.size < 1) {
        error = true
        notifications.add("Please add at least one Skill to your profile")
    }

    if (error) {
        errorNotifications = InputCheck(error, notifications)
    } else {
        errorNotifications = InputCheck(false, notifications)
    }

    return errorNotifications
}

fun formatStringToDate(dateString: String): LocalDate {
    val formatter = DateTimeFormatter.ofPattern("d/M/yyyy", Locale.ENGLISH)
    val date = LocalDate.parse(dateString, formatter)
    return date
}


// helper functions for image conversion
fun Bitmap.toBase64(): String {
    val byteArrayOutputStream = ByteArrayOutputStream()
    val scaledBitmap = Bitmap.createScaledBitmap(this, 100, 100, true)
    scaledBitmap.compress(Bitmap.CompressFormat.PNG, 25, byteArrayOutputStream)
    val byteArray = byteArrayOutputStream.toByteArray()
    return Base64.encodeToString(byteArray, Base64.DEFAULT)
}

fun String.toBitmap(): Bitmap {
    val decodedString = Base64.decode(this, Base64.DEFAULT)
    return if (decodedString.size != null) {
        BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
    } else {
        // if decoded String is null, create empty Bitmap
        Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
    }
}
