package com.example.skillmatcher.views

import android.graphics.Bitmap
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.example.skillmatcher.api.registerUser
import com.example.skillmatcher.data.InputCheck
import com.example.skillmatcher.data.Skill
import com.example.skillmatcher.data.User
import com.example.skillmatcher.ui.theme.LMUGreen
import com.ramcosta.composedestinations.annotation.Destination
import java.time.LocalDateTime


@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun RegisterPage() {

    val eMail = remember {
        mutableStateOf(TextFieldValue())
    }

    val userName = remember {
        mutableStateOf(TextFieldValue())
    }

    val pw = remember {
        mutableStateOf(TextFieldValue())
    }

    val pwSecond = remember {
        mutableStateOf(TextFieldValue())
    }

    val response = remember {
        mutableStateOf("")
    }

    var isWrongMail by rememberSaveable  { mutableStateOf(false)}

    fun validate(text: String) {
            isWrongMail = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(Color.Black.value))
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            item {
                Text(
                    text = "Register",
                    fontSize = 60.sp,
                    fontWeight = FontWeight.Bold, textAlign = TextAlign.Center,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(15.dp))

                var profileImage = imagePicker()

                Spacer(modifier = Modifier.height(5.dp))

                OutlinedTextField(
                    value = eMail.value,
                    onValueChange = { eMail.value = it
                                    isWrongMail},
                    placeholder = { Text(text = "Enter Email") },
                    modifier = Modifier
                        .padding(16.dp),
                    singleLine = true,
                    trailingIcon = {
                        if (isWrongMail)
                            Icon(Icons.Filled.Warning,"error", tint = Color.Red)
                    },
                    keyboardActions = KeyboardActions { validate(eMail.value.text) },
                    )
                if (isWrongMail) {
                    Text(
                        text = "Error message",
                        color = Color.Red,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }

                Spacer(modifier = Modifier.height(5.dp))

                OutlinedTextField(
                    value = userName.value,
                    onValueChange = { userName.value = it },
                    placeholder = { Text(text = "Enter Username") },
                    modifier = Modifier
                        .padding(16.dp),
                    singleLine = true,
                )

                Spacer(modifier = Modifier.height(5.dp))

                OutlinedTextField(
                    value = pw.value,
                    onValueChange = { pw.value = it },
                    placeholder = { Text(text = "Enter Password") },
                    modifier = Modifier
                        .padding(16.dp),
                    singleLine = true,
                )

                Spacer(modifier = Modifier.height(5.dp))

                OutlinedTextField(
                    value = pwSecond.value,
                    onValueChange = { pwSecond.value = it },
                    placeholder = { Text(text = "Repeat Password") },
                    modifier = Modifier
                        .padding(16.dp),
                    singleLine = true,
                )

                Spacer(modifier = Modifier.height(5.dp))

                var profileDescription = projectDescription()

                Spacer(modifier = Modifier.height(5.dp))

                var selectedSkills = createSKillCards()

                registerUserButton(eMail.value.text,userName.value.text,
                    pw.value.text,pwSecond.value.text,profileDescription, selectedSkills
                    ,profileImage,response)

            }

        }
    }

}



@Composable
fun createSKillCards(): MutableList<Skill?> {

    val listOfSelectedSkills = mutableListOf<Skill?>()
    var selectedSkill:Skill?
    val listOfSkills = getSkills()
    LazyRow() {
        listOfSkills.iterator().forEach { skill ->
            item() {
                selectedSkill = drawSkill(skill.name)
                 if (selectedSkill != null) {
                    listOfSelectedSkills.add(selectedSkill)
                }
            }
        }
    }
    return listOfSelectedSkills
}

