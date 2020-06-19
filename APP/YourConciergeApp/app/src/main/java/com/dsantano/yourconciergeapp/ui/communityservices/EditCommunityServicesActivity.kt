package com.dsantano.yourconciergeapp.ui.communityservices

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.lifecycle.Observer
import butterknife.ButterKnife
import com.dsantano.yourconciergeapp.R
import com.dsantano.yourconciergeapp.api.response.communityservices.NewCommunityServices
import com.dsantano.yourconciergeapp.common.Constants
import com.dsantano.yourconciergeapp.common.MyApp
import com.dsantano.yourconciergeapp.common.Resource
import com.dsantano.yourconciergeapp.viewmodel.CommunityServicesViewModel
import kotlinx.android.synthetic.main.activity_edit_community_services.*
import javax.inject.Inject

class EditCommunityServicesActivity : AppCompatActivity() {

    @Inject
    lateinit var communityServicesViewModel: CommunityServicesViewModel
    private var trashBags: Boolean = false
    private var cleanFloor: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_community_services)

        val comServId= intent.getStringExtra(Constants.INTENT_ID).toString()
        var comServTrash= intent.getBooleanExtra(Constants.INTENT_TRASH_BAGS, false)
        val comServCleanFloor= intent.getBooleanExtra(Constants.INTENT_CLEAN_FLOOR, false)
        val edit= intent.getBooleanExtra(Constants.INTENT_FOR_EDIT, false)

        (applicationContext as MyApp).getApplicationComponent().inject(this)
        ButterKnife.bind(this)

        if(edit) {
            trashBags = comServTrash
            cleanFloor = comServCleanFloor
            if (trashBags) {
                switchTrashBags.isChecked = true
            }
            if (cleanFloor) {
                switchCleanFloor.isChecked = true
            }
            switchTrashBags.setOnCheckedChangeListener { buttonView, isChecked ->
                trashBags = isChecked
            }
            switchCleanFloor.setOnCheckedChangeListener { buttonView, isChecked ->
                cleanFloor = isChecked
            }
        }

        buttonDoEditComServEdit.setOnClickListener{ v->
            if(edit){
                editComServ(comServId)
            } else {
                newComServ()
            }
        }
    }

    fun editComServ(id: String){
        communityServicesViewModel.editCommunityServiceById(id, NewCommunityServices(trashBags, cleanFloor))
        communityServicesViewModel.communityService.observe(this, Observer {
            when (it) {
                is Resource.Error -> {
                    Handler().postDelayed({
                        Toast.makeText(MyApp.instance,"Error editing the Community Services", Toast.LENGTH_SHORT).show()
                    }, 2000)
                }
                is Resource.Success -> {
                    Toast.makeText(
                        MyApp.instance,
                        "Community Services edited",
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

    fun newComServ(){
        communityServicesViewModel.postNewCommunityService(NewCommunityServices(trashBags, cleanFloor))
        communityServicesViewModel.communityService.observe(this, Observer {
            when (it) {
                is Resource.Error -> {
                    Handler().postDelayed({
                        Toast.makeText(MyApp.instance,"Error creating the Community Services", Toast.LENGTH_SHORT).show()
                    }, 2000)
                }
                is Resource.Success -> {
                    Toast.makeText(
                        MyApp.instance,
                        "Community Services created",
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
