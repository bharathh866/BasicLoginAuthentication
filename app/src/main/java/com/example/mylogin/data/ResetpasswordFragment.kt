package com.example.mylogin.data

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
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
    }
}
