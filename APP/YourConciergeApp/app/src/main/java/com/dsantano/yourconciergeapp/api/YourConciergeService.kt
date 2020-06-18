package com.dsantano.yourconciergeapp.api

import com.dsantano.yourconciergeapp.api.response.communityservices.CommunityServicesListItem
import com.dsantano.yourconciergeapp.api.response.communityservices.NewCommunityServices
import com.dsantano.yourconciergeapp.api.response.login.LoginResponse
import com.dsantano.yourconciergeapp.api.response.login.SendToLogin
import com.dsantano.yourconciergeapp.api.response.register.RegisterResponse
import com.dsantano.yourconciergeapp.api.response.register.SendToRegister
import com.dsantano.yourconciergeapp.api.response.tickets.NewTicket
import com.dsantano.yourconciergeapp.api.response.tickets.Ticket
import com.dsantano.yourconciergeapp.api.response.users.SendToEditUser
import com.dsantano.yourconciergeapp.api.response.users.User
import retrofit2.Response
import retrofit2.http.*

interface YourConciergeService {

    //AUTH
    @POST("/auth/login")
    suspend fun postLogin(@Body request : SendToLogin) : Response<LoginResponse>

    @POST("/user/register")
    suspend fun postRegister(@Body request : SendToRegister) : Response<RegisterResponse>

    //USERS
    @GET("/user/me")
    suspend fun getMe() : Response<User>

    @GET("/user/byid/{id}")
    suspend fun getUserById(@Path("id") userId : String) : Response<User>

    @PUT("/user/{id}")
    suspend fun editUserById(@Path("id") userId : String, @Body request : SendToEditUser) : Response<Void>

    @DELETE("/user/{id}")
    suspend fun deleteUserById(@Path("id") userId : String) : Response<Void>

    //TICKETS
    @GET("/ticket/all")
    suspend fun getAllTickets() : Response<List<Ticket>>

    @GET("/ticket/mytickets")
    suspend fun getMyTickets() : Response<List<Ticket>>

    @GET("/ticket/{id}")
    suspend fun getTicketById(@Path("id") ticketId : String) : Response<Ticket>

    @POST("/ticket/new")
    suspend fun postNewTicket(@Body request : NewTicket) : Response<Ticket>

    @PUT("/ticket/{id}")
    suspend fun editTicketById(@Path("id") ticketId : String, @Body request : NewTicket) : Response<Ticket>

    @DELETE("/ticket/{id}")
    suspend fun deleteTicketById(@Path("id") ticketId : String) : Response<Void>

    //COMMUNITY SERVICES
    @GET("/comunityservices/all")
    suspend fun getAllCommunityServices() : Response<List<CommunityServicesListItem>>

    @GET("/comunityservices/mycomunityservices")
    suspend fun getMyCommunityServices() : Response<CommunityServicesListItem>

    @GET("/comunityservices/byuser/{id}")
    suspend fun getCommunityServicesByUserId(@Path("id") userId : String) : Response<CommunityServicesListItem>

    @GET("/comunityservices/{id}")
    suspend fun getCommunityServicesById(@Path("id") comServId : String) : Response<CommunityServicesListItem>

    @POST("/comunityservices/new")
    suspend fun postNewCommunityService(@Body request : NewCommunityServices) : Response<CommunityServicesListItem>

    @PUT("/comunityservices/{id}")
    suspend fun editCommunityServiceById(@Path("id") comServId : String, @Body request : NewCommunityServices) : Response<CommunityServicesListItem>

    @DELETE("/comunityservices/{id}")
    suspend fun deleteCommunityServiceById(@Path("id") comServId : String) : Response<Void>
}