package com.yapp.buddycon.designsystem.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**  */
@Composable
fun BottomFullButton(
    onButtonClick: () -> Unit,
    buttonBackgroundColor: Color = Color(0xFFFF4E6E),
    buttonTextColor: Color = Color.White,
    buttonText: String = "버튼"
) {
    Button(
        onClick = { onButtonClick() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonBackgroundColor,
            contentColor = buttonTextColor
        ),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        // todo - 버튼 text 폰트 및 사이즈 설정하기
        Text(text = buttonText)
    }
}