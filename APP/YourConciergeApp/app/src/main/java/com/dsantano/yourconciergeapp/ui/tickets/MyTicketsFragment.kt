package com.dsantano.yourconciergeapp.ui.tickets

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.Observer
import com.dsantano.yourconciergeapp.R
import com.dsantano.yourconciergeapp.common.Constants
import com.dsantano.yourconciergeapp.common.MyApp
import com.dsantano.yourconciergeapp.common.MySharedPreferencesManager
import com.dsantano.yourconciergeapp.common.Resource
import com.dsantano.yourconciergeapp.ui.communityservices.MyCommunityServicesActivity
import com.dsantano.yourconciergeapp.ui.login.LoginActivity
import com.dsantano.yourconciergeapp.ui.profile.ProfileActivity
import com.dsantano.yourconciergeapp.ui.ticket.EditTicketActivity

import com.dsantano.yourconciergeapp.viewmodel.TicketViewModel
import javax.inject.Inject

class MyTicketsFragment : Fragment() {

    private var columnCount = 1
    @Inject lateinit var ticketViewModel: TicketViewModel
    private lateinit var myTicketsAdapter: MyTicketRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)

        (activity?.applicationContext as MyApp).getApplicationComponent().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_my_tickets_list, container, false)
        myTicketsAdapter = MyTicketRecyclerViewAdapter()

        // Set the adapter
        val recyclerView = view.findViewById<RecyclerView>(R.id.myTicketsRecyclerView)

        with(recyclerView) {
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
            adapter = myTicketsAdapter
        }

        loadAllMyTickets()

        return view
    }

    private fun loadAllMyTickets(){
        ticketViewModel.getMyTickets()
        ticketViewModel.ticketList.observe(this, Observer {
            when(it) {
                is Resource.Success -> {
                    it.data?.let { results ->
                        myTicketsAdapter.setData(results)
                    }
                }
                is Resource.Error -> {
                    it.message?.let { message ->
                        Log.e("Error", "An error occured: $message")
                    }
                }
                is Resource.Loading -> {
                }
            }
        })
    }

    private fun loadAllTickets(){
        ticketViewModel.getAllTickets()
        ticketViewModel.ticketList.observe(this, Observer {
            when(it) {
                is Resource.Success -> {
                    it.data?.let { results ->
                        myTicketsAdapter.setData(results)
                    }
                }
                is Resource.Error -> {
                    it.message?.let { message ->
                        Log.e("Error", "An error occured: $message")
                    }
                }
                is Resource.Loading -> {
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        loadAllMyTickets()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val role = MySharedPreferencesManager().getSharedPreferences().getString(Constants.SHAREDPREF_USER_ROLE, "")
        if(role.equals("ADMIN")){
            inflater.inflate(R.menu.menu_ticket_list, menu)
        } else {
            inflater.inflate(R.menu.user_menu_ticket_list, menu)
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item!!.itemId
        if (id == R.id.all_my_tickets){
            loadAllMyTickets()
        }
        if (id == R.id.new_ticket){
            var intent = Intent(MyApp.instance, EditTicketActivity::class.java).apply{
                putExtra(Constants.INTENT_ID, "")
                putExtra(Constants.INTENT_CREATED_AT, "")
                putExtra(Constants.INTENT_LAST_UPDATED, "")
                putExtra(Constants.INTENT_INCIDENCE, "")
                putExtra(Constants.INTENT_DESCRIPTION, "")
                putExtra(Constants.INTENT_URGENT, "")
                putExtra(Constants.INTENT_FOR_EDIT, false)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            MyApp.instance.startActivity(intent)
        }
        if (id == R.id.all_tickets){
            loadAllTickets()
        }
        if (id == R.id.profile){
            val profile: Intent = Intent(MyApp.instance, ProfileActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(profile)
        }
        if (id == R.id.community_services){
            val comserv: Intent = Intent(MyApp.instance, MyCommunityServicesActivity::class.java).apply {
                putExtra(Constants.INTENT_ID, "")
                putExtra(Constants.INTENT_FOR_EDIT, false)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(comserv)
        }
        if (id == R.id.logout){
            MySharedPreferencesManager().removeStringValue(Constants.SHAREDPREF_AUTH_TOKEN)
            MySharedPreferencesManager().removeStringValue(Constants.SHAREDPREF_USER_ROLE)
            val logout: Intent = Intent(MyApp.instance, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(logout)
            activity?.finish()
        }
        return super.onOptionsItemSelected(item)
    }

}
