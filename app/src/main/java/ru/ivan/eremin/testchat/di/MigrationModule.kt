package ru.ivan.eremin.testchat.di

import androidx.room.migration.Migration
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.Multibinds
import ru.ivan.eremin.testchat.di.qualifier.database.AppDatabase

@Module
@InstallIn(SingletonComponent::class)
interface MigrationModule {
    @Multibinds
    @AppDatabase
    fun providersMigration(): Set<Migration>
}