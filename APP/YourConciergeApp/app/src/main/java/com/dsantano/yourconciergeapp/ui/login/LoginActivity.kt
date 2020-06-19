package com.dsantano.yourconciergeapp.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import butterknife.ButterKnife
import com.dsantano.yourconciergeapp.R
import com.dsantano.yourconciergeapp.api.response.login.SendToLogin
import com.dsantano.yourconciergeapp.common.Constants
import com.dsantano.yourconciergeapp.common.MyApp
import com.dsantano.yourconciergeapp.common.MySharedPreferencesManager
import com.dsantano.yourconciergeapp.common.Resource
import com.dsantano.yourconciergeapp.ui.MainActivity
import com.dsantano.yourconciergeapp.ui.register.RegisterActivity
import com.dsantano.yourconciergeapp.viewmodel.AuthViewModel
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {

    @Inject
    lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        (applicationContext as MyApp).getApplicationComponent().inject(this)

        ButterKnife.bind(this)

        val token: String? = MySharedPreferencesManager().getSharedPreferences().getString(Constants.SHAREDPREF_AUTH_TOKEN, "")

        /*if (!token.isNullOrEmpty()) {
            val doLogin: Intent = Intent(MyApp.instance, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(doLogin)
            finish()
        }*/

        buttonDoLogin.setOnClickListener{ v ->
            authViewModel.doLogin(SendToLogin(editTextUsernameLogin.text.toString(), editTextPasswordLogin.text.toString()))
            authViewModel.login.observe(this, Observer {
                when (it) {
                is Resource.Error -> {
                    progressBarLogin.visibility = View.INVISIBLE
                    Handler().postDelayed({
                        cardViewLogin.visibility = View.VISIBLE
                        Toast.makeText(MyApp.instance,"Error at login",Toast.LENGTH_SHORT).show()
                    }, 2000)
                }
                is Resource.Success -> {
                    progressBarLogin.visibility = View.GONE
                    cardViewLogin.visibility = View.INVISIBLE

                    MySharedPreferencesManager().setStringValue(Constants.SHAREDPREF_AUTH_TOKEN, it.data!!.token)
                    MySharedPreferencesManager().setStringValue(Constants.SHAREDPREF_USER_ROLE, it.data.user.roles)

                    Toast.makeText(MyApp.instance, "Succesfull login", Toast.LENGTH_SHORT).show()

                    val login: Intent = Intent(MyApp.instance, MainActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                    startActivity(login)
                    finish()

                }
                is Resource.Loading -> {
                    progressBarLogin.visibility = View.VISIBLE
                    cardViewLogin.visibility = View.INVISIBLE
                }
            }

            })
        }

        buttonRegister.setOnClickListener{v ->
            val register: Intent = Intent(MyApp.instance, RegisterActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(register)
        }


    }
}
