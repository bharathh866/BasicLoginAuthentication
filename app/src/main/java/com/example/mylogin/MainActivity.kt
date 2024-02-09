package com.example.mylogin

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils.replace
import androidx.datastore.preferences.preferencesDataStore

import androidx.lifecycle.lifecycleScope
import com.example.mylogin.Repository.Userpreferencesrepository
import com.example.mylogin.data.LogoFragment

import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
class MainActivity : AppCompatActivity() {
  lateinit var userPreferencerepo: Userpreferencesrepository

    private val Context.dataStore by preferencesDataStore(
        name = "user"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val logofrga = LogoFragment()
        val login = LoginFragment()
        userPreferencerepo = Userpreferencesrepository(dataStore)
        showLogoFragment()


        observeUserLoginState()
    }
    private fun showLogoFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.flfragment, LogoFragment())
            .commit()

        // Delay for 3 seconds and then replace with appropriate fragment
        lifecycleScope.launch {
            delay(3000)
            LogoFragment()
        }
    }
    fun observeUserLoginState() {
        lifecycleScope.launch {
            userPreferencerepo.userLoggedInStateFlow.collect { isLoggedIn ->
                if (isLoggedIn) {

                    supportFragmentManager.beginTransaction()
                        .replace(R.id.flfragment, MyNavdrawer())
                        .commit()
                } else {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.flfragment, LoginFragment())
                        .commit()
                }
            }
        }
    }

    private fun navigateToLoggedOutState() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.flfragment, LoginFragment())
            .commit()
    }

}