package com.example.skillmatcher.views

import android.content.Context
import android.content.res.Resources.NotFoundException
import android.util.Log
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
import com.example.skillmatcher.api.addSkillToUser
import com.example.skillmatcher.api.getAvailableSkills
import com.example.skillmatcher.api.registerUser
import com.example.skillmatcher.data.Skill
import com.example.skillmatcher.data.SkillModel
import com.example.skillmatcher.ui.theme.LMUGreen
import com.ramcosta.composedestinations.annotation.Destination

var selectedSkills = mutableListOf<Skill>()

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

    val registerResponse = remember {
        mutableStateOf("")
    }

    val response = remember {
        mutableStateOf(listOf(Skill(null, "")))
    }

    getAvailableSkills(ctx, response)

    val skills = response.value

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

                // var profilDescription = projectDescription()

                Spacer(modifier = Modifier.height(5.dp))

                createSkillCards(skills)
                RegisterUser(ctx, userName, pw, selectedSkills, registerResponse)
            }

        }
    }
}

@Composable
fun createSkillCards(listOfSkills: List<Skill>) {
    LazyRow {
        for (skill in listOfSkills) {
            item {
                drawSkill(skill)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun drawSkill(skill: Skill): SkillModel? {
    val skillTextField = remember { mutableStateOf(TextFieldValue()) }
    val context = LocalContext.current
    var selected by remember { mutableStateOf(false) }
    val color = if (selected) LMUGreen else Color.Gray
    val skillName = skill.name

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
                    if (selected) {
                        selectedSkills.add(skill)
                    } else {
                        selectedSkills.remove(skill)
                    }
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
    var skillValue: String = skillTextField.value.toString();
    try {
        return SkillModel(skillName, skillValue.toInt())
    } catch (e: NumberFormatException) {
        return null;
    }
}

// TODO add image
@Composable
fun RegisterUser(
    ctx: Context,
    userName: MutableState<TextFieldValue>,
    job: MutableState<TextFieldValue>,
    selectedSkills: MutableList<Skill>,
    registerResponse: MutableState<String>
) {
    Button(
        onClick = {
            val skillList = selectedSkills.map { it.id }
            Log.d("addSkillsToUser", "Skill ID List: $skillList")
            registerUser(ctx, userName, job, registerResponse)

            addSkillToUser(userName.value.text, skillList as List<Long>)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = "Register", modifier = Modifier.padding(8.dp))
    }
}


