package com.yapp.moa.network.di

import com.yapp.moa.network.di.qualifiers.BuddyConRetrofit
import com.yapp.moa.network.di.qualifiers.KakaoRetrofit
import com.yapp.moa.network.di.qualifiers.LoginRetrofit
import com.yapp.moa.network.service.auth.AuthService
import com.yapp.moa.network.service.gifticon.GiftiConService
import com.yapp.moa.network.service.kakao.KakaoService
import com.yapp.moa.network.service.member.MemberService
import com.yapp.moa.network.service.notification.NotificationService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun provideAuthService(
        @LoginRetrofit retrofit: Retrofit
    ): AuthService =
        retrofit.create()

    /** api service */
    @Provides
    @Singleton
    fun provideGiftiConService(
        @BuddyConRetrofit retrofit: Retrofit
    ): GiftiConService =
        retrofit.create()

    @Provides
    @Singleton
    fun provideKakaoService(
        @KakaoRetrofit retrofit: Retrofit
    ): KakaoService =
        retrofit.create()

    @Provides
    @Singleton
    fun provideMemberService(
        @BuddyConRetrofit retrofit: Retrofit
    ): MemberService =
        retrofit.create()

    @Provides
    @Singleton
    fun provideNotificationService(
        @BuddyConRetrofit retrofit: Retrofit
    ): NotificationService =
        retrofit.create()
}
