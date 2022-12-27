package com.example.skillmatcher.api

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.ui.text.input.TextFieldValue
import com.example.skillmatcher.data.ApiUser
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST


// TODO: Wie hier machen: https://stackoverflow.com/questions/30180957/send-post-request-with-params-using-retrofit
// TODO: BACKEND liefer bei login dirc alle daten!

// define an interface that represents the API we want to access => use this interface to make requests to the API
interface BackendAPI {
    @Headers("Accept: application/json")
    @POST("auth/login")
    // @Body annotation to pass JSON data
    // add "suspend" in front of "func" to run in co-routine instead of main thread?
    // add UserLoginModel (JSON) email + password is passed => UserModel is returned - error here?
    fun loginUser(@Body userLoginModel: JSONObject): Call<String?>?
    //fun loginUser(@Body params: RequestBody): Call<LoginResponse?>?

    @GET("excluded")
    fun getAllUsers(): Call<List<ApiUser>>
}

fun postLoginUserData(
    ctx: Context,
    userName: MutableState<TextFieldValue>,
    // job = user password
    job: MutableState<TextFieldValue>,
    result: MutableState<kotlin.String>
) {

    Log.i("APIController", "Post login data!")

    // change URL for testing - has to be http://10.0.2.2:8080/ when running local server
    val url =
        "http://10.0.2.2:8080/"
    // "http://msp-ws2223-5.dev.mobile.ifi.lmu.de:80/"

    /*val gson = GsonBuilder()
        .setLenient()
        .create()*/

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
    /*val userLoginModel = UserLoginModel(userName.value.text, job.value.text)*/
    try {
        val paramObject = JSONObject()
        paramObject.put("email", "test12@test.de")
        paramObject.put("password", "test")
        Log.d("JSONObject: ", paramObject.toString())
        val call: Call<String?>? = retrofitAPI.loginUser(paramObject)

        // call a method (asynchronously) to create an update and pass our model class
        /*val call: Call<LoginResponse?>? = retrofitAPI.loginUser(userLoginModel)*/
        // execute request asynchronously
        call!!.enqueue(object : Callback<String?> {
            // call onResponse when request succeeds
            override fun onResponse(
                call: Call<String?>,
                response: Response<String?>
            ) {
                Log.i(
                    "Executing call to function: ",
                    "Post login user data "
                ) // only executes with wrong login data?
                // show button when we get a response from our api
                Toast.makeText(ctx, "Data posted to API", Toast.LENGTH_SHORT).show()

                try {
                    //get your response....
                    Log.d("RetroFit2.0: Login: ", response.body().toString())
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                val statusCode =
                    "Http-Code:" + response.code() // +  "JWT : " + response.body() + "\n" + "User Name : " + model!!.email + "\n" + "Job : " + model!!.password
                Log.i("Response: ", statusCode)
                // below line we are setting our string to our response.
                result.value = statusCode
            }

            // error handling
            override fun onFailure(call: Call<String?>, t: Throwable) {
                // we get error response from API.
                result.value = "Error found is : " + t.message
                t.message?.let { Log.d("Error: ", it) };
            }
        })

    } catch (e: JSONException) {
        e.printStackTrace()
    }

}

fun getAllUsers() {
    Log.i("APIController", "get user data!")

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
    // call a method (asynchronously) to create an update and pass our model class
    val call: Call<List<ApiUser>> = retrofitAPI.getAllUsers()
    // execute request asynchronously
    call!!.enqueue(object : Callback<List<ApiUser>> {
        // call onResponse when request succeeds
        override fun onResponse(call: Call<List<ApiUser>>, response: Response<List<ApiUser>>) {
            Log.i(
                "Executing call to function: ",
                "get all users"
            ) // only executes with wrong login data?
            val resp =
                "Http-Code:" + response.code()
            // below line we are setting our string to our response.
            Log.i("Response: ", resp)
            Log.d("Response: ", response.body().toString())
        }

        // error handling
        override fun onFailure(call: Call<List<ApiUser>>, t: Throwable) {
            // log error response
            t.message?.let { Log.i("Error found is : ", it) }
        }

    })
}