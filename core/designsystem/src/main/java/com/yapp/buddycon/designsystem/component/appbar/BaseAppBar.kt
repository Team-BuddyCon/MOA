package com.yapp.buddycon.designsystem.component.appbar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.yapp.buddycon.designsystem.R
import com.yapp.buddycon.designsystem.theme.BuddyConTheme
import com.yapp.buddycon.designsystem.theme.Paddings
import com.yapp.buddycon.designsystem.theme.Pink100

private val AppbarHeight = 52.dp
private val AppbarIconSize = 24.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BaseAppBar(buddyConAppBars: BuddyConAppBars) {
    TopAppBar(
        title = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                buddyConAppBars.title?.let { title ->
                    Text(
                        text = title,
                        modifier = Modifier.fillMaxWidth(),
                        style = if (buddyConAppBars is BuddyConAppBars.WithNotification) {
                            BuddyConTheme.typography.title02
                        } else {
                            BuddyConTheme.typography.subTitle
                        },
                        textAlign = if (buddyConAppBars is BuddyConAppBars.WithNotification) {
                            TextAlign.Start
                        } else {
                            TextAlign.Center
                        }
                    )
                }
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(AppbarHeight),
        navigationIcon = {
            when (buddyConAppBars) {
                is BuddyConAppBars.WithBack,
                is BuddyConAppBars.WithBackAndEdit -> {
                    Box(
                        modifier = Modifier
                            .padding(start = Paddings.large)
                            .fillMaxHeight(),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_left_arrow),
                            contentDescription = buddyConAppBars.title,
                            modifier = Modifier
                                .size(AppbarIconSize)
                                .clickable {
                                    (buddyConAppBars as? BuddyConAppBars.WithBack)?.onBack?.invoke()
                                    (buddyConAppBars as? BuddyConAppBars.WithBackAndEdit)?.onBack?.invoke()
                                },
                            tint = BuddyConTheme.colors.onTopAppBarColor
                        )
                    }
                }

                else -> Unit
            }
        },
        actions = {
            Box(
                modifier = Modifier
                    .padding(end = Paddings.xlarge)
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                when (buddyConAppBars) {
                    is BuddyConAppBars.WithNotification -> {
                        Icon(
                            painter = painterResource(R.drawable.ic_notification_sel),
                            contentDescription = buddyConAppBars.title,
                            modifier = Modifier
                                .size(AppbarIconSize)
                                .clickable { buddyConAppBars.action?.invoke() },
                            tint = Color.Unspecified
                        )
                    }

                    is BuddyConAppBars.ForSetting -> {
                        Icon(
                            painter = painterResource(R.drawable.ic_notification),
                            contentDescription = buddyConAppBars.title,
                            modifier = Modifier
                                .size(AppbarIconSize)
                                .clickable { buddyConAppBars.action?.invoke() },
                            tint = Color.Unspecified
                        )
                    }

                    else -> {
                        Text(
                            text = stringResource(R.string.top_appbar_edit),
                            style = BuddyConTheme.typography.subTitle.copy(
                                color = if (buddyConAppBars is BuddyConAppBars.WithBackAndEdit) {
                                    Pink100
                                } else {
                                    Color.Transparent
                                }
                            ),
                            modifier = Modifier.then(
                                if (buddyConAppBars is BuddyConAppBars.WithBackAndEdit) {
                                    Modifier.clickable {
                                        buddyConAppBars.onEdit?.invoke()
                                    }
                                } else Modifier
                            )
                        )
                    }
                }
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = BuddyConTheme.colors.topAppBarColor
        )
    )
}

@Composable
fun TopAppBarWithBack(
    title: String,
    onBack: (() -> Unit)? = null
) {
    BaseAppBar(
        buddyConAppBars = BuddyConAppBars.WithBack(
            title = title,
            onBack = onBack
        )
    )
}

@Composable
fun TopAppBarWithBackAndEdit(
    title: String,
    onEdit: (() -> Unit)? = null,
    onBack: (() -> Unit)? = null
) {
    BaseAppBar(
        buddyConAppBars = BuddyConAppBars.WithBackAndEdit(
            title = title,
            onEdit = onEdit,
            onBack = onBack
        )
    )
}

@Composable
fun TopAppBarWithNotification(
    title: String,
    action: (() -> Unit)? = null
) {
    BaseAppBar(
        buddyConAppBars = BuddyConAppBars.WithNotification(
            title = title,
            action = action
        )
    )
}

@Composable
fun TopAppBarForSetting(
    action: (() -> Unit)? = null
) {
    BaseAppBar(
        buddyConAppBars = BuddyConAppBars.ForSetting(
            title = null,
            action = action
        )
    )
}

fun getTopAppBarHeight() = AppbarHeight
