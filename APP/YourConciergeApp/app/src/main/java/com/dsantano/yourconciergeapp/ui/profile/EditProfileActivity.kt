package com.dsantano.yourconciergeapp.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.lifecycle.Observer
import butterknife.ButterKnife
import com.dsantano.yourconciergeapp.R
import com.dsantano.yourconciergeapp.api.response.users.SendToEditUser
import com.dsantano.yourconciergeapp.common.Constants
import com.dsantano.yourconciergeapp.common.MyApp
import com.dsantano.yourconciergeapp.common.Resource
import com.dsantano.yourconciergeapp.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_edit_profile.*
import javax.inject.Inject

class EditProfileActivity : AppCompatActivity() {

    @Inject
    lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        val userId= intent.getStringExtra(Constants.INTENT_ID).toString()
        val userFullName= intent.getStringExtra(Constants.INTENT_FULLNAME).toString()
        val userUsername= intent.getStringExtra(Constants.INTENT_USERNAME).toString()
        val userFloor= intent.getStringExtra(Constants.INTENT_FLOOR).toString()
        val userNumber= intent.getStringExtra(Constants.INTENT_NUMBER).toString()

        (applicationContext as MyApp).getApplicationComponent().inject(this)
        ButterKnife.bind(this)

        editTextUsername.setText(userUsername)
        editTextFullName.setText(userFullName)
        editTextFloor.setText(userFloor)
        editTextNumber.setText(userNumber)

        buttonDoEditProfile.setOnClickListener{ v->
            userViewModel.editUserById(userId,
                SendToEditUser(
                    editTextUsername.text.toString(),
                    editTextPassword.text.toString(),
                    editTextFullName.text.toString(),
                    editTextFloor.text.toString(),
                    editTextNumber.text.toString()
                    )
            )
            userViewModel.voidResponse.observe(this, Observer {
                when (it) {
                    is Resource.Error -> {
                        Handler().postDelayed({

                            Toast.makeText(MyApp.instance,"Error editing your user",Toast.LENGTH_SHORT).show()
                        }, 2000)
                    }
                    is Resource.Success -> {
                        Toast.makeText(
                            MyApp.instance,
                            "User edited",
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
}
