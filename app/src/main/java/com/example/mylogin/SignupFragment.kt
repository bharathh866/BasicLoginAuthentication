package com.example.mylogin

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

import androidx.fragment.app.Fragment
import com.example.mylogin.data.User
import com.example.mylogin.userViewmodel.UserViewModel

class SignupFragment : Fragment() {

    private lateinit var mUserModel: UserViewModel
    private lateinit var editTextpersonfirst: EditText
    private lateinit var editTextpersonlast: EditText
    private lateinit var editTextpersonemail: EditText
    private lateinit var editTextpersonpassword: EditText
    private lateinit var signupbtn: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_signupactivity, container, false)

        mUserModel = UserViewModel(requireActivity().application)
        editTextpersonfirst = view.findViewById(R.id.etfirst)
        editTextpersonlast = view.findViewById(R.id.etlast)
        editTextpersonemail = view.findViewById(R.id.etmail)
        editTextpersonpassword = view.findViewById(R.id.etpass)
        signupbtn = view.findViewById(R.id.signupbtn)

        signupbtn.setOnClickListener {
            insertDatatoDatabase()
        }

        return view
    }

    private fun insertDatatoDatabase() {
        val firstName = editTextpersonfirst.text.toString()
        val lastName = editTextpersonlast.text.toString()
        val email = editTextpersonemail.text.toString()
        val password = editTextpersonpassword.text.toString()

        if (inputcheck(firstName, lastName, email, password)) {
            val user = User(0, firstName, lastName, email, password)
            if (isPasswordValid(password)) {

                mUserModel.addUser(user)

                val fragmentTransaction =
                    requireActivity().supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.flfragment, LoginFragment())
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()


            } else {
                Toast.makeText(
                    requireContext(),
                    "Password should contain at least one special character and have a maximum length of 8 characters",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            Toast.makeText(requireContext(), "Enter all the fields", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputcheck(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ): Boolean {
        return !(TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName) || TextUtils.isEmpty(
            email
        ) || TextUtils.isEmpty(password))
    }

    private fun isPasswordValid(password: String): Boolean {
        val pattern = Regex("^(?=.*[!@#\$%^&*()-+=])(?=\\S+$).{1,8}$")
        return pattern.matches(password)

    }
}

