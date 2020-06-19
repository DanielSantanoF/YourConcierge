package com.dsantano.yourconciergeapp.ui.profile

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
import com.dsantano.yourconciergeapp.api.response.users.User
import com.dsantano.yourconciergeapp.common.Constants
import com.dsantano.yourconciergeapp.common.MyApp
import com.dsantano.yourconciergeapp.common.MySharedPreferencesManager
import com.dsantano.yourconciergeapp.common.Resource
import com.dsantano.yourconciergeapp.ui.login.LoginActivity
import com.dsantano.yourconciergeapp.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_profile.*
import javax.inject.Inject

class ProfileActivity : AppCompatActivity() {

    @Inject
    lateinit var userViewModel: UserViewModel
    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        (applicationContext as MyApp).getApplicationComponent().inject(this)
        ButterKnife.bind(this)

        loadProfile()

    }

    fun loadProfile(){
        userViewModel.getMe()
        userViewModel.user.observe(this, Observer {
            when (it) {
                is Resource.Error -> {
                    progressBarProfile.visibility = View.VISIBLE
                    Handler().postDelayed({
                        cardViewProfile.visibility = View.INVISIBLE
                        Toast.makeText(MyApp.instance,"Error loading profile",Toast.LENGTH_SHORT).show()
                    }, 2000)
                }
                is Resource.Success -> {
                    user = it.data
                    progressBarProfile.visibility = View.GONE
                    textViewFullName.text = it.data!!.fullName
                    textViewUsername.text = it.data.username
                    textViewFloor.text = "Floor: " + it.data.floor
                    textViewNumber.text = "Number: " + it.data.number
                    cardViewProfile.visibility = View.VISIBLE
                }
                is Resource.Loading -> {
                    progressBarProfile.visibility = View.VISIBLE
                    cardViewProfile.visibility = View.INVISIBLE

                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_profile, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item!!.itemId
        if (id == R.id.edit_profile){
            var intent = Intent(MyApp.instance, EditProfileActivity::class.java).apply{
                putExtra(Constants.INTENT_ID, user?.id)
                putExtra(Constants.INTENT_FULLNAME, user?.fullName)
                putExtra(Constants.INTENT_USERNAME, user?.username)
                putExtra(Constants.INTENT_FLOOR, user?.floor)
                putExtra(Constants.INTENT_NUMBER, user?.number)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            MyApp.instance.startActivity(intent)
        }
        if (id == R.id.logout_profile){
            MySharedPreferencesManager().removeStringValue(Constants.SHAREDPREF_AUTH_TOKEN)
            MySharedPreferencesManager().removeStringValue(Constants.SHAREDPREF_USER_ROLE)
            val logout: Intent = Intent(MyApp.instance, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(logout)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        loadProfile()
    }

}
