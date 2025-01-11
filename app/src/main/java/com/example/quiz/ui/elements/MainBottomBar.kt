package com.example.quiz.ui.elements

//@Composable
//fun MainBottomBar(options: List<TabBarItem>, selectedIndex: Int) {
//    NavigationBar {
//        options.forEachIndexed { index, option ->
//            NavigationBarItem(
//                selected = selectedIndex == index,
//                onClick = {
//                    if (selectedIndex != index) {
//                        option.onClick(index)
//                    }
//                },
//                icon = {
//                    BadgedBox(badge = {
//                        if (option.badgeAmount != null) {
//                            Badge { Text(option.badgeAmount.toString()) }
//                        }
//                    }) {
//                        Icon(
//                            imageVector = if (selectedIndex == index) option.selectedIcon else option.unselectedIcon,
//                            contentDescription = option.title
//                        )
//                    }
//                })
//        }
//    }
//}