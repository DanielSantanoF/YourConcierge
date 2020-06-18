package com.dsantano.yourconciergeapp.ui.tickets

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.dsantano.yourconciergeapp.R
import com.dsantano.yourconciergeapp.api.response.tickets.Ticket

import kotlinx.android.synthetic.main.fragment_my_tickets.view.*

class MyTicketRecyclerViewAdapter() : RecyclerView.Adapter<MyTicketRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener
    private var ticketList: List<Ticket> = ArrayList()

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Ticket
            /*var intent = Intent(MyApp.instance, actityofdetail::class.java).apply{
                putExtra(Constants.INTENT_ID, item.id)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            MyApp.instance.startActivity(intent)*/
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_my_tickets, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = ticketList[position]
        holder.txtIncidence.text = item.incidence
        holder.txtDate.text = item.createdAt

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = ticketList.size

    fun setData(list: List<Ticket>){
        ticketList = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val txtIncidence: TextView = mView.textViewIncidenceMyTicketList
        val txtDate: TextView = mView.textViewDateMyTicketList

    }
}
