package com.dsantano.yourconciergeapp.ui.ticket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.lifecycle.Observer
import butterknife.ButterKnife
import com.dsantano.yourconciergeapp.R
import com.dsantano.yourconciergeapp.api.response.tickets.NewTicket
import com.dsantano.yourconciergeapp.common.Constants
import com.dsantano.yourconciergeapp.common.MyApp
import com.dsantano.yourconciergeapp.common.Resource
import com.dsantano.yourconciergeapp.viewmodel.TicketViewModel
import kotlinx.android.synthetic.main.activity_edit_ticket.*
import javax.inject.Inject

class EditTicketActivity : AppCompatActivity() {

    @Inject
    lateinit var ticketViewModel: TicketViewModel
    private var isUrgent: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_ticket)

        val ticketId= intent.getStringExtra(Constants.INTENT_ID).toString()
        val ticketCreatedAt= intent.getStringExtra(Constants.INTENT_CREATED_AT).toString()
        val ticketLastUpdated= intent.getStringExtra(Constants.INTENT_LAST_UPDATED).toString()
        val ticketIncidence= intent.getStringExtra(Constants.INTENT_INCIDENCE).toString()
        val ticketDescription= intent.getStringExtra(Constants.INTENT_DESCRIPTION).toString()
        var ticketUrgent= intent.getBooleanExtra(Constants.INTENT_URGENT, false)
        isUrgent = ticketUrgent
        val ticketForEdit= intent.getBooleanExtra(Constants.INTENT_FOR_EDIT, false)

        (applicationContext as MyApp).getApplicationComponent().inject(this)
        ButterKnife.bind(this)

        if(ticketForEdit){
            editTextIncidence.setText(ticketIncidence)
            editTextDescription.setText(ticketDescription)
            if(isUrgent){
                switchUrgentTicketEdit.isChecked = true
            }
        }
        switchUrgentTicketEdit.setOnCheckedChangeListener { buttonView, isChecked ->
            isUrgent = isChecked
        }

        buttonDoEditTicketEdit.setOnClickListener{ v ->
            if(ticketForEdit){
                doEdit(ticketId)
            } else {
                newTicket()
            }
        }
    }

    fun newTicket(){
        ticketViewModel.postNewTicket(NewTicket(editTextIncidence.text.toString(), editTextDescription.text.toString(), isUrgent))
        ticketViewModel.ticket.observe(this, Observer {
            when (it) {
                is Resource.Error -> {
                    Handler().postDelayed({
                        Toast.makeText(MyApp.instance,"Error creating the ticket",Toast.LENGTH_SHORT).show()
                    }, 2000)
                }
                is Resource.Success -> {
                    Toast.makeText(
                        MyApp.instance,
                        "Ticket created",
                        Toast.LENGTH_SHORT
                    ).show()
                    Handler().postDelayed({
                        finish()
                    }, 2000)

                }
                is Resource.Loading -> {
                }
            }
        })
    }

    fun doEdit(id: String){
        ticketViewModel.editTicketById(id, NewTicket(editTextIncidence.text.toString(), editTextDescription.text.toString(), isUrgent))
        ticketViewModel.ticket.observe(this, Observer {
            when (it) {
                is Resource.Error -> {
                    Handler().postDelayed({
                        Toast.makeText(MyApp.instance,"Error editing the ticket",Toast.LENGTH_SHORT).show()
                    }, 2000)
                }
                is Resource.Success -> {
                    Toast.makeText(
                        MyApp.instance,
                        "Ticket edited",
                        Toast.LENGTH_SHORT
                    ).show()
                    Handler().postDelayed({
                        finish()
                    }, 2000)

                }
                is Resource.Loading -> {
                }
            }
        })
    }


}
