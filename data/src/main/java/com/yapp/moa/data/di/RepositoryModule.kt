package com.yapp.moa.data.di

import com.yapp.moa.data.repository.local.TokenRepositoryImpl
import com.yapp.moa.data.repository.remote.AuthRepositoryImpl
import com.yapp.moa.data.repository.remote.AvailableGifticonRepositoryImpl
import com.yapp.moa.data.repository.remote.GifticonRepositoryImpl
import com.yapp.moa.data.repository.remote.KakaoRepositoryImpl
import com.yapp.moa.data.repository.remote.MemberRepositoryImpl
import com.yapp.moa.data.repository.remote.NotificationRepositoryImpl
import com.yapp.moa.domain.repository.AuthRepository
import com.yapp.moa.domain.repository.AvailableGifticonRepository
import com.yapp.moa.domain.repository.GifticonRepository
import com.yapp.moa.domain.repository.KakaoRepository
import com.yapp.moa.domain.repository.MemberRepository
import com.yapp.moa.domain.repository.NotificationRepository
import com.yapp.moa.domain.repository.TokenRepository
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

    @Binds
    @Singleton
    fun bindGifticonRepository(
        gifticonRepositoryImpl: GifticonRepositoryImpl
    ): GifticonRepository

    @Binds
    @Singleton
    fun bindKakaoRepository(
        kakaoRepositoryImpl: KakaoRepositoryImpl
    ): KakaoRepository

    @Binds
    @Singleton
    fun bindMemberRepository(
        memberRepositoryImpl: MemberRepositoryImpl
    ): MemberRepository

    @Binds
    @Singleton
    fun bindNotificationRepository(
        notificationRepositoryImpl: NotificationRepositoryImpl
    ): NotificationRepository
}
