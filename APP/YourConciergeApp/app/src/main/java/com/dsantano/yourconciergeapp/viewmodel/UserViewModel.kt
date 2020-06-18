package com.dsantano.yourconciergeapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dsantano.yourconciergeapp.api.response.users.SendToEditUser
import com.dsantano.yourconciergeapp.api.response.users.User
import com.dsantano.yourconciergeapp.common.Resource
import com.dsantano.yourconciergeapp.repository.UserRepository
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class UserViewModel @Inject constructor(val userRepository: UserRepository) : ViewModel() {

    var user : MutableLiveData<Resource<User>> = MutableLiveData()
    var voidResponse :  MutableLiveData<Resource<Unit>> = MutableLiveData()

    fun getMe() = viewModelScope.launch {
        user.value = Resource.Loading()
        val resp = userRepository.getMyUser()
        user.value =handleResponse(resp)
    }

    fun getUserById(id: String) = viewModelScope.launch {
        user.value = Resource.Loading()
        val resp = userRepository.getUserById(id)
        user.value =handleResponse(resp)
    }

    fun handleResponse(response: Response<User>): Resource<User> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error("Error loading data")
    }

    fun editUserById(id: String, req: SendToEditUser) = viewModelScope.launch {
        voidResponse.value = Resource.Loading()
        val resp = userRepository.ediUserById(id, req)
        voidResponse.value =handleVoidResponse(resp)
    }

    fun deleteUserById(id: String) = viewModelScope.launch {
        voidResponse.value = Resource.Loading()
        val resp = userRepository.deleteUserById(id)
        voidResponse.value =handleVoidResponse(resp)
    }

    fun handleVoidResponse(response: Response<Void>): Resource<Unit> {
        if(response.isSuccessful) {
            return Resource.Success(Unit)
        }
        return Resource.Error("Error")
    }
}