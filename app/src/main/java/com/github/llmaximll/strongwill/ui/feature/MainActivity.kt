package com.github.llmaximll.strongwill.ui.feature

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.navArgument
import com.github.llmaximll.strongwill.ui.feature.NavigationKeys.Arg.WILL_TIMER_ID
import com.github.llmaximll.strongwill.ui.feature.NavigationKeys.Route.WILL_TIMER_ADD
import com.github.llmaximll.strongwill.ui.feature.NavigationKeys.Route.WILL_TIMER_DETAILS
import com.github.llmaximll.strongwill.ui.feature.NavigationKeys.Route.WILL_TIMER_LIST
import com.github.llmaximll.strongwill.ui.feature.timer_add.TimerAddDestinationScreen
import com.github.llmaximll.strongwill.ui.feature.timer_add.TimerAddViewModel
import com.github.llmaximll.strongwill.ui.feature.timer_details.TimerDetailsContract
import com.github.llmaximll.strongwill.ui.feature.timer_details.TimerDetailsDestinationScreen
import com.github.llmaximll.strongwill.ui.feature.timer_details.TimerDetailsViewModel
import com.github.llmaximll.strongwill.ui.feature.timer_edit.TimerEditContract
import com.github.llmaximll.strongwill.ui.feature.timer_edit.TimerEditDestinationScreen
import com.github.llmaximll.strongwill.ui.feature.timer_edit.TimerEditViewModel
import com.github.llmaximll.strongwill.ui.feature.timers.TimersContract
import com.github.llmaximll.strongwill.ui.feature.timers.TimersDestinationScreen
import com.github.llmaximll.strongwill.ui.feature.timers.TimersViewModel
import com.github.llmaximll.strongwill.ui.theme.StrongWillTheme
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StrongWillTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    WillApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun WillApp() {
    val navController = rememberAnimatedNavController()
    AnimatedNavHost(navController = navController, startDestination = WILL_TIMER_LIST) {
        composable(route = WILL_TIMER_LIST) {
            TimersDestination(navController = navController)
        }
        composable(
            route = WILL_TIMER_DETAILS,
            arguments = listOf(navArgument(WILL_TIMER_ID) {
                type = NavType.LongType
            })
        ) {
            TimerDetailsDestination(navController)
        }
        composable(route = WILL_TIMER_ADD) {
            TimerAddDestination(navController = navController)
        }
        composable(
            route = NavigationKeys.Route.WILL_TIMER_EDIT,
            arguments = listOf(navArgument(WILL_TIMER_ID) {
                type = NavType.LongType
            })
        ) {
            TimerEditDestination(navController = navController)
        }
    }
}

@Composable
private fun TimersDestination(navController: NavHostController) {
    val viewModel: TimersViewModel = hiltViewModel()
    val state = viewModel.viewState.value
    TimersDestinationScreen(
        onRefresh = { viewModel.updateTimers() },
        state = state,
        effectFlow = viewModel.effect,
        onEventSent = { event -> viewModel.setEvent(event) },
        onNavigationRequested = { navigationEffect ->
            if (navigationEffect is TimersContract.Effect.Navigation.ToTimerDetails) {
                navController.navigate("$WILL_TIMER_LIST/${navigationEffect.timerId}")
            }
            if (navigationEffect is TimersContract.Effect.Navigation.ToTimerAdd) {
                navController.navigate(WILL_TIMER_ADD)
            }
        }
    )
}

@Composable
private fun TimerAddDestination(navController: NavHostController) {
    val viewModel: TimerAddViewModel = hiltViewModel()
    val state = viewModel.viewState.value
    TimerAddDestinationScreen(
        state = state,
        effectFlow = viewModel.effect,
        onEventSent = { event -> viewModel.setEvent(event) },
        onNavigationRequested = {
            navController.popBackStack()
        }
    )
}

@Composable
private fun TimerDetailsDestination(navController: NavHostController) {
    val viewModel: TimerDetailsViewModel = hiltViewModel()
    val state = viewModel.viewState.value
    TimerDetailsDestinationScreen(
        state = state,
        effectFlow = viewModel.effect,
        onEventSent = { event -> viewModel.setEvent(event) },
        onNavigationRequested = { navigationEffect ->
            if (navigationEffect is TimerDetailsContract.Effect.Navigation.ToTimerEdit) {
                navController.navigate("$WILL_TIMER_ADD/${navigationEffect.timerId}")
            }
            if (navigationEffect is TimerDetailsContract.Effect.Navigation.ToTimers) {
                navController.popBackStack()
            }
        }
    )
}

@Composable
private fun TimerEditDestination(navController: NavHostController) {
    val viewModel: TimerEditViewModel = hiltViewModel()
    val state = viewModel.viewState.value
    TimerEditDestinationScreen(
        state = state,
        effectFlow = viewModel.effect,
        onEventSent = { event ->
            viewModel.setEvent(event)
        },
        onNavigationRequested = { navigationEffect ->
            when (navigationEffect) {
                is TimerEditContract.Effect.Navigation.ToTimers -> navController.popBackStack()
            }
        }
    )
}

object NavigationKeys {
    object Arg {
        const val WILL_TIMER_ID = "willTimerName"
    }

    object Route {
        const val WILL_TIMER_LIST = "will_timer_list"
        const val WILL_TIMER_DETAILS = "$WILL_TIMER_LIST/{$WILL_TIMER_ID}"
        const val WILL_TIMER_ADD = "will_timer_add"
        const val WILL_TIMER_EDIT = "$WILL_TIMER_ADD/{$WILL_TIMER_ID}"
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    StrongWillTheme {
        WillApp()
    }
}