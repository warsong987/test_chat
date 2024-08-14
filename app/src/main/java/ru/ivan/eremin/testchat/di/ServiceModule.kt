package ru.ivan.eremin.testchat.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.ivan.eremin.testchat.data.authorization.authorization.service.AuthorizationService

@InstallIn(SingletonComponent::class)
@Module
class ServiceModule {
    @Provides
    fun authorizationService(retrofit: Retrofit): AuthorizationService =
        retrofit.create(AuthorizationService::class.java)
}