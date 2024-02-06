package com.example.mylogin.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

//Contains all methods to access the our db
@Dao
interface Userdao {
    @Insert(onConflict=OnConflictStrategy.REPLACE)
     fun addUser(user:User)

     @Query("SELECT * FROM user_table WHERE username = :username AND password = :password")
     fun checkuser(username:String,password:String):User?

    @Query("SELECT * FROM user_table WHERE username = :username")
 fun getUserByUsername(username: String): User?

    @Query("UPDATE user_table SET password = :newPassword WHERE username = :username")
    fun updatePassword(username: String?, newPassword: String?)

}


