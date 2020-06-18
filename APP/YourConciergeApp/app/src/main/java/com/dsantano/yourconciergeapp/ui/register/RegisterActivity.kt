package com.dsantano.yourconciergeapp.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.lifecycle.Observer
import butterknife.ButterKnife
import com.dsantano.yourconciergeapp.R
import com.dsantano.yourconciergeapp.api.response.register.SendToRegister
import com.dsantano.yourconciergeapp.common.MyApp
import com.dsantano.yourconciergeapp.common.Resource
import com.dsantano.yourconciergeapp.ui.login.LoginActivity
import com.dsantano.yourconciergeapp.viewmodel.AuthViewModel
import kotlinx.android.synthetic.main.activity_register.*
import javax.inject.Inject

class RegisterActivity : AppCompatActivity() {

    @Inject
    lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        (applicationContext as MyApp).getApplicationComponent().inject(this)

        ButterKnife.bind(this)

        buttonDoRegister.setOnClickListener{ v ->
            authViewModel.doRegister(
                SendToRegister(
                    editTextUsernameRegister.text.toString(),
                    editTextFullNameRegister.text.toString(),
                    editTextFloorRegister.text.toString(),
                    editTextNumberRegister.text.toString(),
                    editTextPasswordRegister.text.toString(),
                    editTextConfirmPasswordRegister.text.toString()
                )
            )

            authViewModel.register.observe(this, Observer {
                when (it) {
                    is Resource.Error -> {
                        Handler().postDelayed({
                            Toast.makeText(MyApp.instance,"Error at register", Toast.LENGTH_SHORT).show()
                        }, 2000)
                    }
                    is Resource.Success -> {
                        Toast.makeText(MyApp.instance, "You are now registed", Toast.LENGTH_SHORT).show()
                        val register: Intent = Intent(MyApp.instance, LoginActivity::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        }
                        startActivity(register)
                        finish()
                    }
                    is Resource.Loading -> {
                    }
                }
            })
        }
    }
}
