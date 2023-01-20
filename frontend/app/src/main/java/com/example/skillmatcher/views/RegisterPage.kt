package com.example.skillmatcher.views

import android.graphics.Bitmap
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.*
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

    val interactionSource = remember { MutableInteractionSource() }
    val interactions = remember { mutableStateListOf<Interaction>() }

    val eMail = remember {
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
    var isSomethingWrong by rememberSaveable  { mutableStateOf(false)}

    var listOfSelectedSkills = remember{mutableListOf<Skill?>()}


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
                    keyboardActions = KeyboardActions { isWrongMail = validateEmail(eMail.value.text) },
                    )
                if (isWrongMail) {
                    Text(
                        text = "please type in a correct e-mail adress",
                        color = Color.Red,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }

                Spacer(modifier = Modifier.height(5.dp))

                /*OutlinedTextField(
                    value = userName.value,
                    onValueChange = { userName.value = it },
                    placeholder = { Text(text = "Enter Username") },
                    modifier = Modifier
                        .padding(16.dp),
                    singleLine = true,
                )*/

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

                createSKillCards(listOfSelectedSkills = listOfSelectedSkills)

                registerUserButton(interactionSource = interactionSource,eMail.value.text,
                    pw.value.text,pwSecond.value.text,profileDescription, listOfSelectedSkills
                    ,profileImage,response)

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
                var errorNotifications: InputCheck = checkIfInputIsCorrect(eMail.value.text,
                    pw.value.text,pwSecond.value.text, listOfSelectedSkills)
                if(lastInteraction.equals("Pressed") and errorNotifications.error){
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
    }
}


@Composable
fun createSKillCards(listOfSelectedSkills: MutableList<Skill?>): MutableList<Skill?> {

    var selectedSkill:Skill?
    val listOfSkills = getSkills()
    Column() {
        LazyRow() {
            listOfSkills.iterator().forEach { skill ->
                item() {
                    selectedSkill = drawSkill(skill.name)
                    if (selectedSkill != null) {
                        if (selectedSkill!!.isSelected and !isSkillAlreadySelected(
                                listOfSelectedSkills, selectedSkill!!
                            )
                        ) {
                            listOfSelectedSkills.add(selectedSkill)
                        } else if (!selectedSkill!!.isSelected and isSkillAlreadySelected(
                                listOfSelectedSkills,
                                selectedSkill!!
                            )
                        ) {
                            listOfSelectedSkills.removeAt(
                                returnSelectedSkillPosition(
                                    listOfSelectedSkills,
                                    selectedSkill!!
                                )
                            )
                        }
                    }
                }
            }
        }
    }
    return listOfSelectedSkills
}

fun isSkillAlreadySelected(listOfSkills: MutableList<Skill?>, selectedSkill: Skill):Boolean{
    for(i in listOfSkills.indices){
        if(listOfSkills[i]!!.name.equals(selectedSkill.name)){
           return true
        }
    }
    return false
}

fun returnSelectedSkillPosition(listOfSelectedSkills: MutableList<Skill?>, selectedSkill: Skill): Int {
    var position: Int = 0
    for(i in listOfSelectedSkills.indices){
        if(listOfSelectedSkills[i]!!.name.equals(selectedSkill.name)){
            position = i
        }
    }
    return position
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
                        .makeText(context, "selected", Toast.LENGTH_SHORT)
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
    var skillValue:String = skillTextField.value.text;
    try {return Skill(name,skillValue.toInt(),selected)}
    catch (e :NumberFormatException){
        return null;
    }
}

@Composable
fun registerUserButton(
    interactionSource: MutableInteractionSource,
    eMail: String,pw: String, pwSecond: String,
    profileDescription: String,
    selectedSkills: MutableList<Skill?>,
    profileImage: Bitmap?,
    response: MutableState<String>
){
        var error by remember { mutableStateOf(false)}

        Button(interactionSource = interactionSource,onClick = {

            var errorNotifications: InputCheck = checkIfInputIsCorrect(eMail,pw,pwSecond,selectedSkills)
            error = errorNotifications.error
            if(!error){
                createUser(eMail,pw,profileDescription,selectedSkills,profileImage,response)
            }
        }) {
            Text(text = "Register")
            if(error){
                Icon(Icons.Filled.Warning,"error", tint = Color.Red)
            }
        }
}

fun checkIfInputIsCorrect( eMail: String, pw: String, pwSecond: String,
                           selectedSkills: MutableList<Skill?>,): InputCheck {

    var error: Boolean = false
    val notifications = mutableListOf<String>()
    var errorNotifications: InputCheck

    if(eMail.isEmpty() or !android.util.Patterns.EMAIL_ADDRESS.matcher(eMail).matches()){
        error = true
        notifications.add("Please ad a E-Mail to your Profile")
    }
    if(pw.isEmpty()){
        error = true
        notifications.add("Please ad a Password to your Profile")
    }
    if(pwSecond.isEmpty() or (pw != pwSecond)){
        error = true
        notifications.add("Password is not identical")
    }
    if(selectedSkills.size < 1){
        error = true
        notifications.add("Please ad at least one Skill to your Profile")
    }

    if(error){
        errorNotifications = InputCheck(error, notifications)
    }else{
        errorNotifications = InputCheck(false, notifications)
    }

    return errorNotifications
}

fun createUser(
    eMail: String, pw: String,
    profileDescription: String,
    selectedSkills: MutableList<Skill?>,
    profileImage: Bitmap?,
    response: MutableState<String>
){

    val newUser = User(eMail,
        created = LocalDateTime.now(),pw,profileDescription,selectedSkills,profileImage)

    //registerUser(newUser.id,newUser.password,response) //Todo: restliche values hinzufügen
}

fun validateEmail(email: String):Boolean {

    return !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}




