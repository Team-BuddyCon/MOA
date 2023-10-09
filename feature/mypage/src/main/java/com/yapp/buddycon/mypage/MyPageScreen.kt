package com.yapp.buddycon.mypage

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.yapp.buddycon.designsystem.component.BottomFullButton

@Composable
fun MyPageScreen() {
    Column {
        Text(text = "MyPage")

        // todo
        //      - 디자인 시스템 요소들이 앱에서 어떻게 표출되는지 볼 수 있는 별도의 화면 구성하기
        //      - 버디콘 개발자 모드를 별도로 만들고 이곳에서 볼 수 있도록?
        //      - 현재는 마이페이지 탭에서 임시로 볼 수 있도록 배치
        BottomFullButton(onButtonClick = {})
    }
}
