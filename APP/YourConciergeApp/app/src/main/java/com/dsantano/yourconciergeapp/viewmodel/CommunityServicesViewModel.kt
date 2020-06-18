package com.dsantano.yourconciergeapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dsantano.yourconciergeapp.api.response.communityservices.CommunityServicesListItem
import com.dsantano.yourconciergeapp.api.response.communityservices.NewCommunityServices
import com.dsantano.yourconciergeapp.common.Resource
import com.dsantano.yourconciergeapp.repository.CommunityServicesRepository
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class CommunityServicesViewModel@Inject constructor(val communityServicesRepository: CommunityServicesRepository) : ViewModel() {

    var communityServicesList : MutableLiveData<Resource<List<CommunityServicesListItem>>> = MutableLiveData()
    var communityService : MutableLiveData<Resource<CommunityServicesListItem>> = MutableLiveData()
    var voidResponse : MutableLiveData<Resource<Unit>> = MutableLiveData()

    fun getAllCommunityServices() = viewModelScope.launch {
        communityServicesList.value = Resource.Loading()
        val resp = communityServicesRepository.getAllCommunityServices()
        communityServicesList.value =handleResponseList(resp)
    }

    fun handleResponseList(response: Response<List<CommunityServicesListItem>>): Resource<List<CommunityServicesListItem>> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error("Error loading data")
    }

    fun getMyCommunityServices() = viewModelScope.launch {
        communityService.value = Resource.Loading()
        val resp = communityServicesRepository.getMyCommunityServices()
        communityService.value =handleResponse(resp)
    }

    fun getCommunityServicesByUserId(id: String) = viewModelScope.launch {
        communityService.value = Resource.Loading()
        val resp = communityServicesRepository.getCommunityServicesByUserId(id)
        communityService.value =handleResponse(resp)
    }

    fun getCommunityServicesById(id: String) = viewModelScope.launch {
        communityService.value = Resource.Loading()
        val resp = communityServicesRepository.getCommunityServicesById(id)
        communityService.value =handleResponse(resp)
    }

    fun postNewCommunityService(req : NewCommunityServices) = viewModelScope.launch {
        communityService.value = Resource.Loading()
        val resp = communityServicesRepository.postNewCommunityService(req)
        communityService.value =handleResponse(resp)
    }

    fun editCommunityServiceById(id: String, req : NewCommunityServices) = viewModelScope.launch {
        communityService.value = Resource.Loading()
        val resp = communityServicesRepository.editCommunityServiceById(id, req)
        communityService.value =handleResponse(resp)
    }

    fun handleResponse(response: Response<CommunityServicesListItem>): Resource<CommunityServicesListItem> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error("Error loading data")
    }

    fun deleteCommunityServiceById(id: String) = viewModelScope.launch {
        voidResponse.value = Resource.Loading()
        val resp = communityServicesRepository.deleteCommunityServiceById(id)
        voidResponse.value =handleVoidResponse(resp)
    }

    fun handleVoidResponse(response: Response<Void>): Resource<Unit> {
        if(response.isSuccessful) {
            return Resource.Success(Unit)
        }
        return Resource.Error("Error")
    }
}