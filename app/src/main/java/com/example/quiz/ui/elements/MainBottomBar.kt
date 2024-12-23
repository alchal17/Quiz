package com.example.quiz.ui.elements

import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.quiz.activities.TabBarItem

@Composable
fun MainBottomBar(options: List<TabBarItem>, selectedIndex: Int) {
    NavigationBar {
        options.forEachIndexed { index, option ->
            NavigationBarItem(
                selected = selectedIndex == index,
                onClick = {
                    if (selectedIndex != index) {
                        option.onClick(index)
                    }
                },
                icon = {
                    BadgedBox(badge = {
                        if (option.badgeAmount != null) {
                            Badge { Text(option.badgeAmount.toString()) }
                        }
                    }) {
                        Icon(
                            imageVector = if (selectedIndex == index) option.selectedIcon else option.unselectedIcon,
                            contentDescription = option.title
                        )
                    }
                })
        }
    }
}