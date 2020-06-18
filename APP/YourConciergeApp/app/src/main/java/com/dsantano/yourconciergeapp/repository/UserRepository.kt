package com.dsantano.yourconciergeapp.repository

import androidx.lifecycle.MutableLiveData
import com.dsantano.yourconciergeapp.api.YourConciergeService
import com.dsantano.yourconciergeapp.api.response.login.SendToLogin
import com.dsantano.yourconciergeapp.api.response.register.SendToRegister
import com.dsantano.yourconciergeapp.api.response.users.SendToEditUser
import com.dsantano.yourconciergeapp.api.response.users.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(var yourConciergeService: YourConciergeService){
    var user: MutableLiveData<User> = MutableLiveData()
    var userList: MutableLiveData<List<User>> = MutableLiveData()

    suspend fun postUserLogin(req: SendToLogin) = yourConciergeService.postLogin(req)

    suspend fun postRegisterNewUser(req: SendToRegister) = yourConciergeService.postRegister(req)

    suspend fun getMyUser() = yourConciergeService.getMe()

    suspend fun getUserById(id: String) = yourConciergeService.getUserById(id)

    suspend fun ediUserById(id: String, req: SendToEditUser) = yourConciergeService.editUserById(id, req)

    suspend fun deleteUserById(id: String) = yourConciergeService.deleteUserById(id)

}