package com.cbmoney.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.cbmoney.ui.theme.Gray
import com.cbmoney.ui.theme.GreenColor

val items = listOf(
    NavDestination.BottomNavItem.Home,
    NavDestination.BottomNavItem.Reports,
    NavDestination.BottomNavItem.Budget,
    NavDestination.BottomNavItem.Profile
)

@Composable
fun BottomNavigation(
    navController: NavController,
    modifier: Modifier = Modifier,
    isVisibility: Boolean = true
) {
    val navHostController = navController

    val backStackEntry = navHostController.currentBackStackEntryAsState()

    val currentRoute = backStackEntry.value?.destination?.route
    if (isVisibility) {
        BottomAppBar(
            modifier = modifier.clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)),
            containerColor = Color.White,
        ) {
            items.forEach { item ->
                NavigationBarItem(
                    selected = item.route == currentRoute,
                    onClick = {
                        navHostController.navigate(route = item.route) {
                            navHostController.graph.startDestinationRoute?.let {
                                popUpTo(it)
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = GreenColor,
                        selectedTextColor = GreenColor,
                        indicatorColor = Color.Transparent,
                        unselectedIconColor = Gray,
                        unselectedTextColor = Gray
                    ),

                    modifier = Modifier
                        .height(55.dp)
                        .background(Color.Transparent),
                    label = {
                        Text(
                            text = stringResource(id = item.labelResId),
                        )
                    },
                    icon = {
                        Icon(
                            painter = painterResource(item.iconResId), contentDescription = null,
                            modifier = Modifier
                                .size(24.dp)
                                .background(Color.Transparent),

                            )
                    })
            }
        }
    }

}

