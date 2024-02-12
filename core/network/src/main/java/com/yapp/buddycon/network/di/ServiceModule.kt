package com.yapp.buddycon.network.di

import com.yapp.buddycon.network.di.qualifiers.BuddyConRetrofit
import com.yapp.buddycon.network.service.auth.AuthService
import com.yapp.buddycon.network.service.gifticon.GiftiConService
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
        @BuddyConRetrofit retrofit: Retrofit
    ): AuthService =
        retrofit.create()

    /** api service */
    @Provides
    @Singleton
    fun provideGiftiConService(
        @BuddyConRetrofit retrofit: Retrofit
    ): GiftiConService =
        retrofit.create()
}
