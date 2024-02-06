package com.example.mylogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button

import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.mylogin.data.Resetpassword
import com.example.mylogin.userViewmodel.UserViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

lateinit var username:EditText
lateinit var ft:TextView
lateinit var password:EditText
lateinit var mainn:Button
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        username=findViewById(R.id.loguser)
        password=findViewById(R.id.logpass)
        mUserModel = ViewModelProvider(this).get(UserViewModel::class.java)

        val sigtext=findViewById<TextView>(R.id.signtext)
        mainn=findViewById(R.id.signinn)
        mainn.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                validateuser()
            }
        }
ft=findViewById(R.id.forgettext)
            ft.setOnClickListener {
                val intent = Intent(this,Resetpassword::class.java)

                startActivity(intent)
            }

        sigtext.setOnClickListener {

                val intent = Intent(this, Signupactivity::class.java)

                startActivity(intent)
        }
    }
 suspend fun validateuser() {
val username= username.text.toString()
    val pass= password.text.toString()

        val verifyuser = mUserModel.checkuser(username, pass)
        if(verifyuser !=null) {
            if (inputcheck(username, pass)) {
                val intent = Intent(this, MyNavdrawer::class.java)
                startActivity(intent)
            }
        }

        }
}
    fun inputcheck( email: String,password:String): Boolean {
        return !( TextUtils.isEmpty(email) || TextUtils.isEmpty(password)
                )
    }


