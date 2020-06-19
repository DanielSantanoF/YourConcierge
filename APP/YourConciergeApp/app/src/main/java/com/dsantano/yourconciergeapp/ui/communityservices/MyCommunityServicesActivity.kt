package com.dsantano.yourconciergeapp.ui.communityservices

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import butterknife.ButterKnife
import com.dsantano.yourconciergeapp.R
import com.dsantano.yourconciergeapp.api.response.communityservices.CommunityServicesListItem
import com.dsantano.yourconciergeapp.common.Constants
import com.dsantano.yourconciergeapp.common.MyApp
import com.dsantano.yourconciergeapp.common.MySharedPreferencesManager
import com.dsantano.yourconciergeapp.common.Resource
import com.dsantano.yourconciergeapp.ui.allcommunityservices.ShowAllCommunitySericesActivity
import com.dsantano.yourconciergeapp.viewmodel.CommunityServicesViewModel
import kotlinx.android.synthetic.main.activity_my_community_services.*
import kotlinx.android.synthetic.main.activity_profile.*
import javax.inject.Inject

class MyCommunityServicesActivity : AppCompatActivity() {

    @Inject
    lateinit var communityServicesViewModel: CommunityServicesViewModel
    private var communityServicesListItem: CommunityServicesListItem? = null
    private var isEspecific: Boolean = false
    private var especificId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_community_services)

        (applicationContext as MyApp).getApplicationComponent().inject(this)
        ButterKnife.bind(this)

        val comServId= intent.getStringExtra(Constants.INTENT_ID).toString()
        val especific= intent.getBooleanExtra(Constants.INTENT_FOR_EDIT, false)
        isEspecific = especific
        especificId = comServId

        if(especific){
            loadCommunityServiceById(comServId)
        } else {
            loadMyCommunityServices()
        }

    }

    fun loadMyCommunityServices(){
        communityServicesViewModel.getMyCommunityServices()
        communityServicesViewModel.communityService.observe(this, Observer {
            when (it) {
                is Resource.Error -> {
                    progressBarCommunityServices.visibility = View.VISIBLE
                    Handler().postDelayed({
                        cardViewComServ.visibility = View.INVISIBLE
                        Toast.makeText(MyApp.instance,"You dont have Community Services please select them",Toast.LENGTH_SHORT).show()
                        var intent = Intent(MyApp.instance, EditCommunityServicesActivity::class.java).apply{
                            putExtra(Constants.INTENT_ID, "")
                            putExtra(Constants.INTENT_TRASH_BAGS, false)
                            putExtra(Constants.INTENT_CLEAN_FLOOR, false)
                            putExtra(Constants.INTENT_FOR_EDIT, false)
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        }
                        MyApp.instance.startActivity(intent)
                    }, 2000)
                }
                is Resource.Success -> {
                    communityServicesListItem = it.data
                    progressBarCommunityServices.visibility = View.GONE
                    textViewCleanFloor.text = "You have floor to be cleaned?"
                    textViewTrash.text = "You have trash?"
                    if(it.data!!.cleanFloor){
                        imageViewCleanFloorUp.visibility = View.VISIBLE
                        imageViewCleanFloorDown.visibility = View.GONE
                    } else {
                        imageViewCleanFloorUp.visibility = View.GONE
                        imageViewCleanFloorDown.visibility = View.VISIBLE
                    }
                    if(it.data!!.trashBags){
                        imageViewTrashUp.visibility = View.VISIBLE
                        imageViewTrashDown.visibility = View.GONE
                    } else {
                        imageViewTrashUp.visibility = View.GONE
                        imageViewTrashDown.visibility = View.VISIBLE
                    }
                    textViewWelcomeToComServ.text = "Welcome to your Community Services"
                    cardViewComServ.visibility = View.VISIBLE
                }
                is Resource.Loading -> {
                    progressBarCommunityServices.visibility = View.VISIBLE
                    cardViewComServ.visibility = View.INVISIBLE

                }
            }
        })
    }

    fun loadCommunityServiceById(id: String){
        communityServicesViewModel.getCommunityServicesById(id)
        communityServicesViewModel.communityService.observe(this, Observer {
            when (it) {
                is Resource.Error -> {
                    progressBarCommunityServices.visibility = View.VISIBLE
                    Handler().postDelayed({
                        cardViewComServ.visibility = View.INVISIBLE
                        Toast.makeText(MyApp.instance,"You dont have Community Services please select them",Toast.LENGTH_SHORT).show()
                        var intent = Intent(MyApp.instance, EditCommunityServicesActivity::class.java).apply{
                            putExtra(Constants.INTENT_ID, "")
                            putExtra(Constants.INTENT_TRASH_BAGS, false)
                            putExtra(Constants.INTENT_CLEAN_FLOOR, false)
                            putExtra(Constants.INTENT_FOR_EDIT, false)
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        }
                        MyApp.instance.startActivity(intent)
                    }, 2000)
                }
                is Resource.Success -> {
                    communityServicesListItem = it.data
                    progressBarCommunityServices.visibility = View.GONE
                    textViewCleanFloor.text = "You have floor to be cleaned?"
                    textViewTrash.text = "You have trash?"
                    if(it.data!!.cleanFloor){
                        imageViewCleanFloorUp.visibility = View.VISIBLE
                        imageViewCleanFloorDown.visibility = View.GONE
                    } else {
                        imageViewCleanFloorUp.visibility = View.GONE
                        imageViewCleanFloorDown.visibility = View.VISIBLE
                    }
                    if(it.data!!.trashBags){
                        imageViewTrashUp.visibility = View.VISIBLE
                        imageViewTrashDown.visibility = View.GONE
                    } else {
                        imageViewTrashUp.visibility = View.GONE
                        imageViewTrashDown.visibility = View.VISIBLE
                    }
                    textViewWelcomeToComServ.text = "Welcome to your Community Services"
                    cardViewComServ.visibility = View.VISIBLE
                }
                is Resource.Loading -> {
                    progressBarCommunityServices.visibility = View.VISIBLE
                    cardViewComServ.visibility = View.INVISIBLE

                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val role = MySharedPreferencesManager().getSharedPreferences().getString(Constants.SHAREDPREF_USER_ROLE, "")
        if(role.equals("ADMIN")){
            menuInflater.inflate(R.menu.menu_community_services, menu)
        } else {
            menuInflater.inflate(R.menu.user_menu_community_services, menu)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item!!.itemId
        if (id == R.id.edit_com_serv){
            var intent = Intent(MyApp.instance, EditCommunityServicesActivity::class.java).apply{
                putExtra(Constants.INTENT_ID, communityServicesListItem?.id)
                putExtra(Constants.INTENT_TRASH_BAGS, communityServicesListItem?.trashBags)
                putExtra(Constants.INTENT_CLEAN_FLOOR, communityServicesListItem?.cleanFloor)
                putExtra(Constants.INTENT_FOR_EDIT, true)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            MyApp.instance.startActivity(intent)
        }
        if (id == R.id.all_com_serv){
            var allComServ = Intent(MyApp.instance, ShowAllCommunitySericesActivity::class.java).apply{
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            MyApp.instance.startActivity(allComServ)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        if(isEspecific){
            loadCommunityServiceById(especificId)
        } else {
            loadMyCommunityServices()
        }
    }

}
