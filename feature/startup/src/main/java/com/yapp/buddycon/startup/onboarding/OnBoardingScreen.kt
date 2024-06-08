package com.yapp.buddycon.startup.onboarding

import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.yapp.buddycon.designsystem.R
import com.yapp.buddycon.designsystem.component.button.BuddyConButton
import com.yapp.buddycon.designsystem.theme.BuddyConTheme
import com.yapp.buddycon.designsystem.theme.Grey50
import com.yapp.buddycon.designsystem.theme.Grey60
import com.yapp.buddycon.designsystem.theme.Grey90
import com.yapp.buddycon.designsystem.theme.Paddings
import com.yapp.buddycon.designsystem.theme.Pink100
import kotlinx.coroutines.launch

private data class OnBoardingItem(
    @DrawableRes val drawableRes: Int,
    val title: String,
    val description: String
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen(
    onNavigateToLogin: () -> Unit = {}
) {
    val items = listOf(
        OnBoardingItem(
            drawableRes = R.drawable.ic_onboarding_gifticon,
            title = stringResource(R.string.startup_onboarding_gifticon_title),
            description = stringResource(R.string.startup_onboarding_gifticon_description)
        ),
        OnBoardingItem(
            drawableRes = R.drawable.ic_onboarding_map,
            title = stringResource(R.string.startup_onboarding_map_title),
            description = stringResource(R.string.startup_onboarding_map_description)
        )
    )

    val pagerState = rememberPagerState { items.size }
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(BuddyConTheme.colors.background)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BuddyConTheme.colors.background)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) {
            OnBoardingPagerContent(
                modifier = Modifier.fillMaxSize(),
                onBoardingItem = items[it],
                pagerState = pagerState,
                onNavigateToLogin = onNavigateToLogin
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun OnBoardingPagerContent(
    modifier: Modifier = Modifier,
    onBoardingItem: OnBoardingItem,
    pagerState: PagerState,
    onNavigateToLogin: () -> Unit = {}
) {
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.weight(100f))
        Image(
            painter = painterResource(onBoardingItem.drawableRes),
            contentDescription = onBoardingItem.title,
            modifier = Modifier.size(290.dp, 300.dp),
            contentScale = ContentScale.FillBounds
        )
        Spacer(Modifier.weight(40f))
        Text(
            text = onBoardingItem.title,
            style = BuddyConTheme.typography.title01
        )
        Spacer(Modifier.weight(16f))
        Text(
            text = onBoardingItem.description,
            style = BuddyConTheme.typography.body01.copy(color = Grey60),
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.weight(88f))
        DynamicHorizontalPagerIndicator(
            pagerState = pagerState,
            pageCount = pagerState.pageCount,
            modifier = Modifier.fillMaxWidth()
        )
        Column(
            modifier = Modifier.weight(94f),
            verticalArrangement = Arrangement.Bottom
        ) {
            if (pagerState.currentPage < pagerState.pageCount - 1) {
                Row(
                    modifier = Modifier
                        .padding(bottom = 24.dp)
                        .padding(horizontal = Paddings.xlarge)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.startup_onboarding_pager_skip),
                        modifier = Modifier.clickable {
                            coroutineScope.launch {
                                pagerState.scrollToPage(pagerState.pageCount - 1)
                            }
                        },
                        style = BuddyConTheme.typography.subTitle.copy(color = Grey60)
                    )
                    Spacer(Modifier.weight(1f))
                    Text(
                        text = stringResource(R.string.startup_onboarding_pager_next),
                        modifier = Modifier.clickable {
                            coroutineScope.launch {
                                pagerState.scrollToPage(pagerState.currentPage + 1)
                            }
                        },
                        style = BuddyConTheme.typography.subTitle.copy(color = Pink100)
                    )
                }
            } else {
                BuddyConButton(
                    text = stringResource(R.string.startup_onboarding_start),
                    modifier = Modifier
                        .padding(bottom = Paddings.xlarge)
                        .padding(horizontal = Paddings.xlarge)
                        .fillMaxWidth()
                ) {
                    onNavigateToLogin()
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun DynamicHorizontalPagerIndicator(
    pagerState: PagerState,
    pageCount: Int,
    modifier: Modifier = Modifier,
    pageIndexMapping: (Int) -> Int = { it }
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val position = pageIndexMapping(pagerState.currentPage)
            (0 until pageCount).forEach { index ->
                val indicatorModifier = Modifier
                    .size(
                        width = if (index == position) 30.dp else 8.dp,
                        height = 8.dp
                    )
                    .background(
                        color = if (index == position) Grey90 else Grey50,
                        shape = CircleShape
                    )
                Box(indicatorModifier)
            }
        }
    }
}
