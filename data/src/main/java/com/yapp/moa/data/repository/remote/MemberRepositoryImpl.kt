package com.yapp.moa.data.repository.remote

import com.yapp.moa.domain.repository.MemberRepository
import com.yapp.moa.network.service.member.MemberService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MemberRepositoryImpl @Inject constructor(
    private val memberService: MemberService
) : MemberRepository {
    override fun fetchLogout(): Flow<Boolean> = flow {
        emit(
            memberService.fetchLogout().status == 200
        )
    }
}
