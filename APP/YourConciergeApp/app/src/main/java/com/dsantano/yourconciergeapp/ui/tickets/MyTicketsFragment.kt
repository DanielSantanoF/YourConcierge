package com.dsantano.yourconciergeapp.ui.tickets

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.dsantano.yourconciergeapp.R
import com.dsantano.yourconciergeapp.common.MyApp
import com.dsantano.yourconciergeapp.common.Resource

import com.dsantano.yourconciergeapp.viewmodel.TicketViewModel
import javax.inject.Inject

class MyTicketsFragment : Fragment() {

    private var columnCount = 1
    @Inject lateinit var ticketViewModel: TicketViewModel
    private lateinit var myTicketsAdapter: MyTicketRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
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

        return view
    }

}
