package com.dsantano.yourconciergeapp.common

import com.dsantano.yourconciergeapp.api.generators.ServiceGenerator
import com.dsantano.yourconciergeapp.ui.login.LoginActivity
import com.dsantano.yourconciergeapp.ui.register.RegisterActivity
import com.dsantano.yourconciergeapp.ui.tickets.MyTicketsFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ServiceGenerator::class])
interface ApplicationComponent {
    fun inject(main: LoginActivity)
    fun inject(registerActivity: RegisterActivity)
    fun inject(myTicketsFragment: MyTicketsFragment)
}