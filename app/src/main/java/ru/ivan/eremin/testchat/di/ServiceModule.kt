package ru.ivan.eremin.testchat.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.ivan.eremin.testchat.data.service.auth.AuthorizationService

@InstallIn(SingletonComponent::class)
@Module
class ServiceModule {

    @Provides
    fun provideAuthorizationService(
        retrofit: Retrofit
    ): AuthorizationService {
        return retrofit.create(AuthorizationService::class.java)
    }
}