package com.github.llmaximll.strongwill.ui.feature.timer_add

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.llmaximll.strongwill.base.LAUNCH_LISTEN_FOR_EFFECTS
import com.github.llmaximll.strongwill.model.Timer
import com.github.llmaximll.strongwill.ui.common.*
import com.github.llmaximll.strongwill.ui.feature.timers.TimersContract
import com.github.llmaximll.strongwill.ui.theme.*
import com.github.llmaximll.strongwill.utils.Categories
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@Composable
@OptIn(ExperimentalComposeUiApi::class)
fun TimerAddDestinationScreen(
	context: Context? = null,
	state: TimerAddContract.State,
	effectFlow: Flow<TimerAddContract.Effect>?,
	onEventSent: (event: TimerAddContract.Event) -> Unit,
	onNavigationRequested: (navigationEffect: TimerAddContract.Effect.Navigation) -> Unit
) {
	LaunchedEffect(LAUNCH_LISTEN_FOR_EFFECTS) {
		effectFlow?.onEach { effect ->
			when (effect) {
				is TimerAddContract.Effect.Navigation.ToTimers -> onNavigationRequested(
					effect
				)
			}
		}?.collect()
	}
	
	val focusManager = LocalFocusManager.current

	val scrollState = rememberScrollState()

	var nameTimer by rememberSaveable { mutableStateOf("") }
	var descriptionTimer by rememberSaveable { mutableStateOf("") }
	var categoryTimer by rememberSaveable { mutableStateOf(Categories.Fun) }
	var colorTimer by rememberSaveable { mutableStateOf(0) }

	var isErrorName by rememberSaveable { mutableStateOf(false) }
	var error by rememberSaveable { mutableStateOf("Name") }

	val scaffoldState: ScaffoldState = rememberScaffoldState()
	Scaffold(
		modifier = Modifier.fillMaxSize(),
		scaffoldState = scaffoldState,
		topBar = {
			CreatingAppBar(
				onEventSent = onEventSent
			)
		}
	) {
		Column(
			modifier = Modifier
				.fillMaxSize()
				.padding(start = 16.dp, end = 16.dp)
				.verticalScroll(state = scrollState)
		) {
			CurrentTextField(
				error,
				focusManager,
				isError = isErrorName
			) { text ->
				nameTimer = text
			}
			Spacer(modifier = Modifier.height(8.dp))
			DescriptionTextField(
				"Description",
				focusManager
			) { text ->
				descriptionTimer = text
			}
			Spacer(modifier = Modifier.height(16.dp))
			ColorPicker { selected ->
				colorTimer = selected
			}
			Spacer(modifier = Modifier.height(16.dp))
			CategoryPicker(
				when (colorTimer) {
					0 -> YellowCategory
					1 -> RedCategory
					2 -> BlueCategory
					3 -> GreenCategory
					4 -> PinkCategory
					5 -> PurpleCategory
					6 -> LilacCategory
					else -> OrangeCategory
				}
			) { category ->
				categoryTimer = category
			}
			CurrentButton(
				text = "Save"
			) {
				if (nameTimer !in state.nameTimers) {
					onEventSent(TimerAddContract.Event.SaveTimer(
						Timer(
							name = nameTimer,
							description = descriptionTimer,
							category = categoryTimer,
							color = colorTimer
						)
					))
				} else {
					isErrorName = true
					error = "A timer with this name already exists"
				}
			}
		}
	}
}

@Composable
fun CreatingAppBar(
	onEventSent: (event: TimerAddContract.Event) -> Unit
) {
	TopAppBar(
		navigationIcon = {
			IconButton(
				onClick = {
					onEventSent(TimerAddContract.Event.BackPressed)
				}
			) {
				Icon(
					imageVector = Icons.Default.ArrowBack,
					modifier = Modifier.padding(horizontal = 12.dp),
					contentDescription = "Action icon"
				)
			}
		},
		title = { Text("Creating a timer") },
		backgroundColor = MaterialTheme.colors.background
	)
}

@Preview
@Composable
fun TimerAddDestinationScreenPreview() {
	TimerAddDestinationScreen(
		context = null,
		TimerAddContract.State(),
		effectFlow = null,
		onEventSent = {  }
	) { }
}