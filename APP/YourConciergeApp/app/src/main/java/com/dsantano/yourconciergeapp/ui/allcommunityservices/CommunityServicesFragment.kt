package com.dsantano.yourconciergeapp.ui.allcommunityservices

import android.content.Context
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

import com.dsantano.yourconciergeapp.viewmodel.CommunityServicesViewModel
import javax.inject.Inject

class CommunityServicesFragment : Fragment() {

    private var columnCount = 1
    @Inject
    lateinit var communityServicesViewModel: CommunityServicesViewModel
    private lateinit var myComServAdapter: MyCommunityServicesRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity?.applicationContext as MyApp).getApplicationComponent().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_community_services_list, container, false)
        myComServAdapter = MyCommunityServicesRecyclerViewAdapter()

        val recyclerView = view.findViewById<RecyclerView>(R.id.allCommunityServicesRocyclerView)

        with(recyclerView) {
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
            adapter = myComServAdapter
        }

        loadAllComServ()

        return view
    }

    private fun loadAllComServ(){
        communityServicesViewModel.getAllCommunityServices()
        communityServicesViewModel.communityServicesList.observe(this, Observer {
            when(it) {
                is Resource.Success -> {
                    it.data?.let { results ->
                        myComServAdapter.setData(results)
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
        loadAllComServ()
    }


}
