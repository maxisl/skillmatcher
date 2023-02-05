package com.example.skillmatcher.views

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
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
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skillmatcher.api.addSkillToUser
import com.example.skillmatcher.api.getAvailableSkills
import com.example.skillmatcher.api.getUser
import com.example.skillmatcher.api.registerUser
import com.example.skillmatcher.data.InputCheck
import com.example.skillmatcher.data.Skill
import com.example.skillmatcher.data.User
import com.example.skillmatcher.destinations.LoginPageDestination
import com.example.skillmatcher.destinations.SideBarDestination
import com.example.skillmatcher.ui.theme.LMUGreen
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.getstream.chat.android.client.ChatClient


@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun RegisterPage(navigator: DestinationsNavigator) {
    val ctx = LocalContext.current

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
        mutableStateOf(listOf(Skill(0, "", 0, false)))
    }

    val result = remember {
        mutableStateOf("")
    }

    var isWrongMail by rememberSaveable { mutableStateOf(false) }
    var isSomethingWrong by rememberSaveable { mutableStateOf(false) }

    var listOfSelectedSkills = remember { mutableListOf<Skill?>() }

    var passwordVisibility by remember { mutableStateOf(false) }
    var passwordSecondVisibility by remember { mutableStateOf(false) }

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
                    onValueChange = {
                        eMail.value = it
                        isWrongMail
                    },
                    placeholder = { Text(text = "Enter Email") },
                    modifier = Modifier
                        .padding(16.dp),
                    singleLine = true,
                    trailingIcon = {
                        if (isWrongMail)
                            Icon(Icons.Filled.Warning, "error", tint = Color.Red)
                    },
                    keyboardActions = KeyboardActions {
                        isWrongMail = validateEmail(eMail.value.text)
                    },
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
                    visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                    modifier = Modifier
                        .padding(16.dp),
                    singleLine = true,
                    trailingIcon = {
                        val image = if (passwordVisibility)
                            Icons.Filled.Visibility
                        else Icons.Filled.VisibilityOff

                        val description =
                            if (passwordVisibility) "Hide password" else "Show password"

                        IconButton(onClick = {
                            passwordVisibility = !passwordVisibility
                        }) {
                            Icon(imageVector = image, description)
                        }
                    }
                )

                Spacer(modifier = Modifier.height(5.dp))

                OutlinedTextField(
                    value = pwSecond.value,
                    onValueChange = { pwSecond.value = it },
                    placeholder = { Text(text = "Repeat Password") },
                    visualTransformation = if (passwordSecondVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                    modifier = Modifier
                        .padding(16.dp),
                    singleLine = true,
                    trailingIcon = {
                        val image = if (passwordSecondVisibility)
                            Icons.Filled.Visibility
                        else Icons.Filled.VisibilityOff

                        val description =
                            if (passwordSecondVisibility) "Hide password" else "Show password"

                        IconButton(onClick = {
                            passwordSecondVisibility = !passwordSecondVisibility
                        }) {
                            Icon(imageVector = image, description)
                        }
                    },

                    )

                Spacer(modifier = Modifier.height(5.dp))

                var profileDescription = projectDescription()

                Spacer(modifier = Modifier.height(5.dp))

                createSKillCards(listOfSelectedSkills = listOfSelectedSkills, ctx, response)

                registerUserButton(
                    interactionSource = interactionSource,
                    eMail.value.text,
                    pw.value.text,
                    pwSecond.value.text,
                    profileDescription,
                    listOfSelectedSkills,
                    profileImage,
                    result,
                    ctx,
                    navigator
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
                    eMail.value.text,
                    pw.value.text, pwSecond.value.text, listOfSelectedSkills
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
    }
}

@Composable
fun createSKillCards(
    listOfSelectedSkills: MutableList<Skill?>,
    ctx: Context,
    response: MutableState<List<Skill>>
): MutableList<Skill?> {

    val listOfSkills = getSkills(ctx, response)
    Log.d("CreateProjectPage", "List of skills: $listOfSkills")
    Column() {
        LazyRow() {
            listOfSkills.iterator().forEach { skill ->
                item() {
                    var selectedSkill = drawSkill(skill)
                    if (selectedSkill != null) {
                        if (selectedSkill.isSelected and !isSkillAlreadySelected(
                                listOfSelectedSkills, selectedSkill
                            )
                        ) {
                            listOfSelectedSkills.add(selectedSkill)
                        } else if (!selectedSkill.isSelected and isSkillAlreadySelected(
                                listOfSelectedSkills,
                                selectedSkill
                            )
                        ) {
                            listOfSelectedSkills.removeAt(
                                returnSelectedSkillPosition(
                                    listOfSelectedSkills,
                                    selectedSkill
                                )
                            )
                        }
                    }
                }
            }
        }
    }
    Log.d("CreateProjectPage", "Current List of Skills: $listOfSelectedSkills")
    return listOfSelectedSkills
}


