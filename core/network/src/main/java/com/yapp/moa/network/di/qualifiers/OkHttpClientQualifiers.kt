package com.yapp.moa.network.di.qualifiers

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LoginClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BuddyConClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class KakaoClient
