package com.yapp.buddycon.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.yapp.buddycon.navigation.base.BottomDestination
import com.yapp.buddycon.navigation.base.BuddyConDestination
import com.yapp.buddycon.navigation.gifticon.GifticonDestination
import com.yapp.buddycon.navigation.map.MapDestination
import com.yapp.buddycon.navigation.mypage.MyPageDestination

@Composable
internal fun BuddyConBottomBar(navHostController: NavHostController) {
    val bottomDestinations = listOf(
        GifticonDestination.Gifticon,
        MapDestination.Map,
        MyPageDestination.MyPage
    )

    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val hasBottomDestination = bottomDestinations.any { it.route == currentDestination?.route }

    if (hasBottomDestination) {
        NavigationBar(
            modifier = Modifier
                .height(72.dp)
                .background(Color.White)
                .graphicsLayer {
                    shape = RoundedCornerShape(
                        topStart = 25.dp,
                        topEnd = 25.dp
                    )
                    shadowElevation = 25f
                    clip = true
                },
            containerColor = Color.White,
            tonalElevation = 50.dp
        ) {
            bottomDestinations.forEach { bottomDestination ->
                BuddyConBottomBarItem(
                    navHostController = navHostController,
                    bottomDestination = bottomDestination,
                    currentDestination = currentDestination
                )
            }
        }
    }
}

@Composable
private fun RowScope.BuddyConBottomBarItem(
    navHostController: NavHostController,
    bottomDestination: BuddyConDestination,
    currentDestination: NavDestination?
) {
    require(bottomDestination is BottomDestination)
    val selected = bottomDestination.route == currentDestination?.route
    NavigationBarItem(
        selected = selected,
        onClick = {
            navHostController.navigate(bottomDestination.route) {
                launchSingleTop = true
            }
        },
        icon = {
            Icon(
                painter = painterResource(if (selected) bottomDestination.drawableSelResId else bottomDestination.drawableResId),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = Color.Unspecified
            )
        },
        modifier = Modifier.weight(1f),
        label = {
            Text(
                text = bottomDestination.route,
                color = if (selected) Color(0xFF2D2D2D) else Color(0xFF838486),
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium
            )
        },
        alwaysShowLabel = true,
        colors = NavigationBarItemDefaults.colors(
            indicatorColor = Color.White,
            selectedIconColor = Color.Transparent,
            unselectedIconColor = Color.Transparent,
            unselectedTextColor = Color.Transparent
        )
    )
}
