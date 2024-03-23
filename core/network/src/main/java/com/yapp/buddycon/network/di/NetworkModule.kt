package com.yapp.buddycon.network.di

import com.yapp.buddycon.network.di.qualifiers.BuddyConClient
import com.yapp.buddycon.network.di.qualifiers.BuddyConInterceptorQualifier
import com.yapp.buddycon.network.di.qualifiers.BuddyConRetrofit
import com.yapp.buddycon.network.di.qualifiers.HttpLoggingInterceptorQualifier
import com.yapp.buddycon.network.di.qualifiers.KakaoClient
import com.yapp.buddycon.network.di.qualifiers.KakaoInterceptorQualifier
import com.yapp.buddycon.network.di.qualifiers.KakaoRetrofit
import com.yapp.buddycon.network.di.qualifiers.LoginClient
import com.yapp.buddycon.network.di.qualifiers.LoginRetrofit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BUDDYCON_BASE_URL = "http://43.202.14.1:8080/"
    private const val KAKAO_BASE_URL = "https://dapi.kakao.com/v2/"

    @LoginClient
    @Provides
    @Singleton
    fun provideLoginClient(
        @HttpLoggingInterceptorQualifier httpLoggingInterceptor: Interceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @LoginRetrofit
    @Provides
    @Singleton
    fun provideLoginRetrofit(
        @LoginClient okHttpClient: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(BUDDYCON_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @BuddyConClient
    @Provides
    @Singleton
    fun provideBuddyConClient(
        @HttpLoggingInterceptorQualifier httpLoggingInterceptor: Interceptor,
        @BuddyConInterceptorQualifier buddyConInterceptor: Interceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(buddyConInterceptor)
            .build()

    @BuddyConRetrofit
    @Provides
    @Singleton
    fun provideBuddyConRetrofit(
        @BuddyConClient okHttpClient: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(BUDDYCON_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @KakaoClient
    @Provides
    @Singleton
    fun provideKakaoClient(
        @KakaoInterceptorQualifier interceptor: Interceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

    @KakaoRetrofit
    @Provides
    @Singleton
    fun provideKakaoRetrofit(
        @KakaoClient okHttpClient: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(KAKAO_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
}
