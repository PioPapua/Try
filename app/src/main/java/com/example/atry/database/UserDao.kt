package com.example.atry.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

// Defines methods for using the User class with Room.

@Dao
interface UserDao {

    @Insert
    fun insert(user: User)

    @Update
    fun update(user: User)

    @Query("SELECT * from user_table WHERE id = :key")
    fun get(key: Int): User?

    @Query("DELETE FROM user_table")
    fun clear()

    @Query("SELECT * FROM user_table")
    fun getAllUsers(): LiveData<List<User>>

    @Query("SELECT * FROM user_table ORDER BY id DESC LIMIT 1")
    fun getLastUser(): User?

}