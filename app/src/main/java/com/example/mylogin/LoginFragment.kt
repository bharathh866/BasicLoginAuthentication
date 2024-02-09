package com.example.mylogin

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

import com.example.mylogin.data.ResetpasswordFragment
import com.example.mylogin.userViewmodel.UserViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private lateinit var username: EditText
    private lateinit var ft: TextView
    private lateinit var password: EditText
    private lateinit var mainn: Button
    private lateinit var mUserModel: UserViewModel

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

        sigtext.setOnClickListener {
            val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.flfragment,SignupFragment())

            fragmentTransaction.commit()
        }

        return view
    }

    private suspend fun validateuser() {
        val username = username.text.toString()
        val pass = password.text.toString()

        val verifyuser = mUserModel.checkuser(username, pass)
        if (verifyuser != null) {
            if (inputcheck(username, pass)) {
                val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.flfragment,MyNavdrawer())

                fragmentTransaction.commit()
            }
        }
    }

    private fun inputcheck(email: String, password: String): Boolean {
        return !(TextUtils.isEmpty(email) || TextUtils.isEmpty(password))
    }
}