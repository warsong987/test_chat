package ru.ivan.eremin.testchat.database.dao

import androidx.room.Dao
import androidx.room.Query
import ru.ivan.eremin.testchat.database.entity.AuthDb

@Dao
interface AuthDao {
    @Query("SELECT * FROM AuthDb WHERE userId=:userId")
    suspend fun getAuthInfo(userId: Long): AuthDb

    @Query("UPDATE AuthDb SET accessToken=:accessToken")
    suspend fun updateAccessToken(accessToken: String)

    @Query("DELETE FROM AuthDb WHERE userId=:userId")
    suspend fun delete(userId: Long)
}