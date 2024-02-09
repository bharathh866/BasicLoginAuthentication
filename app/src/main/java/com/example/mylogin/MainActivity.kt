package com.example.mylogin

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.datastore.preferences.preferencesDataStore

import androidx.lifecycle.lifecycleScope
import com.example.mylogin.Repository.Userpreferencesrepository
import com.example.mylogin.data.LogoFragment

import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
class MainActivity : AppCompatActivity() {
    private lateinit var userPreferencerepo:Userpreferencesrepository
    private lateinit var userPreferencesJob: Job
    private val Context.dataStore by preferencesDataStore(
        name = "user"
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val logofrga=LogoFragment()
        val login=LoginFragment()
        userPreferencerepo = Userpreferencesrepository(dataStore)

        userPreferencesJob = lifecycleScope.launch {
            userPreferencerepo.userPreferencesFlow.collect { showCompleted->
                if (showCompleted) {
                    Handler().postDelayed({

                        supportFragmentManager.beginTransaction()
                            .replace(R.id.flfragment,LogoFragment())
                            .commit()
                    }, 3000)
                    navigateToLoggedOutState()
                } else {
                    navigateToLoggedInState()
                }
            }
        }


        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flfragment,LogoFragment())
            commit()
        }


    }

    private fun navigateToLoggedInState() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.flfragment,MyNavdrawer())
            .commit()
    }

    private fun navigateToLoggedOutState() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.flfragment,LoginFragment())
            .commit()
    }

}