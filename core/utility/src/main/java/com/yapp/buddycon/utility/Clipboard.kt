package com.yapp.buddycon.utility

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import android.widget.Toast
import com.yapp.buddycon.designsystem.R

fun copyInClipBoard(
    context: Context,
    text: String
) {
    val manager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("Address", text)
    manager.setPrimaryClip(clip)

    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2) {
        Toast.makeText(context, context.getString(R.string.copy_complete), Toast.LENGTH_SHORT).show()
    }
}
