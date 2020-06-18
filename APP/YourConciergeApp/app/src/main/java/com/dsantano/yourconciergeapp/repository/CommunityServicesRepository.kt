package com.dsantano.yourconciergeapp.repository

import androidx.lifecycle.MutableLiveData
import com.dsantano.yourconciergeapp.api.YourConciergeService
import com.dsantano.yourconciergeapp.api.response.communityservices.CommunityServicesListItem
import com.dsantano.yourconciergeapp.api.response.communityservices.NewCommunityServices
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommunityServicesRepository @Inject constructor(var yourConciergeService: YourConciergeService){
    var communityServices: MutableLiveData<CommunityServicesListItem> = MutableLiveData()
    var communityServicesList: MutableLiveData<List<CommunityServicesListItem>> = MutableLiveData()

    suspend fun getAllCommunityServices() = yourConciergeService.getAllCommunityServices()

    suspend fun getMyCommunityServices() = yourConciergeService.getMyCommunityServices()

    suspend fun getCommunityServicesByUserId(id: String) = yourConciergeService.getCommunityServicesByUserId(id)

    suspend fun getCommunityServicesById(id: String) = yourConciergeService.getCommunityServicesById(id)

    suspend fun postNewCommunityService(req: NewCommunityServices) = yourConciergeService.postNewCommunityService(req)

    suspend fun editCommunityServiceById(id: String, req: NewCommunityServices) = yourConciergeService.editCommunityServiceById(id, req)

    suspend fun deleteCommunityServiceById(id: String) = yourConciergeService.deleteCommunityServiceById(id)

}