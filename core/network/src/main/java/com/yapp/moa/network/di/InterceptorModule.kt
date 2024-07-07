package com.yapp.moa.network.di

import com.yapp.moa.domain.repository.AuthRepository
import com.yapp.moa.domain.repository.TokenRepository
import com.yapp.moa.network.di.qualifiers.BuddyConInterceptorQualifier
import com.yapp.moa.network.di.qualifiers.HttpLoggingInterceptorQualifier
import com.yapp.moa.network.di.qualifiers.KakaoInterceptorQualifier
import com.yapp.moa.network.interceptor.BuddyConInterceptor
import com.yapp.moa.network.interceptor.KakaoInterceptor
import com.yapp.moa.network.BuildConfig
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

    @KakaoInterceptorQualifier
    @Provides
    @Singleton
    fun provideKakaoInterceptor(): Interceptor = KakaoInterceptor()
}
