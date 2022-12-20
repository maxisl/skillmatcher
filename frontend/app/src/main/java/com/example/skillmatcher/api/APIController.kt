package com.example.skillmatcher.api

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.ui.text.input.TextFieldValue
import com.example.skillmatcher.data.User
import com.example.skillmatcher.data.UserLoginModel
import com.example.skillmatcher.data.UserModel
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


// TODO: Wie hier machen: https://stackoverflow.com/questions/30180957/send-post-request-with-params-using-retrofit
// TODO: BACKEND liefer bei login dirc alle daten!
// TODO: create retrofit instance (object) necessary?

// define an interface that represents the API we want to access => use this interface to make requests to the API
interface BackendAPI {
    @POST("auth/login")
    // @Body annotation to pass JSON data
    // add "suspend" in front of "func" to run in co-routine instead of main thread?
    // add UserLoginModel (JSON) email + password is passed => UserModel is returned - error here?
    fun loginUser(@Body userLoginModel: UserLoginModel?): Call<UserModel?>?

    @GET("")
    fun getAllUsers(): Call<List<User>>
}

fun postLoginUserData(
    ctx: Context,
    userName: MutableState<TextFieldValue>,
    job: MutableState<TextFieldValue>,
    result: MutableState<String>
) {

    Log.i("APIController", "Post login data!")

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
    // pass data from our text fields to our model class
    val userLoginModel = UserLoginModel(userName.value.text, job.value.text)
    // call a method (asynchronously) to create an update and pass our model class
    val call: Call<UserModel?>? = retrofitAPI.loginUser(userLoginModel)
    // execute request asynchronously
    call!!.enqueue(object : Callback<UserModel?> {
        // call onResponse when request succeeds
        override fun onResponse(call: Call<UserModel?>, response: Response<UserModel?>) {
            Log.i("Executing call to function: ", "Post login user data ") // only executes with wrong login data?
            // call when we get a response from our api
            Toast.makeText(ctx, "Data posted to API", Toast.LENGTH_SHORT).show()
            // we are getting a response from our body and
            // passing it to our model class.
            // val model: UserModel? = response.body()
            // on below line we are getting our data from model class
            // and adding it to our string.

            val resp =
                "Http-Code:" + response.code() // +  "JWT : " + response.body() + "\n" + "User Name : " + model!!.email + "\n" + "Job : " + model!!.password
            // below line we are setting our string to our response.
            result.value = resp
            Log.i("Response: ", resp)
        }

        // error handling
        override fun onFailure(call: Call<UserModel?>, t: Throwable) {
            // we get error response from API.
            result.value = "Error found is : " + t.message
        }
    })

}

fun getAllUsers() {}