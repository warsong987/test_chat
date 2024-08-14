package ru.ivan.eremin.testchat.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.ivan.eremin.testchat.database.dao.AuthDao
import ru.ivan.eremin.testchat.database.entity.AuthDb

@Database(
    entities = [
        AuthDb::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase(){
    companion object {
        const val NAME_DATABASE = "chat.db"
    }

    abstract fun authDao(): AuthDao
}