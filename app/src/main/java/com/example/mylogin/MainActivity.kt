package com.example.mylogin

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.mylogin.data.LogoFragment

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
val logofrga=LogoFragment()
        val login=LoginFragment()

                Handler().postDelayed({
            val loginFragment = LoginFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.flfragment,login)
                .commit()
        }, 3000)
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flfragment,LogoFragment())
            commit()
        }


        }
    }




