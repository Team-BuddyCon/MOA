package com.yapp.moa.domain.repository

import kotlinx.coroutines.flow.Flow

interface MemberRepository {
    fun fetchLogout(): Flow<Boolean>
}
