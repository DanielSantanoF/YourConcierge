package com.dsantano.yourconciergeapp.common

class Constants {

    companion object{
        const val API_BASE_URL="http://10.0.2.2:9000/"
        //const val API_BASE_URL="https://yourconcierge.herokuapp.com/"
        val SHARED_PREFERENCES_FILE: String? = "SHARED_PREFERENCES_FILE"
        const val SHAREDPREF_AUTH_TOKEN = "authToken"
        const val SHAREDPREF_USER_ROLE = "userRole"
        const val INTENT_ID="id"
    }

}