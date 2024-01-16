package com.yapp.buddycon.data.di

import com.yapp.buddycon.data.api.GiftiConService
import com.yapp.buddycon.data.di.qualifiers.BuddyConClient
import com.yapp.buddycon.data.di.qualifiers.BuddyConRetrofit
import com.yapp.buddycon.data.di.qualifiers.HttpLoggingInterceptorQualifier
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "http://43.202.14.1:8080/"

    @BuddyConClient
    @Provides
    @Singleton
    fun provideBuddyConClient(
        @HttpLoggingInterceptorQualifier httpLoggingInterceptor: Interceptor,
        //@BuddyConInterceptorQualifier buddyConInterceptor: Interceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            //.addInterceptor(buddyConInterceptor)
            .build()

    @BuddyConRetrofit
    @Provides
    @Singleton
    fun provideBuddyConRetrofit(
        @BuddyConClient okHttpClient: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    /** api service */
    @Provides
    @Singleton
    fun provideGiftiConService(
        @BuddyConRetrofit retrofit: Retrofit
    ): GiftiConService =
        retrofit.create()
}