fun getSkills(): MutableList<Skill> {
    //do API call and give list of skills back
    val listOfSkills = mutableListOf<Skill>()
    listOfSkills.add(Skill("Java"))
    listOfSkills.add(Skill("Python"))
    listOfSkills.add(Skill("REST"))
    listOfSkills.add(Skill("REACT"))
    listOfSkills.add(Skill("Java-Script"))


    return listOfSkills
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun drawSkill(name: String): Skill? {
    val skillTextField = remember { mutableStateOf(TextFieldValue()) }
    val context = LocalContext.current
    var selected by remember { mutableStateOf(false) }
    val color = if (selected) LMUGreen else Color.Gray

    Card(
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier
            .padding(10.dp)
            .width(180.dp)
            .clickable(MutableInteractionSource(),
                rememberRipple(bounded = true, color = Color.Black),
                onClick = {
                    selected = !selected
                    Toast
                        .makeText(context, "saved", Toast.LENGTH_SHORT)
                        .show()
                })
    ) {
        Column(modifier = Modifier.background(color)) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 5.dp)
            ) {
                Text(
                    text = name,
                    textAlign = TextAlign.Center
                )

                OutlinedTextField(
                    value = skillTextField.value,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    onValueChange = { skillTextField.value = it },
                    placeholder = { Text(text = "Level") },
                    modifier = Modifier
                        .padding(16.dp),
                    singleLine = true,
                    colors = TextFieldDefaults.textFieldColors(containerColor = Color.Black),
                    maxLines = 1,

                )
            }

        }
    }
    var skillValue:String = skillTextField.value.toString();
    try {return Skill(name,skillValue.toInt())}
    catch (e :NumberFormatException){
        return null;
    }
}

@Composable
fun registerUserButton(
    eMail: String, username: String, pw: String, pwSecond: String,
    profileDescription: String,
    selectedSkills: MutableList<Skill?>,
    profileImage: Bitmap?,
    response: MutableState<String>
){
        var error: Boolean = false
        val notifications = mutableListOf<String>()
        var errorNotifications: InputCheck? = null

        Button(onClick = {

            if(eMail.isEmpty()){
                error = true
                notifications.add("Please ad a E-Mail to your Profile")
            }
            if(username.isEmpty()){
                error = true
                notifications.add("Please ad a Username to your Profile")
            }
            if(pw.isEmpty()){
                error = true
                notifications.add("Please ad a Password to your Profile")
            }
            if(pwSecond.isEmpty() or (pw != pwSecond)){
                error = true
                notifications.add("Password is not identical to your Profile")
            }
            if(selectedSkills.size < 1){
                error = true
                notifications.add("Please ad at least one Skill to your Profile")
            }



            if(error){
                errorNotifications = InputCheck(error, notifications)
            }else{
                errorNotifications = InputCheck(false, notifications)
                createUser(eMail,username,pw,profileDescription,selectedSkills,profileImage,response)
            }

        }) {
            Text(text = "Register")
        }

}

@Composable
fun OnLifecycleEvent(onEvent: (owner: LifecycleOwner, event: Lifecycle.Event) -> Unit) {
    val eventHandler = rememberUpdatedState(onEvent)
    val lifecycleOwner = rememberUpdatedState(LocalLifecycleOwner.current)

    DisposableEffect(lifecycleOwner.value) {
        val lifecycle = lifecycleOwner.value.lifecycle
        val observer = LifecycleEventObserver { owner, event ->
            eventHandler.value(owner, event)
        }

        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }
}

@Composable
private fun errorMessage(){
    Text(
        text = "Register",
        fontSize = 60.sp,
        fontWeight = FontWeight.Bold, textAlign = TextAlign.Center,
        color = Color.White
    )
}


fun createUser(
    eMail: String, username: String, pw: String,
    profileDescription: String,
    selectedSkills: MutableList<Skill?>,
    profileImage: Bitmap?,
    response: MutableState<String>
){

    val newUser = User(username,eMail,
        created = LocalDateTime.now(),pw,profileDescription,selectedSkills,profileImage)

    //registerUser(newUser.id,newUser.password,response) //Todo: restliche values hinzufügen
}





