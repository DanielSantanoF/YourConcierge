package com.dsantano.yourconciergeapp.api.generators

import com.dsantano.yourconciergeapp.common.Constants
import com.dsantano.yourconciergeapp.common.MySharedPreferencesManager
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthTokenInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()
        val req: Request
        val token = MySharedPreferencesManager().getSharedPreferences().getString(Constants.SHAREDPREF_AUTH_TOKEN, "")

        val reqBuilder: Request.Builder = original.newBuilder().header("Authorization", "Bearer " + token)
        req = reqBuilder.build()

        return chain.proceed(req)
    }
}