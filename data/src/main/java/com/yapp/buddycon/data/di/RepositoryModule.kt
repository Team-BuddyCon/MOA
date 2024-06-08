package com.yapp.buddycon.data.di

import com.yapp.buddycon.data.repository.local.TokenRepositoryImpl
import com.yapp.buddycon.data.repository.remote.AuthRepositoryImpl
import com.yapp.buddycon.data.repository.remote.AvailableGifticonRepositoryImpl
import com.yapp.buddycon.data.repository.remote.GifticonRepositoryImpl
import com.yapp.buddycon.data.repository.remote.KakaoRepositoryImpl
import com.yapp.buddycon.data.repository.remote.MemberRepositoryImpl
import com.yapp.buddycon.domain.repository.AuthRepository
import com.yapp.buddycon.domain.repository.AvailableGifticonRepository
import com.yapp.buddycon.domain.repository.GifticonRepository
import com.yapp.buddycon.domain.repository.KakaoRepository
import com.yapp.buddycon.domain.repository.MemberRepository
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
}
