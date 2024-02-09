package com.example.mylogin.data

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.mylogin.LoginFragment

import com.example.mylogin.MyNavdrawer
import com.example.mylogin.R
import com.example.mylogin.userViewmodel.UserViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

lateinit var entusername: EditText
lateinit var passt: EditText
lateinit var resett: Button
lateinit var conft: EditText

class ResetpasswordFragment(private val mUserModel: UserViewModel): Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_resetpassword, container, false)
        entusername = view.findViewById(R.id.forpas)
        passt = view.findViewById(R.id.entpass)
        conft = view.findViewById(R.id.confpass)
        resett = view.findViewById(R.id.resetpass)

        entusername.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val username = entusername.text.toString()
                if (username.isNotEmpty()) {
                    GlobalScope.launch(Dispatchers.Main) {
                        checkUsername(username)
                        entusername.isEnabled = false
                    }
                }
            }
        }

        resett.setOnClickListener {
            val username = entusername.text.toString()

            GlobalScope.launch(Dispatchers.Main) {
                checkUsername(username)
            }
            val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.flfragment, LoginFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()

            // Remove the reset password fragment from the back stack
            requireActivity().supportFragmentManager.popBackStack(
                null,
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )

        }

        return view
    }

    private fun updatePassword(username: String) {
        if (conft.text.toString() == passt.text.toString()) {
            val newpass = conft.text.toString()
            mUserModel.updatePassword(username, newpass)

        }
    }

    private suspend fun checkUsername(username: String) {
        val checkuser = mUserModel.getUserbyUsername(username)
        if (checkuser != null) {
            updatePassword(username)

        }
        else {
            Toast.makeText(context, "Username not found", Toast.LENGTH_SHORT).show()
        }

    }
}
