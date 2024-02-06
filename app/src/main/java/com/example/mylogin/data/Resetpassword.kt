package com.example.mylogin.data

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.mylogin.LoginActivity
import com.example.mylogin.R
import com.example.mylogin.mUserModel
import com.example.mylogin.username
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

//import com.example.mylogin.passet

lateinit var entusername:EditText
lateinit var  passt:EditText
lateinit var  resett:Button
lateinit var  conft:EditText
class Resetpassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resetpassword)
        conft = findViewById(R.id.confpass)
        passt = findViewById(R.id.entpass)
        resett = findViewById(R.id.resetpass)
        entusername = findViewById(R.id.forpas)
        entusername.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val username = entusername.text.toString()
                if (username.isNotEmpty()) {

                    GlobalScope.launch(Dispatchers.Main) {
                        checkusername()
                        entusername.isEnabled=false
                    }
                }
            }
        }
        resett.setOnClickListener {
            val username = entusername.text.toString()
            GlobalScope.launch(Dispatchers.Main) {

                checkusername()
            }
        }
    }
        fun updatepassword(username:String) {


            if (conft.text.toString() == passt.text.toString()) {
                val newpass = conft.text.toString()
                mUserModel.updatePassword(username, newpass)

            }
        }

        suspend fun checkusername(){
            val username = entusername.text.toString()

            val checkuser = mUserModel.getUserbyUsername(username)

            if (checkuser != null) {

                updatepassword(username)

                //val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }


        }



}

