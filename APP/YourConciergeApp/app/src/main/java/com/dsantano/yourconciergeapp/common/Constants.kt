package com.dsantano.yourconciergeapp.common

class Constants {

    companion object{
        const val API_BASE_URL="http://10.0.2.2:9000/"
        //const val API_BASE_URL="https://yourconcierge.herokuapp.com/"
        val SHARED_PREFERENCES_FILE: String? = "SHARED_PREFERENCES_FILE"
        const val SHAREDPREF_AUTH_TOKEN = "authToken"
        const val SHAREDPREF_USER_ROLE = "userRole"
        const val INTENT_ID="id"
        const val INTENT_CREATED_AT="createdAt"
        const val INTENT_LAST_UPDATED="lastUpdated"
        const val INTENT_INCIDENCE="incidence"
        const val INTENT_DESCRIPTION="description"
        const val INTENT_URGENT="urgent"
        const val INTENT_FOR_EDIT="forEdit"
        const val INTENT_FULLNAME="fullName"
        const val INTENT_USERNAME="username"
        const val INTENT_FLOOR="floor"
        const val INTENT_NUMBER="number"
        const val INTENT_TRASH_BAGS="number"
        const val INTENT_CLEAN_FLOOR="number"
    }

}