package org.example.flikrphotosearch.photo.presentation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.example.flikrphotosearch.app.config.BottomBarScreen
import org.example.flikrphotosearch.photo.presentation.home.HomeScreen
import org.example.flikrphotosearch.photo.presentation.search.SearchScreen
import org.example.flikrphotosearch.photo.presentation.searchhistory.SearchHistoryScreen
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MainScreen() {

    val bottomNavigationController = rememberNavController()

    val viewModel = koinViewModel<MainViewModel>()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {

        Scaffold(
            bottomBar = { BottomNavigationBar(bottomNavigationController) }
        ) { padding ->

            NavHost(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .windowInsetsPadding(WindowInsets.displayCutout),
                navController = bottomNavigationController,
                startDestination = BottomBarScreen.Home.route
            ) {
                composable(
                    BottomBarScreen.Home.route,
                    exitTransition = { slideOutHorizontally() },
                    popEnterTransition = { slideInHorizontally() }) {
                    HomeScreen(
                        viewModel = viewModel
                    )
                }
                composable(
                    BottomBarScreen.Search.route,
                    enterTransition = {
                        slideInHorizontally { initialOffset ->
                            initialOffset
                        }
                    },
                    exitTransition = {
                        slideOutHorizontally { initialOffset ->
                            initialOffset
                        }
                    }) {
                    SearchScreen(
                        mainViewModel = viewModel
                    )
                }
                composable(
                    BottomBarScreen.History.route,
                    enterTransition = {
                        slideInHorizontally { initialOffset ->
                            initialOffset
                        }
                    },
                    exitTransition = {
                        slideOutHorizontally { initialOffset ->
                            initialOffset
                        }
                    }) {
                    SearchHistoryScreen(
                        viewModel = viewModel
                    )
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is MainUiEffect.Navigation.SwitchScreen -> bottomNavigationController.navigate(
                    effect.toScreen.route
                ) {
                    bottomNavigationController.graph.startDestinationRoute?.let {
                        popUpTo(it)
                    }
                    launchSingleTop = true
                }

                is MainUiEffect.Navigation.Pop -> {
                    when (effect.fromScreen) {

                        BottomBarScreen.Search, BottomBarScreen.History -> bottomNavigationController.navigate(
                            BottomBarScreen.Home.route
                        ) {
                            bottomNavigationController.graph.startDestinationRoute?.let {
                                popUpTo(it)
                            }
                            launchSingleTop = true
                        }

                        BottomBarScreen.Home -> bottomNavigationController.navigateUp()
                    }
                }
            }
        }
    }
}

@Composable
private fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Search,
        BottomBarScreen.History
    )

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { screen ->
            NavigationBarItem(
                icon = {
                    Icon(
                        screen.tabImageVector,
                        contentDescription = "${screen.label} bottom bar icon"
                    )
                },
                label = { Text(text = screen.label) },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        navController.graph.startDestinationRoute?.let {
                            popUpTo(it)
                        }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}