fun isSkillAlreadySelected(listOfSkills: MutableList<Skill?>, selectedSkill: Skill): Boolean {
    for (i in listOfSkills.indices) {
        if (listOfSkills[i]!!.name.equals(selectedSkill.name)) {
            return true
        }
    }
    return false
}

fun returnSelectedSkillPosition(
    listOfSelectedSkills: MutableList<Skill?>,
    selectedSkill: Skill
): Int {
    var position: Int = 0
    for (i in listOfSelectedSkills.indices) {
        if (listOfSelectedSkills[i]!!.name.equals(selectedSkill.name)) {
            position = i
        }
    }
    return position
}

fun getSkills(ctx: Context, response: MutableState<List<Skill>>): List<Skill> {
    //do API call and give list of skills back
    getAvailableSkills(ctx, response)
    val skills = response.value
    return skills
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun drawSkill(skill: Skill): Skill? {
    val skillTextField = remember { mutableStateOf(TextFieldValue()) }
    val context = LocalContext.current
    var selected by remember { mutableStateOf(false) }
    val color = if (selected) LMUGreen else Color.Gray
    val skillName = skill.name
    val skillId = skill.id
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
                    text = skillName,
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
    var skillValue: String = skillTextField.value.text;
    try {
        if (skillValue.isEmpty())
            skillValue = "0"
        return Skill(skillId, skillName, skillValue.toInt(), selected)
    } catch (e: NumberFormatException) {
        return null;
    }
}

@Composable
fun registerUserButton(
    interactionSource: MutableInteractionSource,
    eMail: String, pw: String, pwSecond: String,
    profileDescription: String,
    selectedSkills: MutableList<Skill?>,
    profileImage: String,
    result: MutableState<String>,
    ctx: Context,
    navigator: DestinationsNavigator
) {
    var error by remember { mutableStateOf(false) }
    val userResponse = remember {
        mutableStateOf(User(0, "", mutableListOf(), mutableListOf(), null))
    }

    Button(interactionSource = interactionSource, onClick = {

        var errorNotifications: InputCheck =
            checkIfInputIsCorrect(eMail, pw, pwSecond, selectedSkills)
        error = errorNotifications.error
        if (!error) {
            createUser(eMail, pw, profileDescription, selectedSkills, profileImage, result, ctx)
            navigator.navigate(
                LoginPageDestination()
            )
        }
    }) {
        Text(text = "Register")
        if (error) {
            Icon(Icons.Filled.Warning, "error", tint = Color.Red)
        }
    }
}

private fun checkIfInputIsCorrect(
    eMail: String, pw: String, pwSecond: String,
    selectedSkills: MutableList<Skill?>,
): InputCheck {

    var error: Boolean = false
    val notifications = mutableListOf<String>()
    var errorNotifications: InputCheck

    if (eMail.isEmpty() or !android.util.Patterns.EMAIL_ADDRESS.matcher(eMail).matches()) {
        error = true
        notifications.add("Please ad a E-Mail to your profile")
    }
    if (pw.isEmpty()) {
        error = true
        notifications.add("Please ad a Password to your profile")
    }
    if (pwSecond.isEmpty() or (pw != pwSecond)) {
        error = true
        notifications.add("Password is not identical")
    }
    if (selectedSkills.size < 1) {
        error = true
        notifications.add("Please ad at least one Skill to your profile")
    }

    if (error) {
        errorNotifications = InputCheck(error, notifications)
    } else {
        errorNotifications = InputCheck(false, notifications)
    }

    return errorNotifications
}

fun createUser(
    eMail: String,
    pw: String,
    profileDescription: String,
    selectedSkills: MutableList<Skill?>,
    profileImage: String?,
    result: MutableState<String>,
    ctx: Context
) {


    registerUser(ctx, eMail, pw, profileImage, result) //Todo: restliche values hinzufügen

    val skillIdList: List<Long> = selectedSkills.map { it?.id ?: 0 }

    addSkillToUser(eMail, skillIdList)
    //User hinzufügen in Stream.io nach dem ein User erstellt wurde

    val client = ChatClient.instance()

    val uname = eMail
    Log.d("username", uname)
    val uname2 = uname.replace(".", "")
    Log.d("username2", uname2)
    //val uname3 =uname2.replace("@", "")
    val user = io.getstream.chat.android.client.models.User(

        id = uname2,
        role = "admin",
        name = eMail,
        image = "https://bit.ly/321RmWb",
    )

    client.updateUser(user)
    val token1 = client.devToken(user.id)

    client.connectUser(
        user = user,
        token = token1
    ).enqueue { result ->
        if (result.isSuccess) {
            Log.d("Successful", "Successful")
        } else {
            Log.d("fail", "fail")
        }
    }

}

fun validateEmail(email: String): Boolean {

    return !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}




