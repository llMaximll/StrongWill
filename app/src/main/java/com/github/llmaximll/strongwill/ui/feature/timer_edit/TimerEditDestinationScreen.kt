package com.github.llmaximll.strongwill.ui.feature.timer_edit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.llmaximll.strongwill.base.LAUNCH_LISTEN_FOR_EFFECTS
import com.github.llmaximll.strongwill.model.Timer
import com.github.llmaximll.strongwill.ui.common.CurrentButton
import com.github.llmaximll.strongwill.ui.common.CurrentTextField
import com.github.llmaximll.strongwill.ui.common.DescriptionTextField
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@Composable
fun TimerEditDestinationScreen(
    state: TimerEditContract.State,
    effectFlow: Flow<TimerEditContract.Effect>?,
    onEventSent: (event: TimerEditContract.Event) -> Unit,
    onNavigationRequested: (navigationEffect: TimerEditContract.Effect.Navigation) -> Unit
) {
    // false - nameTimer is not initialized, true - nameTimer is initialized
    var initValue by rememberSaveable { mutableStateOf(false) }

    var nameTimer by rememberSaveable { mutableStateOf(state.timer?.name ?: "") }
    var descriptionTimer by rememberSaveable { mutableStateOf(state.timer?.description ?: "") }

    var isErrorName by rememberSaveable { mutableStateOf(false) }
    var error by rememberSaveable { mutableStateOf("Name") }

    if (!initValue && state.timer != null) {
        nameTimer = state.timer.name
        descriptionTimer = state.timer.description
        initValue = true
    }

    LaunchedEffect(LAUNCH_LISTEN_FOR_EFFECTS) {
        effectFlow?.onEach { effect ->
            when (effect) {
                is TimerEditContract.Effect.Navigation.ToTimers -> onNavigationRequested(
                    effect
                )
            }
        }?.collect()
    }

    val focusManager = LocalFocusManager.current

    val scrollState = rememberScrollState()
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        topBar = { EditAppBar(onEventSent = { onEventSent(TimerEditContract.Event.BackPressed) }) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp)
                .verticalScroll(state = scrollState)
        ) {
            CurrentTextField(
                label = error,
                text = nameTimer,
                isError = isErrorName,
                focusManager = focusManager
            ) { text ->
                nameTimer = text
            }
            Spacer(modifier = Modifier.height(8.dp))
            DescriptionTextField(
                label = "Description",
                text = descriptionTimer,
                focusManager = focusManager
            ) { text ->
                descriptionTimer = text
            }
            CurrentButton(
                text = "Save",
                onEventSent = {
                    if (nameTimer !in state.nameTimers) {
                        onEventSent(TimerEditContract.Event.SaveTimer(timer = state.timer?.copy(
                            name = nameTimer,
                            description = descriptionTimer
                        ) ?: Timer()))
                    } else {
                        isErrorName = true
                        error = "A timer with this name already exists"
                    }
                }
            )
        }

    }
}

@Composable
private fun EditAppBar(
    onEventSent: (event: TimerEditContract.Event) -> Unit
) {
    TopAppBar(
        navigationIcon = {
            IconButton(
                onClick = {
                    onEventSent(TimerEditContract.Event.BackPressed)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    modifier = Modifier.padding(horizontal = 12.dp),
                    contentDescription = "Action icon"
                )
            }
        },
        title = { Text("Edit") },
        backgroundColor = MaterialTheme.colors.background
    )
}

@Preview
@Composable
private fun TimerEditDestinationScreenPreview() {
    TimerEditDestinationScreen(
        state = TimerEditContract.State(timer = Timer()),
        effectFlow = null,
        onEventSent = {  },
        onNavigationRequested = {  }
    )
}