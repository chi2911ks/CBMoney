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
    NavItem.Home, NavItem.Reports, NavItem.Budget, NavItem.Profile
)

//@Preview(showBackground = true)
//@Composable
//private fun BottomNavigationPreview() {
//    BottomNavigation()
//}

@Composable
fun BottomNavigation(navController: NavController, modifier: Modifier = Modifier) {
    val navHostController = navController

    val backStackEntry = navHostController.currentBackStackEntryAsState()

    val currentRoute = backStackEntry.value?.destination?.route

    BottomAppBar(
        modifier = modifier.clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)),
        containerColor = Color.White
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
                        text = stringResource(id = item.labelRes),
                    )
                },
                icon = {
                    Icon(
                        painter = painterResource(item.iconRes), contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .background(Color.Transparent),

                    )
                })
        }

    }
}

