package com.example.mylogin

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.mylogin.Repository.Userpreferencesrepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

import com.example.mylogin.data.ResetpasswordFragment
import com.example.mylogin.userViewmodel.UserViewModel

class LoginFragment : Fragment() {

    private lateinit var username: EditText
    private lateinit var ft: TextView
    private lateinit var password: EditText
    private lateinit var mainn: Button
    private lateinit var mUserModel: UserViewModel
    private lateinit var userpreferencesrepository: Userpreferencesrepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)


        username = view.findViewById(R.id.loguser)
        password = view.findViewById(R.id.logpass)
        mUserModel = ViewModelProvider(this).get(UserViewModel::class.java)

        val sigtext = view.findViewById<TextView>(R.id.signtext)
        mainn = view.findViewById(R.id.signinn)
        mainn.setOnClickListener {


            GlobalScope.launch(Dispatchers.Main) {
                validateuser()
            }

        }
        ft = view.findViewById(R.id.forgettext)
        ft.setOnClickListener {
            val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.flfragment,ResetpasswordFragment(mUserModel))
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
        userpreferencesrepository = (requireActivity() as MainActivity).userPreferencerepo
        sigtext.setOnClickListener {
            val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.flfragment,SignupFragment())

            fragmentTransaction.commit()
        }

        return view
    }
    private suspend fun validateuser() {
        val usernameText = username.text.toString().trim()
        val passText = password.text.toString().trim()

        if (inputcheck(usernameText, passText)) {
            val verifyuser = mUserModel.checkuser(usernameText, passText)
            if (verifyuser != null) {
                loginUser()
                val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.flfragment,MyNavdrawer())
                fragmentTransaction.commit()
            } else {
                showToast("Invalid username or password")
            }
        } else {
            if (TextUtils.isEmpty(usernameText)) {
                showToast("Username cannot be empty")
            }
            if (TextUtils.isEmpty(passText)) {
                showToast("Password cannot be empty")
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun inputcheck(email: String, password: String): Boolean {
        return !(TextUtils.isEmpty(email) || TextUtils.isEmpty(password))
    }
    private fun loginUser() {
        lifecycleScope.launch(Dispatchers.IO) {
            userpreferencesrepository.loginUser("userId", username.toString())
        }
    }
}
