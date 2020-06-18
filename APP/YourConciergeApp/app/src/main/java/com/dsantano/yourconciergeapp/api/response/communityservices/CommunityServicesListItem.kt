package com.dsantano.yourconciergeapp.api.response.communityservices

data class CommunityServicesListItem(
    val cleanFloor: Boolean,
    val id: String,
    val myUser: MyUser,
    val trashBags: Boolean
)