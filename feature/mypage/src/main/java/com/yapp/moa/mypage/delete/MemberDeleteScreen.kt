package com.yapp.moa.mypage.delete

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yapp.moa.designsystem.component.appbar.TopAppBarWithBack
import com.yapp.moa.designsystem.component.button.BuddyConButton
import com.yapp.moa.designsystem.component.dialog.DefaultDialog
import com.yapp.moa.designsystem.theme.BuddyConTheme
import com.yapp.moa.designsystem.theme.Grey20
import com.yapp.moa.designsystem.theme.Grey60
import com.yapp.moa.designsystem.theme.Grey90
import com.yapp.moa.designsystem.theme.Pink100
import com.yapp.moa.designsystem.R

@Composable
fun MemberDeleteScreen(
    memberDeleteViewModel: MemberDeleteViewModel = hiltViewModel(),
    onBack: () -> Unit = {}
) {
    val phrase by memberDeleteViewModel.phrase.collectAsStateWithLifecycle()
    val reason by memberDeleteViewModel.reason.collectAsStateWithLifecycle()
    val reasonText by memberDeleteViewModel.reasonText.collectAsStateWithLifecycle()
    val userName by memberDeleteViewModel.userName.collectAsStateWithLifecycle()
    var showDeleteMemberPopup by remember { mutableStateOf(false) }

    if (showDeleteMemberPopup) {
        DefaultDialog(
            dialogTitle = stringResource(R.string.setting_delete_member_popup_title),
            dismissText = stringResource(R.string.setting_delete_member_popup_dismiss),
            confirmText = stringResource(R.string.setting_delete_member_popup_confirm),
            dialogContent = stringResource(R.string.setting_delete_member_popup_content),
            onConfirm = {
                showDeleteMemberPopup = false
                // TODO 탈퇴 API
            },
            onDismissRequest = {
                showDeleteMemberPopup = false
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBarWithBack(
                title = stringResource(R.string.setting_delete_member),
                onBack = {
                    when (phrase) {
                        DeleteMemberPhrase.FIRST -> {
                            onBack()
                        }

                        DeleteMemberPhrase.SECOND -> {
                            memberDeleteViewModel.setPhrase(DeleteMemberPhrase.FIRST)
                        }

                        DeleteMemberPhrase.THIRD -> {
                            if (reason == DeleteMemberReason.ETC) {
                                memberDeleteViewModel.setPhrase(DeleteMemberPhrase.SECOND)
                            } else {
                                memberDeleteViewModel.setPhrase(DeleteMemberPhrase.FIRST)
                            }
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            BuddyConButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                text = stringResource(R.string.next),
                onClick = {
                    when (phrase) {
                        DeleteMemberPhrase.FIRST -> {
                            when (reason) {
                                DeleteMemberReason.ETC -> {
                                    memberDeleteViewModel.setPhrase(DeleteMemberPhrase.SECOND)
                                }

                                else -> {
                                    memberDeleteViewModel.setPhrase(DeleteMemberPhrase.THIRD)
                                }
                            }
                        }

                        DeleteMemberPhrase.SECOND -> {
                            memberDeleteViewModel.setPhrase(DeleteMemberPhrase.THIRD)
                        }

                        DeleteMemberPhrase.THIRD -> {
                            showDeleteMemberPopup = true
                        }
                    }
                }
            )
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { paddingValues ->
        MemberDeleteContent(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(BuddyConTheme.colors.background),
            memberDeleteViewModel = memberDeleteViewModel,
            phrase = phrase,
            reason = reason,
            reasonText = reasonText,
            userName = userName
        )
    }
}

@Composable
private fun MemberDeleteContent(
    modifier: Modifier = Modifier,
    memberDeleteViewModel: MemberDeleteViewModel = hiltViewModel(),
    phrase: DeleteMemberPhrase,
    reason: DeleteMemberReason,
    reasonText: String,
    userName: String
) {
    Column(modifier) {
        when (phrase) {
            DeleteMemberPhrase.FIRST -> {
                MemberDeleteFirstPhrase(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .padding(horizontal = 16.dp)
                        .fillMaxSize(),
                    deleteMemberReason = reason,
                    onChangeReason = {
                        memberDeleteViewModel.setReason(it)
                    }
                )
            }

            DeleteMemberPhrase.SECOND -> {
                MemberDeleteSecondPhrase(
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .padding(horizontal = 16.dp)
                        .fillMaxSize(),
                    reasonText = reasonText,
                    onChangeReasonText = {
                        memberDeleteViewModel.setReasonText(it)
                    }
                )
            }

            DeleteMemberPhrase.THIRD -> {
                MemberDeleteThirdPhrase(
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .padding(horizontal = 16.dp)
                        .fillMaxSize(),
                    userName = userName
                )
            }
        }
    }
}

@Composable
private fun MemberDeleteFirstPhrase(
    modifier: Modifier = Modifier,
    deleteMemberReason: DeleteMemberReason,
    onChangeReason: (DeleteMemberReason) -> Unit = {}
) {
    Column(modifier) {
        Text(
            text = stringResource(R.string.setting_delete_member_reason),
            style = BuddyConTheme.typography.title02
        )
        Row(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
                .height(46.dp)
                .clickable { onChangeReason(DeleteMemberReason.NOT_USED) },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = DeleteMemberReason.NOT_USED.reason,
                modifier = Modifier.weight(1f),
                style = BuddyConTheme.typography.subTitle.copy(
                    color = if (deleteMemberReason == DeleteMemberReason.NOT_USED) Pink100 else Grey90
                )
            )
            if (deleteMemberReason == DeleteMemberReason.NOT_USED) {
                Icon(
                    painter = painterResource(R.drawable.ic_check),
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = Color.Unspecified
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(46.dp)
                .clickable { onChangeReason(DeleteMemberReason.HAVE_ISSUES) },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = DeleteMemberReason.HAVE_ISSUES.reason,
                modifier = Modifier.weight(1f),
                style = BuddyConTheme.typography.subTitle.copy(
                    color = if (deleteMemberReason == DeleteMemberReason.HAVE_ISSUES) Pink100 else Grey90
                )
            )
            if (deleteMemberReason == DeleteMemberReason.HAVE_ISSUES) {
                Icon(
                    painter = painterResource(R.drawable.ic_check),
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = Color.Unspecified
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(46.dp)
                .clickable { onChangeReason(DeleteMemberReason.DONT_KNOW_HOW_TO_USE) },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = DeleteMemberReason.DONT_KNOW_HOW_TO_USE.reason,
                modifier = Modifier.weight(1f),
                style = BuddyConTheme.typography.subTitle.copy(
                    color = if (deleteMemberReason == DeleteMemberReason.DONT_KNOW_HOW_TO_USE) Pink100 else Grey90
                )
            )
            if (deleteMemberReason == DeleteMemberReason.DONT_KNOW_HOW_TO_USE) {
                Icon(
                    painter = painterResource(R.drawable.ic_check),
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = Color.Unspecified
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(46.dp)
                .clickable { onChangeReason(DeleteMemberReason.ETC) },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = DeleteMemberReason.ETC.reason,
                modifier = Modifier.weight(1f),
                style = BuddyConTheme.typography.subTitle.copy(
                    color = if (deleteMemberReason == DeleteMemberReason.ETC) Pink100 else Grey90
                )
            )
            if (deleteMemberReason == DeleteMemberReason.ETC) {
                Icon(
                    painter = painterResource(R.drawable.ic_check),
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = Color.Unspecified
                )
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun MemberDeleteSecondPhrase(
    modifier: Modifier = Modifier,
    reasonText: String,
    onChangeReasonText: (String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(modifier) {
        Text(
            text = stringResource(R.string.setting_delete_member_reason_etc),
            style = BuddyConTheme.typography.title02
        )
        BasicTextField(
            value = reasonText,
            onValueChange = {
                if (it.length <= 200) {
                    onChangeReasonText(it)
                }
            },
            modifier = Modifier
                .padding(top = 24.dp)
                .fillMaxWidth()
                .heightIn(min = 328.dp)
                .background(Grey20, shape = RoundedCornerShape(20.dp))
                .padding(24.dp),
            textStyle = BuddyConTheme.typography.body01,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                keyboardController?.hide()
            })
        ) { innerTextField ->
            if (reasonText.isEmpty()) {
                Text(
                    text = stringResource(R.string.setting_delete_member_reason_placeholder),
                    style = BuddyConTheme.typography.body01.copy(color = Grey60)
                )
            }
            innerTextField()
        }
    }
}

@Composable
private fun MemberDeleteThirdPhrase(
    modifier: Modifier = Modifier,
    userName: String
) {
    Column(modifier) {
        Text(
            text = String.format(stringResource(R.string.setting_delete_member_third_title_format), userName),
            style = BuddyConTheme.typography.title02
        )
        Row(Modifier.padding(top = 24.dp)) {
            Spacer(
                modifier = Modifier
                    .padding(8.dp)
                    .size(3.dp)
                    .background(Grey60, CircleShape)
            )
            Text(
                text = String.format(stringResource(R.string.setting_delete_member_first_guide_format), userName),
                style = BuddyConTheme.typography.body01.copy(color = Grey60)
            )
        }
        Row(Modifier.padding(top = 16.dp)) {
            Spacer(
                modifier = Modifier
                    .padding(8.dp)
                    .size(3.dp)
                    .background(Grey60, CircleShape)
            )
            Text(
                text = stringResource(R.string.setting_delete_member_second_guide),
                style = BuddyConTheme.typography.body01.copy(color = Grey60)
            )
        }
    }
}
