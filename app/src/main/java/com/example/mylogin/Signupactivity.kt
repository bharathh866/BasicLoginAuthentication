package com.example.mylogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import com.example.mylogin.data.User
import com.example.mylogin.userViewmodel.UserViewModel

lateinit var mUserModel:UserViewModel
lateinit var editTextpersonfirst: EditText
lateinit var editTextpersonlast: EditText
lateinit var editTextpersonemail: EditText
lateinit var editTextpersonpassword: EditText
lateinit var signupbtn: Button
class Signupactivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signupactivity)
        mUserModel = UserViewModel(application)
        editTextpersonfirst = findViewById(R.id.etfirst)
        editTextpersonlast = findViewById(R.id.etlast)
        editTextpersonemail = findViewById(R.id.etmail)
        editTextpersonpassword = findViewById(R.id.etpass)
         signupbtn=findViewById(R.id.signupbtn)
        signupbtn.setOnClickListener {
            insertDatatoDatabase()

//            Handler().postDelayed({
//
//                val intent = Intent(this, LoginActivity::class.java)
//                startActivity(intent)
//                finish()
//            }, 3000)
        }
    }
    fun insertDatatoDatabase() {
        val firstName = editTextpersonfirst.text.toString()
        val lastName = editTextpersonlast.text.toString()
        val email = editTextpersonemail.text.toString()
        val password = editTextpersonpassword.text.toString()

        if (inputcheck(firstName, lastName, email, password)) {

            val user = User(0, firstName, lastName, email, password)
            if(isPasswordValid(password)) {
                mUserModel.addUser(user)
                val intent = Intent(this,LoginActivity::class.java)
                startActivity(intent)
            }
            else{
                Toast.makeText(this,"at least one special character and max 8 char",Toast.LENGTH_SHORT).show()
            }

        }
        else{
            Toast.makeText(this,"Enter all the fields",Toast.LENGTH_SHORT).show()
        }
    }
       fun inputcheck(firstName: String, lastName: String, email: String,password:String): Boolean {
            return !(TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName) || TextUtils.isEmpty(
              email) || TextUtils.isEmpty(password)
            )
        }
    fun isPasswordValid(password: String): Boolean {
        val pattern = Regex("^(?=.*[!@#\$%^&*()-+=])(?=\\S+$).{1,8}$")
        return pattern.matches(password)
    }

}

