package ru.ivan.eremin.testchat.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.ivan.eremin.testchat.service.phone.PhoneInfo
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class SystemServiceModule {

    @Provides
    @Singleton
    fun providePhoneInfo(@ApplicationContext context: Context): PhoneInfo {
        return PhoneInfo.getInstance(context)
    }
}