package com.example.skillmatcher.api

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.ui.text.input.TextFieldValue
import com.example.skillmatcher.data.ApiUser
import com.example.skillmatcher.data.UserLoginModel
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


// TODO: Wie hier machen: https://stackoverflow.com/questions/30180957/send-post-request-with-params-using-retrofit
// TODO: BACKEND liefer bei login dirc alle daten!

// define an interface that represents the API we want to access => use this interface to make requests to the API
interface BackendAPI {
    @POST("auth/login")
    // add "suspend" in front of "fun" to run in co-routine instead of main thread?
    fun loginUser(@Body userLoginModel: UserLoginModel): Call<String>?

    @POST("auth/register")
    fun registerUser(@Body userLoginModel: UserLoginModel): Call<ApiUser>

    //@GET("excluded")
    //fun getAllUsers(): Call<List<ApiUser>>

    @GET("user")
    fun getAllUsers(@Header("Authorization") jwt: String): Call<List<ApiUser>>

    @GET("user/{email}")
    fun getUser(@Header("Authorization") jwt: String, @Path("email") email: String): Call<ApiUser>


}
var token = ""

private lateinit var preferencesManager: PreferencesManager

// change URL for testing - has to be http://10.0.2.2:8080/ when running local server
const val url =
    "http://10.0.2.2:8080/"
//"http://msp-ws2223-5.dev.mobile.ifi.lmu.de:80/"


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
    Log.i("APIController", "Post login data!")

    val retrofitAPI = createRetrofitInstance()

    try {
        // pass data from text fields
        val userLoginModel = UserLoginModel(userName.value.text, job.value.text)
        // call a method (asynchronously) to create an update and pass our model class
        val call: Call<String>? = retrofitAPI.loginUser(userLoginModel)
        // execute request asynchronously
        call!!.enqueue(object : Callback<String> {
            // call onResponse when request succeeds
            override fun onResponse(
                call: Call<String>,
                response: Response<String>
            ) {
                if(response.body() != null) {
                    Toast.makeText(ctx, "Logged in successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(ctx, "Error logging in", Toast.LENGTH_SHORT).show()
                }
                // log received jwt
                Log.d("Received JWT: ", response.body().toString())

                token = response.body().toString()
                Log.d("Token: ", token)

                val statusCode =
                    "HTTP-Code: " + response.code() + "\nJWT : " + token // + "\n" + "User Name : " + model!!.email + "\n" + "Job : " + model!!.password
                result.value = statusCode

                // save the token to EncryptedSharedPreferences
                preferencesManager = PreferencesManager(ctx)
                preferencesManager.saveJWT(token)
            }

            // error handling
            override fun onFailure(call: Call<String>, t: Throwable) {
                // show error response from API in UI
                result.value = "Error found is: \n" + t.message
                // show error in log
                t.message?.let { Log.d("Error: ", it) };
            }
        })

    } catch (e: Exception) {
        val error = e.printStackTrace()
        Log.d("Error stacktrace: ", error.toString())
    }

}

fun registerUser(
    ctx: Context,
    userName: MutableState<TextFieldValue>,
    // job = user password
    job: MutableState<TextFieldValue>,
    result: MutableState<String>
) {
    val retrofitAPI = createRetrofitInstance()

    try {
        val userLoginModel = UserLoginModel(userName.value.text, job.value.text)
        val call: Call<ApiUser> = retrofitAPI.registerUser(userLoginModel)
        call!!.enqueue(object : Callback<ApiUser> {
            override fun onResponse(
                call: Call<ApiUser>,
                response: Response<ApiUser>
            ) {
                Log.i(
                    "Executing call to function: ",
                    "register user"
                )
                if (response.code() == 400) {
                    val resp = "User already exists"
                    result.value = resp
                } else if (response.code() == 200) {
                    val resp = "User created"
                    result.value = resp
                }
                Log.d("Response: ", response.body().toString())
            }

            // error handling
            override fun onFailure(call: Call<ApiUser>, t: Throwable) {
                result.value = "Error found is: \n" + t.message
                t.message?.let { Log.i("Error found is : ", it) }
            }

        })
    } catch (e: Exception) {
        Log.d("Error: ", e.toString())
    }


}

fun getAllUsers() {
    // TODO app crashes if login is not executed before getUsers => preferencesManager has not been initialized
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

fun getUser() {
    val retrofitAPI = createRetrofitInstance()
    val email = "test12@test.de"
    Log.d("Executed ", "getUser")

    val call: Call<ApiUser> = retrofitAPI.getUser("Bearer ${preferencesManager.getJWT()}", email)
    //val call: Call<ApiUser> = retrofitAPI.getUser("Bearer $jwt", email)
    call!!.enqueue(object: Callback<ApiUser> {
        override fun onResponse(call: Call<ApiUser>, response: Response<ApiUser>) {
            val user = response.body()
            Log.d("User Info", user.toString())
        }

        override fun onFailure(call: Call<ApiUser>, t: Throwable) {
            t.message?.let { Log.i("Error found is : ", it) }
        }

    })


}

