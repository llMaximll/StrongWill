package com.github.llmaximll.strongwill.ui.feature

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.github.llmaximll.strongwill.ui.feature.NavigationKeys.Arg.WILL_TIMER_ID
import com.github.llmaximll.strongwill.ui.feature.timer_add.TimerAddDestinationScreen
import com.github.llmaximll.strongwill.ui.feature.timer_add.TimerAddViewModel
import com.github.llmaximll.strongwill.ui.feature.timer_details.TimerDetailsDestinationScreen
import com.github.llmaximll.strongwill.ui.feature.timer_details.TimerDetailsViewModel
import com.github.llmaximll.strongwill.ui.feature.timers.TimersContract
import com.github.llmaximll.strongwill.ui.feature.timers.TimersDestinationScreen
import com.github.llmaximll.strongwill.ui.feature.timers.TimersViewModel
import com.github.llmaximll.strongwill.ui.theme.StrongWillTheme
import dagger.hilt.android.AndroidEntryPoint

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

@Composable
fun WillApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = NavigationKeys.Route.WILL_TIMER_LIST) {
        composable(route = NavigationKeys.Route.WILL_TIMER_LIST) {
            TimersDestination(navController = navController)
        }
        composable(
            route = NavigationKeys.Route.WILL_TIMER_DETAILS,
            arguments = listOf(navArgument(WILL_TIMER_ID) {
                type = NavType.LongType
            })
        ) {
            TimerDetailsDestination(navController)
        }
        composable(route = NavigationKeys.Route.WILL_TIMER_ADD) {
            TimerAddDestination(navController = navController)
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
                navController.navigate("${NavigationKeys.Route.WILL_TIMER_LIST}/${navigationEffect.timerId}")
            }
            if (navigationEffect is TimersContract.Effect.Navigation.ToTimerAdd) {
                navController.navigate(NavigationKeys.Route.WILL_TIMER_ADD)
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
        onNavigationRequested = { navController.popBackStack() }
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
        onNavigationRequested = { navController.popBackStack() }
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
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    StrongWillTheme {
        WillApp()
    }
}