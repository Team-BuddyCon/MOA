package com.yapp.moa.network.di.qualifiers

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class HttpLoggingInterceptorQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BuddyConInterceptorQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class KakaoInterceptorQualifier
