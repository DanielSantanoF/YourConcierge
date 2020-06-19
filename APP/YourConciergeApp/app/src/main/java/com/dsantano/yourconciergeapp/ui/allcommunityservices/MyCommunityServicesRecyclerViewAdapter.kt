package com.dsantano.yourconciergeapp.ui.allcommunityservices

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.dsantano.yourconciergeapp.R
import com.dsantano.yourconciergeapp.api.response.communityservices.CommunityServicesListItem
import com.dsantano.yourconciergeapp.common.Constants
import com.dsantano.yourconciergeapp.common.MyApp
import com.dsantano.yourconciergeapp.ui.communityservices.MyCommunityServicesActivity

import kotlinx.android.synthetic.main.fragment_community_services.view.*


class MyCommunityServicesRecyclerViewAdapter() : RecyclerView.Adapter<MyCommunityServicesRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener
    private var comServList: List<CommunityServicesListItem> = ArrayList()

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as CommunityServicesListItem
            val comserv: Intent = Intent(MyApp.instance, MyCommunityServicesActivity::class.java).apply {
                putExtra(Constants.INTENT_ID, item.id)
                putExtra(Constants.INTENT_FOR_EDIT, true)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            MyApp.instance.startActivity(comserv)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_community_services, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = comServList[position]
        holder.txtFloorNumber.text = "Floor " + item.myUser.floor + " with number " + item.myUser.number
        holder.txtUser.text = item.myUser.fullName
        if(item.trashBags){
            holder.txtTrash.text = "Trash to be picked up"
        } else {
            holder.txtTrash.text = "No trash"
        }
        if(item.cleanFloor){
            holder.txtClean.text = "Need to clean the floor"
        } else {
            holder.txtClean.text = "No need to clean"
        }

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = comServList.size

    fun setData(list: List<CommunityServicesListItem>){
        comServList = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val txtFloorNumber: TextView = mView.textViewFloorNumber
        val txtUser: TextView = mView.textViewFullNameComServList
        val txtTrash: TextView = mView.textViewTrashBagsList
        val txtClean: TextView = mView.textViewCleanFloorList

    }
}
