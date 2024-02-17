package com.yapp.buddycon.network.di

import com.yapp.buddycon.domain.repository.AuthRepository
import com.yapp.buddycon.domain.repository.TokenRepository
import com.yapp.buddycon.network.BuildConfig
import com.yapp.buddycon.network.di.qualifiers.BuddyConInterceptorQualifier
import com.yapp.buddycon.network.di.qualifiers.HttpLoggingInterceptorQualifier
import com.yapp.buddycon.network.interceptor.BuddyConInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InterceptorModule {
    @HttpLoggingInterceptorQualifier
    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): Interceptor =
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }

    @BuddyConInterceptorQualifier
    @Provides
    @Singleton
    fun provideBuddyConInterceptor(
        authRepository: AuthRepository,
        tokenRepository: TokenRepository
    ): Interceptor =
        BuddyConInterceptor(authRepository, tokenRepository)
}
