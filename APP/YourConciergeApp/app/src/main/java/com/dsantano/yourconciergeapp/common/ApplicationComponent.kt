package com.dsantano.yourconciergeapp.common

import com.dsantano.yourconciergeapp.api.generators.ServiceGenerator
import com.dsantano.yourconciergeapp.ui.allcommunityservices.CommunityServicesFragment
import com.dsantano.yourconciergeapp.ui.communityservices.EditCommunityServicesActivity
import com.dsantano.yourconciergeapp.ui.communityservices.MyCommunityServicesActivity
import com.dsantano.yourconciergeapp.ui.ticket.TicketDetailActivity
import com.dsantano.yourconciergeapp.ui.login.LoginActivity
import com.dsantano.yourconciergeapp.ui.profile.EditProfileActivity
import com.dsantano.yourconciergeapp.ui.profile.ProfileActivity
import com.dsantano.yourconciergeapp.ui.register.RegisterActivity
import com.dsantano.yourconciergeapp.ui.ticket.EditTicketActivity
import com.dsantano.yourconciergeapp.ui.tickets.MyTicketsFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ServiceGenerator::class])
interface ApplicationComponent {
    fun inject(main: LoginActivity)
    fun inject(registerActivity: RegisterActivity)
    fun inject(myTicketsFragment: MyTicketsFragment)
    fun inject(ticketDetailActivity: TicketDetailActivity)
    fun inject(editTicketActivity: EditTicketActivity)
    fun inject(profileActivity: ProfileActivity)
    fun inject(editProfileActivity: EditProfileActivity)
    fun inject(myCommunityServicesActivity: MyCommunityServicesActivity)
    fun inject(editCommunityServicesActivity: EditCommunityServicesActivity)
    fun inject(communityServicesFragment: CommunityServicesFragment)
}