package com.example.skillmatcher.views

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.example.skillmatcher.api.registerUser
import com.example.skillmatcher.data.Skill
import com.example.skillmatcher.ui.theme.LMUGreen
import com.ramcosta.composedestinations.annotation.Destination


@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun RegisterPage() {
    val ctx = LocalContext.current

    val userName = remember {
        mutableStateOf(TextFieldValue())
    }

    val pw = remember {
        mutableStateOf(TextFieldValue())
    }

    val response = remember {
        mutableStateOf("")
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

                var profilImage = imagePicker()

                Spacer(modifier = Modifier.height(5.dp))

                OutlinedTextField(
                    value = userName.value,
                    onValueChange = { userName.value = it },
                    placeholder = { Text(text = "Enter Email") },
                    modifier = Modifier
                        .padding(16.dp),
                    singleLine = true,
                )

                Spacer(modifier = Modifier.height(5.dp))

                OutlinedTextField(
                    value = pw.value,
                    onValueChange = { pw.value = it },
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
                    value = pw.value,
                    onValueChange = { pw.value = it },
                    placeholder = { Text(text = "Repeat Password") },
                    modifier = Modifier
                        .padding(16.dp),
                    singleLine = true,
                )

                Spacer(modifier = Modifier.height(5.dp))

                var profilDescription = projectDescription()

                Spacer(modifier = Modifier.height(5.dp))

                createSkillCards()
                RegisterUser(ctx, userName, pw, response)
            }

        }
    }
}

@Composable
fun createSkillCards() {

    val listOfSkills = getSkills()
    LazyRow() {
        listOfSkills.iterator().forEach { skill ->
            item() {
                drawSkill(skill.name)
            }
        }
    }
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
                /* NumberPicker(
                    value = pickerValue,
                    textStyle = TextStyle(Color.White),
                    dividersColor = LMUGreen,
                    range = 0..5,
                    onValueChange = {
                        pickerValue = it
                    }
                )*/
            }

        }
    }
    var skillValue: String = skillTextField.value.toString();
    try {
        return Skill(name, skillValue.toInt())
    } catch (e: NumberFormatException) {
        return null;
    }
}

@Composable
fun RegisterUser(
    ctx: Context,
    userName: MutableState<TextFieldValue>,
    job: MutableState<TextFieldValue>,
    // TODO image
    // TODO skills
    response: MutableState<String>
) {
    Button(
        onClick = {
            registerUser(ctx, userName, job, response)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = "Register", modifier = Modifier.padding(8.dp))
    }
}


