package com.example.skillmatcher.api

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.ui.text.input.TextFieldValue
import com.example.skillmatcher.data.*
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

// define an interface that represents the API we want to access => use this interface to make requests to the API
interface BackendAPI {

    /*************************************************** USER *******************************************/

    @POST("auth/login")
    // add "suspend" in front of "fun" to run in co-routine instead of main thread?
    fun loginUser(@Body userLoginModel: UserLoginModel): Call<String>?

    @POST("auth/register")
    fun registerUser(@Body userRegisterModel: UserRegisterModel): Call<User>

    // DEACTIVATED - TEST ONLY
    //@GET("excluded")
    //fun getAllUsers(): Call<List<ApiUser>>

    @GET("user")
    fun getAllUsers(@Header("Authorization") jwt: String): Call<List<ApiUser>>

    @GET("user/byMail/{email}")
    fun getUser(@Header("Authorization") jwt: String, @Path("email") email: String): Call<User>

    /*************************************************** PROJECT *******************************************/

    @POST("projects/{email}")
    fun createProject(
        @Header("Authorization") jwt: String,
        @Path("email") email: String,
        @Body project: ProjectRequest
    ): Call<Project>

    @GET("projects")
    fun getAllProjects(@Header("Authorization") jwt: String): Call<List<Project>>

    @GET("projects/byUserEmail/{email}")
    fun getProjectsByUserEmail(
        @Header("Authorization") jwt: String,
        @Path("email") email: String,
    ): Call<List<Project>>

    @GET("projects/{projectId}/requiredSkills")
    fun getRequiredSkills(
        @Header("Authorization") jwt: String,
        @Path("{projectId}") projectId: Long,
    ): Call<List<Skill>>

    // LEGACY
    /*@POST("/projects/{projectId}/requiredSkills")
    fun addRequiredSkillsToProject(
        @Header("Authorization") jwt: String,
        @Path("projectId") projectId: Long,
        @Body id: List<Long>
    ): Call<Project>*/

    /*************************************************** SKILL *******************************************/

    @GET("skill/")
    fun getAllSkills(): Call<List<Skill>>

    @POST("/user/{email}/skill/")
    fun addSkillToUser(
        @Path("email") email: String,
        @Body id: List<Long>
    ): Call<ApiUser>
}

var token = ""

private lateinit var preferencesManager: PreferencesManager

// change URL for testing - has to be http://10.0.2.2:8080/ when running local server
const val url =
    "http://10.0.2.2:8080/"
//    "http://msp-ws2223-5.dev.mobile.ifi.lmu.de:80/"


fun createRetrofitInstance(): BackendAPI {
    // enable creation of gson factory
    val gson = GsonBuilder()
        .setLenient()
        .create()

    // create a retrofit builder and pass base url
    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        // sending data in json => add Gson converter factory
        .addConverterFactory(GsonConverterFactory.create(gson))
        // build retrofit builder
        .build()
    // create a "proxy" object that implements the BackendAPI interface
    return retrofit.create(BackendAPI::class.java)
}


