package com.example.skillmatcher.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.models.User
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**@HiltViewModel
class LoginUserChatModel @Inject constructor(private val client: ChatClient
): ViewModel(){

    private val _loginEvent = MutableSharedFlow<LogInEvent>()
    val loginEvent = _loginEvent.asSharedFlow()

    private fun loginUser(username: String, token:String){
        val user = User(id= username, name= username)

        client.connectUser(
            user= user,
            token= token
                        ).enqueue{
                            result ->
            if( result.isSuccess){
                viewModelScope.launch {
                    _loginEvent.emit(LogInEvent.Success)
                }
            } else{
                viewModelScope.launch {
                    _loginEvent.emit(LogInEvent.ErrorLogIn(
                        result.error().message ?: "Unknown Error"
                    ))
                }
            }
        }
    }

    sealed class LogInEvent{
        object ErrorInputTooshort : LogInEvent()
        data class ErrorLogIn(val error: String): LogInEvent()
        object Success: LogInEvent()
    }
}*/