package com.dsantano.yourconciergeapp.ui.ticket

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import butterknife.ButterKnife
import com.dsantano.yourconciergeapp.R
import com.dsantano.yourconciergeapp.api.response.tickets.Ticket
import com.dsantano.yourconciergeapp.common.Constants
import com.dsantano.yourconciergeapp.common.MyApp
import com.dsantano.yourconciergeapp.common.MySharedPreferencesManager
import com.dsantano.yourconciergeapp.common.Resource
import com.dsantano.yourconciergeapp.viewmodel.TicketViewModel
import kotlinx.android.synthetic.main.activity_ticket_detail.*
import javax.inject.Inject

class TicketDetailActivity : AppCompatActivity() {

    private var ticketIdOfDetail: String = ""
    private var ticket: Ticket? = null
    @Inject lateinit var ticketViewModel: TicketViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket_detail)

        val ticketId= intent.getStringExtra(Constants.INTENT_ID).toString()
        ticketIdOfDetail = ticketId
        (applicationContext as MyApp).getApplicationComponent().inject(this)

        ButterKnife.bind(this)
        loadTicketDetail(ticketId)
    }

    fun loadTicketDetail(id: String){
        ticketViewModel.getTicketById(id)
        ticketViewModel.ticket.observe(this, Observer {
            when (it) {
                is Resource.Error -> {
                    progressBarTicketDetail.visibility = View.VISIBLE
                    Handler().postDelayed({
                        textViewContentTicketDetail.visibility = View.INVISIBLE
                        textViewCreatedAtTicketDetail.visibility = View.INVISIBLE
                        textViewIncidenceTicketDetail.visibility = View.INVISIBLE
                        textViewLastUpdatedTicketDetail.visibility = View.INVISIBLE
                        Toast.makeText(MyApp.instance,"Error loading the Ticket",Toast.LENGTH_SHORT).show()
                    }, 2000)
                }
                is Resource.Success -> {
                    progressBarTicketDetail.visibility = View.GONE
                    textViewContentTicketDetail.visibility = View.VISIBLE
                    textViewCreatedAtTicketDetail.visibility = View.VISIBLE
                    textViewIncidenceTicketDetail.visibility = View.VISIBLE
                    textViewLastUpdatedTicketDetail.visibility = View.VISIBLE

                    ticket = it.data
                    textViewLastUpdatedTicketDetail.text = "Last update: " + it.data!!.lastUpdate
                    textViewCreatedAtTicketDetail.text = "Created at: " + it.data.createdAt
                    textViewIncidenceTicketDetail.text = it.data.incidence
                    textViewContentTicketDetail.text = it.data.description
                    if(it.data.urgent){
                        textViewUrgentTicketDetail.visibility = View.VISIBLE
                    }

                }
                is Resource.Loading -> {
                    progressBarTicketDetail.visibility = View.VISIBLE
                    textViewContentTicketDetail.visibility = View.INVISIBLE
                    textViewCreatedAtTicketDetail.visibility = View.INVISIBLE
                    textViewIncidenceTicketDetail.visibility = View.INVISIBLE
                    textViewLastUpdatedTicketDetail.visibility = View.INVISIBLE
                }
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_ticket_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item!!.itemId
        if (id == R.id.edit_ticket){
            var intent = Intent(MyApp.instance, EditTicketActivity::class.java).apply{
                putExtra(Constants.INTENT_ID, ticket?.id)
                putExtra(Constants.INTENT_CREATED_AT, ticket?.createdAt)
                putExtra(Constants.INTENT_LAST_UPDATED, ticket?.lastUpdate)
                putExtra(Constants.INTENT_INCIDENCE, ticket?.incidence)
                putExtra(Constants.INTENT_DESCRIPTION, ticket?.description)
                putExtra(Constants.INTENT_URGENT, ticket?.urgent)
                putExtra(Constants.INTENT_FOR_EDIT, true)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            MyApp.instance.startActivity(intent)
        }
        if (id == R.id.delete_ticket){
            ticketViewModel.deleteTicketById(ticketIdOfDetail)
            ticketViewModel.voidResponse.observe(this, Observer {
                when (it) {
                    is Resource.Error -> {
                        progressBarTicketDetail.visibility = View.INVISIBLE
                        Handler().postDelayed({
                            textViewContentTicketDetail.visibility = View.VISIBLE
                            textViewCreatedAtTicketDetail.visibility = View.VISIBLE
                            textViewIncidenceTicketDetail.visibility = View.VISIBLE
                            textViewLastUpdatedTicketDetail.visibility = View.VISIBLE
                            Toast.makeText(MyApp.instance,"Error deleting the ticket",Toast.LENGTH_SHORT).show()
                        }, 2000)
                    }
                    is Resource.Success -> {
                        progressBarTicketDetail.visibility = View.GONE
                        textViewContentTicketDetail.visibility = View.VISIBLE
                        textViewCreatedAtTicketDetail.visibility = View.VISIBLE
                        textViewIncidenceTicketDetail.visibility = View.VISIBLE
                        textViewLastUpdatedTicketDetail.visibility = View.VISIBLE
                        Toast.makeText(
                            MyApp.instance,
                            "Ticket deleted",
                            Toast.LENGTH_SHORT
                        ).show()
                        Handler().postDelayed({
                            finish()
                        }, 2000)

                    }
                    is Resource.Loading -> {
                        progressBarTicketDetail.visibility = View.VISIBLE
                        textViewContentTicketDetail.visibility = View.INVISIBLE
                        textViewCreatedAtTicketDetail.visibility = View.INVISIBLE
                        textViewIncidenceTicketDetail.visibility = View.INVISIBLE
                        textViewLastUpdatedTicketDetail.visibility = View.INVISIBLE
                    }
                }
            })
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        loadTicketDetail(ticketIdOfDetail)
    }
}
