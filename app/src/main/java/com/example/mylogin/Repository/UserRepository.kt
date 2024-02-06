package com.example.mylogin.Repository

import com.example.mylogin.data.User
import com.example.mylogin.data.Userdao

class UserRepository(private val userdao: Userdao) {

    suspend fun addUser(user: User){
                userdao.addUser(user)
    }
    fun updatePassword(username: String?, newPassword: String?) {
        userdao.updatePassword(username, newPassword)
    }

suspend fun checkuser(username:String,password:String):User?{
    return userdao.checkuser(username,password)

}
    suspend fun getUserbyUsername(username:String):User?{
        return userdao.getUserByUsername(username)
    }

}