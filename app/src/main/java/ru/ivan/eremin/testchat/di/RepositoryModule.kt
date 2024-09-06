package ru.ivan.eremin.testchat.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.ivan.eremin.testchat.data.repository.auth.AuthorizationRepositoryImpl
import ru.ivan.eremin.testchat.domain.authorization.authorization.AuthorizationRepository

@InstallIn(SingletonComponent::class)
@Module
interface RepositoryModule {

    @Binds
    fun authorizationRepository(repository: AuthorizationRepositoryImpl): AuthorizationRepository

}