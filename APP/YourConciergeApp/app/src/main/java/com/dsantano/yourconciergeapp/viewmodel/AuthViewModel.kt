package com.dsantano.yourconciergeapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dsantano.yourconciergeapp.api.response.login.LoginResponse
import com.dsantano.yourconciergeapp.api.response.login.SendToLogin
import com.dsantano.yourconciergeapp.api.response.register.RegisterResponse
import com.dsantano.yourconciergeapp.api.response.register.SendToRegister
import com.dsantano.yourconciergeapp.common.Resource
import com.dsantano.yourconciergeapp.repository.UserRepository
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class AuthViewModel @Inject constructor(val userRepository: UserRepository) : ViewModel()  {

    var login : MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    var register : MutableLiveData<Resource<RegisterResponse>> = MutableLiveData()

    fun doLogin(req : SendToLogin) = viewModelScope.launch {
        login.value = Resource.Loading()
        val resp = userRepository.postUserLogin(req)
        login.value =handleLogin(resp)
    }

    fun handleLogin(response: Response<LoginResponse>): Resource<LoginResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error("Error at login")
    }

    fun doRegister(req : SendToRegister) = viewModelScope.launch {
        register.value = Resource.Loading()
        val resp = userRepository.postRegisterNewUser(req)
        register.value =handleRegister(resp)
    }

    fun handleRegister(response: Response<RegisterResponse>): Resource<RegisterResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error("Error at register")
    }

}