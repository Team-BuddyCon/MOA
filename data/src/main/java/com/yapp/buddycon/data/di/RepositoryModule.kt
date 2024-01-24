package com.yapp.buddycon.data.di

import com.yapp.buddycon.data.repository.AuthRepositoryImpl
import com.yapp.buddycon.data.repository.TokenRepositoryImpl
import com.yapp.buddycon.data.repository.remote.AvailableGifticonRepositoryImpl
import com.yapp.buddycon.domain.repository.AuthRepository
import com.yapp.buddycon.domain.repository.AvailableGifticonRepository
import com.yapp.buddycon.domain.repository.TokenRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    @Singleton
    fun bindTokenRepository(
        tokenRepositoryImpl: TokenRepositoryImpl
    ): TokenRepository

    @Binds
    @Singleton
    fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    fun bindAvailableGifticonRepository(
        availableGifticonRepositoryImpl: AvailableGifticonRepositoryImpl
    ): AvailableGifticonRepository
}
