package ru.ivan.eremin.testchat.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.ivan.eremin.testchat.database.AppDatabase
import ru.ivan.eremin.testchat.database.dao.AuthDao
import javax.inject.Singleton
import  ru.ivan.eremin.testchat.di.qualifier.database.AppDatabase as AppDatabaseMigration

@Module
@InstallIn(SingletonComponent::class)
class DbModule {
    @Provides
    @Singleton
    fun providesAppDatabase(
        @ApplicationContext context: Context,
        @AppDatabaseMigration migration: Set<@JvmSuppressWildcards Migration>
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.NAME_DATABASE
        ).allowMainThreadQueries()
            .addMigrations(*migration.toTypedArray())
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideAuthDao(appDatabase: AppDatabase): AuthDao {
        return appDatabase.authDao()
    }
}