fun postLoginUserData(
    ctx: Context,
    userName: MutableState<TextFieldValue>,
    job: MutableState<TextFieldValue>,          // job = user password
    result: MutableState<String>
) {
    Log.d("postLoginUserData", "Executed")

    val retrofitAPI = createRetrofitInstance()

    var successfulLogin = false
    try {
        // pass data from text fields
        val userLoginModel = UserLoginModel(userName.value.text, job.value.text)
        // call a method (asynchronously) to create an update and pass model class
        val call: Call<String>? = retrofitAPI.loginUser(userLoginModel)
        // execute request asynchronously
        call!!.enqueue(object : Callback<String> {
            // call onResponse when request succeeds
            override fun onResponse(
                call: Call<String>,
                response: Response<String>
            ) {
                if (response.body() != null) {
                    successfulLogin = true
                }
                if (successfulLogin) {
                    Toast.makeText(ctx, "Logged in successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(ctx, "Error logging in", Toast.LENGTH_SHORT).show()
                }
                token = response.body().toString()

                val statusCode =
                    "HTTP-Code: " + response.code() + "\nJWT : " + token
                result.value = statusCode

                // save token and mail of successfully logged in user to EncryptedSharedPreferences
                preferencesManager = PreferencesManager(ctx)
                preferencesManager.saveJWT(token)
                if (successfulLogin) {
                    preferencesManager.saveMail(userName.value.text)
                } else {
                    preferencesManager.saveMail("")
                }

            }

            // error handling
            override fun onFailure(call: Call<String>, t: Throwable) {
                // show error response from API in UI
                result.value = "Error found is: \n" + t.message
                // show error in log
                t.message?.let { Log.d("Error: ", it) }
            }
        })

    } catch (e: Exception) {
        val error = e.printStackTrace()
        Log.d("Error stacktrace: ", error.toString())
    }

}

fun registerUser(
    ctx: Context,
    userName: String,
    // job = user password
    job: String,
    image: String?,
    result: MutableState<String>
) {
    val retrofitAPI = createRetrofitInstance()

    try {
        val userRegisterModel = UserRegisterModel(userName, job, image)
        val call: Call<User> = retrofitAPI.registerUser(userRegisterModel)
        call.enqueue(object : Callback<User> {
            override fun onResponse(
                call: Call<User>,
                response: Response<User>
            ) {
                Log.d(
                    "registerUser",
                    "Executed"
                )
                if (response.code() == 400) {
                    Toast.makeText(ctx, "User already exists", Toast.LENGTH_SHORT).show()
                    val resp = "User already exists"
                    result.value = resp
                } else if (response.code() == 200) {
                    Toast.makeText(ctx, "User created", Toast.LENGTH_SHORT).show()
                    val resp = "User created"
                    result.value = resp
                }
                Log.d("registerUser", response.body().toString())
            }

            // error handling
            override fun onFailure(call: Call<User>, t: Throwable) {
                result.value = "Error found is: \n" + t.message
                t.message?.let { Log.i("Error found is : ", it) }
            }

        })
    } catch (e: Exception) {
        Log.d("Error: ", e.toString())
    }


}

fun getAllUsers() {
    Log.d("Token: ", "${preferencesManager.getJWT()}")
    val retrofitAPI = createRetrofitInstance()
    // have to add "Bearer " in front of JWT in order to match pattern defined in backend
    val call: Call<List<ApiUser>> = retrofitAPI.getAllUsers("Bearer ${preferencesManager.getJWT()}")
    call!!.enqueue(object : Callback<List<ApiUser>> {
        override fun onResponse(call: Call<List<ApiUser>>, response: Response<List<ApiUser>>) {
            val resp =
                "Http-Code:" + response.code()
            Log.i("Response: ", resp)
            Log.d("Response: ", response.body().toString())
        }

        override fun onFailure(call: Call<List<ApiUser>>, t: Throwable) {
            t.message?.let { Log.i("Error found is : ", it) }
        }
    })
}

fun getUser(result: MutableState<User>) {
    val retrofitAPI = createRetrofitInstance()
    Log.d("getUser", "Executed")

    val call: Call<User> = retrofitAPI.getUser(
        "Bearer ${preferencesManager.getJWT()}",
        "${preferencesManager.getMail()}"
    )
    //val call: Call<ApiUser> = retrofitAPI.getUser("Bearer $jwt", email)
    call!!.enqueue(object : Callback<User> {
        override fun onResponse(call: Call<User>, response: Response<User>) {
            val user = response.body()
            if (user != null) {
                result.value = user
            }
            Log.d("getUser", "User Info ${user.toString()}")
        }



        override fun onFailure(call: Call<User>, t: Throwable) {
            t.message?.let { Log.i("Error found is : ", it) }
        }

    })
    // TODO save image in sharedPrefs upon login?
    // preferencesManager.saveImage()
}

fun getUserMail(result: MutableState<String>) {
    val retrofitAPI = createRetrofitInstance()
    val call: Call<User> = retrofitAPI.getUser(
        "Bearer ${preferencesManager.getJWT()}",
        "${preferencesManager.getMail()}"
    )
    call!!.enqueue(object : Callback<User> {
        override fun onResponse(call: Call<User>, response: Response<User>) {
            val userMail = preferencesManager.getMail()
            Log.d("User Mail: ", userMail.toString())
            if (userMail != null) {
                result.value = userMail
            }
        }

        override fun onFailure(call: Call<User>, t: Throwable) {
            t.message?.let { Log.i("Error found is : ", it) }
        }

    })
}

// TODO add image
fun createProject(
    ctx: Context,
    name: String,
    description: String,
    maxAttendees: String,
    startDate: String,
    endDate: String,
    image: String?,
    skillIds: List<Long>
) {
    Log.d("createProject", "Executed")
    val project =
        ProjectRequest(name, description, maxAttendees, startDate, endDate, image, skillIds)
    Log.d("createProject", "Project: $project")
    val retrofitAPI = createRetrofitInstance()
    val call: Call<Project> = retrofitAPI.createProject(
        "Bearer ${preferencesManager.getJWT()}",
        "${preferencesManager.getMail()}",
        project
    )
    call!!.enqueue(object : Callback<Project> {
        override fun onResponse(call: Call<Project>, response: Response<Project>) {
            Log.d("createProject", "Created $response")
            if (response.code() == 200) {
                Toast.makeText(ctx, "Project $name created", Toast.LENGTH_SHORT).show()
                Log.d("createProject", "Response Code ${response.code()}")
            } else {
                Toast.makeText(ctx, "Failed to create project", Toast.LENGTH_SHORT).show()
                Log.d("createProject", "Response Code ${response.code()}")
            }
        }

        override fun onFailure(call: Call<Project>, t: Throwable) {
            t.message?.let { Log.d("createProject", "Error: $it") }
        }

    })
}

fun getAllProjects(result: MutableState<List<Project>>) {
    Log.d("getAllProjects: ", "Executed")
    val retrofitAPI = createRetrofitInstance()
    val call: Call<List<Project>> =
        retrofitAPI.getAllProjects("Bearer ${preferencesManager.getJWT()}")
    call!!.enqueue(object : Callback<List<Project>> {
        override fun onResponse(call: Call<List<Project>>, response: Response<List<Project>>) {
            // Log.d("getAllProjects", "Http-Code: ${response.code()}") // debug only
            Log.d("getAllProjects", response.body().toString())
            result.value = response.body() as MutableList<Project>
            Log.d("getAllProjects", "Projects as List: $result")
        }

        override fun onFailure(call: Call<List<Project>>, t: Throwable) {
            t.message?.let { Log.i("Error found is : ", it) }
        }
    })
}

fun getAvailableSkills(ctx: Context, result: MutableState<List<Skill>>) {
    Log.d("getAvailableSkills", "Executed")
    val retrofitAPI = createRetrofitInstance()
    preferencesManager = PreferencesManager(ctx)
    // preferencesManager = PreferencesManager(ctx)
    val call: Call<List<Skill>> =
        retrofitAPI.getAllSkills(/*"Bearer ${preferencesManager.getJWT()}"*/)
    call!!.enqueue(object : Callback<List<Skill>> {
        override fun onResponse(call: Call<List<Skill>>, response: Response<List<Skill>>) {
            Log.d("getAvailableSkills", "Http-Code: ${response.code()}") // debug only
            Log.d("getAvailableSkills", response.body().toString())
            result.value = response.body() as MutableList<Skill>
            Log.d("getAvailableSkills", "Skills as List: $result")
        }

        override fun onFailure(call: Call<List<Skill>>, t: Throwable) {
            t.message?.let { Log.d("getAvailableSkills", it) }
        }
    })
}

fun addSkillToUser(
    email: String,
    skillIds: List<Long>
) {
    Log.d("addSkillsToUser", "Executed")
    Log.d("addSkillsToUser", "Skills to add: $skillIds")
    Log.d("addSkillsToUser", "Email: $email")
    val retrofitAPI = createRetrofitInstance()

    val call: Call<ApiUser> = retrofitAPI.addSkillToUser(
        email, skillIds
    )
    call!!.enqueue(object : Callback<ApiUser> {
        override fun onResponse(call: Call<ApiUser>, response: Response<ApiUser>) {
            Log.d("addSkillsToUser", "Created $response")
            if (response.code() == 201) {
                Log.d("addSkillsToUser", "Response Code ${response.code()}")
            } else {
                Log.d("addSkillsToUser", "Failed: Response Code ${response.code()}")
            }
        }

        // TODO Error: End of input at line 1 column 1 path $ (works though)
        override fun onFailure(call: Call<ApiUser>, t: Throwable) {
            t.message?.let { Log.d("addSkillsToUser", "Error: $it") }
        }

    })
}

fun getProjectsByUserEmail(result: MutableState<List<Project>>) {
    Log.d("getProjectsByUserEmail: ", "Executed")
    val retrofitAPI = createRetrofitInstance()
    val call: Call<List<Project>> =
        retrofitAPI.getProjectsByUserEmail(
            "Bearer ${preferencesManager.getJWT()}",
            "${preferencesManager.getMail()}"
        )
    call!!.enqueue(object : Callback<List<Project>> {
        override fun onResponse(call: Call<List<Project>>, response: Response<List<Project>>) {
            // Log.d("getProjectsByUserEmail", "Http-Code: ${response.code()}") // debug only
            Log.d("getProjectsByUserEmail", response.body().toString())
            try {
                result.value = response.body() as MutableList<Project>
            } catch (e: Exception) {
                Log.d("getProjectsByUserEmail", "CATCHED ERROR !!! $result")
            }
            Log.d("getProjectsByUserEmail", "Projects as List: $result")
        }

        override fun onFailure(call: Call<List<Project>>, t: Throwable) {
            t.message?.let { Log.i("Error found is : ", it) }
        }
    })
}

// LEGACY: required skills are automatically added to project upon creation
/*fun addRequiredSkillsToProject(
    skillIds: List<Long>
) {
    Log.d("addRequiredSkillsToProject", "Executed")
    Log.d("addRequiredSkillsToProject", "Skills to add: $skillIds")
    val retrofitAPI = createRetrofitInstance()

    val call: Call<Project> = retrofitAPI.addRequiredSkillsToProject(
        "Bearer ${preferencesManager.getJWT()}",
        projectId,
        skillIds
    )
    Log.d("addRequiredSkillsToProject", "Email: ${preferencesManager.getMail()}")
    call!!.enqueue(object : Callback<Project> {
        override fun onResponse(call: Call<Project>, response: Response<Project>) {
            Log.d("addRequiredSkillsToProject", "Created $response")
            if (response.code() == 201) {
                Log.d("addRequiredSkillsToProject", "Response Code ${response.code()}")
            } else {
                Log.d("addRequiredSkillsToProject", "Failed: Response Code ${response.code()}")
            }
        }

        // TODO Error: End of input at line 1 column 1 path $ (works though)
        override fun onFailure(call: Call<Project>, t: Throwable) {
            t.message?.let { Log.d("addRequiredSkillsToProject", "Error: $it") }
        }

    })
}*/

fun getRequiredSkills(result: MutableState<List<Skill>>, projectId: Long) {
    Log.d("getRequiredSkills: ", "Executed")
    val retrofitAPI = createRetrofitInstance()
    val call: Call<List<Skill>> =
        retrofitAPI.getRequiredSkills(
            "Bearer ${preferencesManager.getJWT()}",
            projectId
        )
    call!!.enqueue(object : Callback<List<Skill>> {
        override fun onResponse(call: Call<List<Skill>>, response: Response<List<Skill>>) {
            // Log.d("getProjectsByUserEmail", "Http-Code: ${response.code()}") // debug only
            Log.d("getRequiredSkills", response.body().toString())
            try {
                result.value = response.body() as MutableList<Skill>
            } catch (e: Exception) {
                Log.d("getRequiredSkills", "CATCHED ERROR !!! $result")
            }
            Log.d("getRequiredSkills", "Projects as List: $result")
        }

        override fun onFailure(call: Call<List<Skill>>, t: Throwable) {
            t.message?.let { Log.i("Error found is : ", it) }
        }
    })
}

fun getLocalUserEmail(): String? {
    return preferencesManager.getMail()
}


