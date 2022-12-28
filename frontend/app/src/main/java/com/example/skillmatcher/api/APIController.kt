package com.example.skillmatcher.api

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.ui.text.input.TextFieldValue
import com.example.skillmatcher.data.ApiUser
import com.example.skillmatcher.data.UserLoginModel
import com.google.gson.GsonBuilder
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
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
    // add "suspend" in front of "func" to run in co-routine instead of main thread?
    fun loginUser(@Body userLoginModel: UserLoginModel): Call<String>?

    @GET("excluded")
    fun getAllUsers(): Call<List<ApiUser>>

    @POST("auth/register")
    fun registerUser(@Body userLoginModel: UserLoginModel): Call<UserLoginModel>
}

fun postLoginUserData(
    ctx: Context,
    userName: MutableState<TextFieldValue>,
    // job = user password
    job: MutableState<TextFieldValue>,
    result: MutableState<String>
) {

    Log.i("APIController", "Post login data!")

    // change URL for testing - has to be http://10.0.2.2:8080/ when running local server
    val url =
        "http://10.0.2.2:8080/"
    // "http://msp-ws2223-5.dev.mobile.ifi.lmu.de:80/"

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
    val retrofitAPI = retrofit.create(BackendAPI::class.java)

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
                // show button when we get a response from our api
                Toast.makeText(ctx, "Data posted to API", Toast.LENGTH_SHORT).show()

                // log received jwt
                Log.d("Received JWT: ", response.body().toString())

                val statusCode =
                    "HTTP-Code: " + response.code()  +  "\nJWT : " + response.body() // + "\n" + "User Name : " + model!!.email + "\n" + "Job : " + model!!.password
                result.value = statusCode
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
        Log.d("Error stacktrace:", error.toString())
    }

}

fun getAllUsers() {
    Log.i("APIController", "get user data!")

    // change URL for testing - has to be http://10.0.2.2:8080/ when running local server
    val url = "http://10.0.2.2:8080/"
    // http://msp-ws2223-5.dev.mobile.ifi.lmu.de:80/

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
    val retrofitAPI = retrofit.create(BackendAPI::class.java)
    // call a method (asynchronously) to create an update and pass our model class
    val call: Call<List<ApiUser>> = retrofitAPI.getAllUsers()
    // execute request asynchronously
    call!!.enqueue(object : Callback<List<ApiUser>> {
        // call onResponse when request succeeds
        override fun onResponse(call: Call<List<ApiUser>>, response: Response<List<ApiUser>>) {
            val resp =
                "Http-Code:" + response.code()
            // below line we are setting our string to our response.
            Log.i("Response: ", resp)
            // log all users found
            Log.d("Response: ", response.body().toString())
        }

        // error handling
        override fun onFailure(call: Call<List<ApiUser>>, t: Throwable) {
            // log error response
            t.message?.let { Log.i("Error found is : ", it) }
        }

    })
}

// TODO bad request (400)
fun registerUser() {
    // change URL for testing - has to be http://10.0.2.2:8080/ when running local server
    val url = "http://10.0.2.2:8080/"
    // http://msp-ws2223-5.dev.mobile.ifi.lmu.de:80/


    // create a retrofit builder and pass base url
    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        // sending data in json => add Gson converter factory
        .addConverterFactory(GsonConverterFactory.create())
        // build retrofit builder
        .build()
    // create a "proxy" object that implements the BackendAPI interface
    val retrofitAPI = retrofit.create(BackendAPI::class.java)
    try {
        val paramObject = JSONObject()
        paramObject.put("email", "test13@test.de")
        paramObject.put("password", "test")
        // call a method (asynchronously) to create an update and pass our model class
        val call: Call<UserLoginModel> = retrofitAPI.registerUser(paramObject)
        call!!.enqueue(object : Callback<UserLoginModel> {
            // call onResponse when request succeeds
            override fun onResponse(
                call: Call<UserLoginModel>,
                response: Response<UserLoginModel>
            ) {
                Log.i(
                    "Executing call to function: ",
                    "register user"
                ) // only executes with wrong login data?
                val resp =
                    "Http-Code:" + response.code()
                // below line we are setting our string to our response.
                Log.i("Response: ", resp)
                Log.d("Response: ", response.body().toString())
            }

            // error handling
            override fun onFailure(call: Call<UserLoginModel>, t: Throwable) {
                // log error response
                t.message?.let { Log.i("Error found is : ", it) }
            }

        })
    } catch (e: JSONException) {
        e.printStackTrace()
    }


}