package com.example.mylogin.userViewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel

import androidx.lifecycle.viewModelScope
import com.example.mylogin.Repository.UserRepository
import com.example.mylogin.data.User
import com.example.mylogin.data.UserDatabase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: UserRepository


    init {
        val userdao = UserDatabase.getDatabase(application).userDao()
        repository = UserRepository(userdao)

    }


    fun addUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(user)
        }
    }
    fun updatePassword(username: String?, newPassword: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updatePassword(username, newPassword)
        }
    }

   suspend fun checkuser(username: String, password: String):User? {
       return withContext(Dispatchers.IO) {
           repository.checkuser(username, password)
       }
   }
       suspend fun getUserbyUsername(username: String):User? {
           return withContext(Dispatchers.IO) {
               repository.getUserbyUsername(username)
           }

   }
}




