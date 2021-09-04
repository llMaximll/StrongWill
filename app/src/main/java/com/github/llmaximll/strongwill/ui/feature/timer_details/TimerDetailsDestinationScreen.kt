package com.github.llmaximll.strongwill.ui.feature.timer_details

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.llmaximll.strongwill.base.LAUNCH_LISTEN_FOR_EFFECTS
import com.github.llmaximll.strongwill.ui.common.HistoryBar
import com.github.llmaximll.strongwill.ui.common.TimerCircularProgressBar
import com.github.llmaximll.strongwill.utils.getRanksList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@Composable
fun TimerDetailsDestinationScreen(
	state: TimerDetailsContract.State,
	effectFlow: Flow<TimerDetailsContract.Effect>?,
	onEventSent: (event: TimerDetailsContract.Event) -> Unit,
	onNavigationRequested: (navigationEffect: TimerDetailsContract.Effect.Navigation) -> Unit
) {
	LaunchedEffect(LAUNCH_LISTEN_FOR_EFFECTS) {
		onEventSent(TimerDetailsContract.Event.RefreshData)

		effectFlow?.onEach { effect ->
			when (effect) {
				is TimerDetailsContract.Effect.Navigation.ToTimers -> onNavigationRequested(
					effect
				)
				is TimerDetailsContract.Effect.Navigation.ToTimerEdit -> onNavigationRequested(
					effect
				)
			}
		}?.collect()
	}

	val scaffoldState = rememberScaffoldState()
	val scrollState = rememberScrollState()
	Scaffold(
		modifier = Modifier.fillMaxSize(),
		scaffoldState = scaffoldState,
		topBar = { DetailsAppBar(timerId = state.timer?.id, onEventSent = { event ->
			when (event) {
				is TimerDetailsContract.Event.BackPressed -> {
					onEventSent(TimerDetailsContract.Event.BackPressed)
				}
				is TimerDetailsContract.Event.ToTimerEdit -> {
					onEventSent(TimerDetailsContract.Event.ToTimerEdit(event.timerId))
				}
			}
		}) }
	) {
		Column(
			modifier = Modifier
				.fillMaxSize()
				.padding(horizontal = 8.dp)
				.verticalScroll(state = scrollState),
			horizontalAlignment = Alignment.CenterHorizontally,
		) {
			Spacer(modifier = Modifier.height(8.dp))
			DescriptionBox(
				name = state.timer?.name ?: "Unknown",
				description = state.timer?.description ?: "Unknown"
			)
			Spacer(modifier = Modifier.height(8.dp))
			TimerCircularProgressBar(
				text = getRanksList().getOrElse(state.progressList.indexOfFirst {
					it > 0f && it < 1f
				}) { "Cadet" },
				progress = state.progressList.firstOrNull { it > 0f && it < 1f }
					?: 1f
			)
			Spacer(modifier = Modifier.height(20.dp))
			HistoryBar(
				progressList = state.progressList
			)
		}
	}
}

@Composable
private fun DetailsAppBar(
	onEventSent: (event: TimerDetailsContract.Event) -> Unit,
	timerId: Long?
) {
	TopAppBar(
		navigationIcon = {
			IconButton(
				onClick = {
					onEventSent(TimerDetailsContract.Event.BackPressed)
				}
			) {
				Icon(
					imageVector = Icons.Default.ArrowBack,
					modifier = Modifier.padding(horizontal = 12.dp),
					contentDescription = "Action icon"
				)
			}
		},
		title = { Text("Details") },
		actions = {
			IconButton(
				onClick = { onEventSent(TimerDetailsContract.Event.ToTimerEdit(timerId = timerId ?: 0L)) }
			) {
				Icon(
					imageVector = Icons.Default.Edit,
					modifier = Modifier.padding(horizontal = 12.dp),
					contentDescription = null
				)
			}
		},
		backgroundColor = MaterialTheme.colors.background
	)
}

@Composable
private fun DescriptionBox(
	name: String,
	description: String
) {
	var expanded by rememberSaveable { mutableStateOf(false) }

	Column(
		modifier = Modifier
			.fillMaxWidth()
			.shadow(
				elevation = 5.dp,
				shape = RoundedCornerShape(16.dp)
			)
			.clip(RoundedCornerShape(16.dp))
			.background(MaterialTheme.colors.background)
			.clickable { expanded = !expanded }
			.animateContentSize()
			.padding(16.dp)
	) {
		Row(
			modifier = Modifier.fillMaxWidth()
		) {
			Text(
				modifier = Modifier
					.fillMaxWidth()
					.weight(weight = 0.9f),
				text = name,
				style = MaterialTheme.typography.h6,
				maxLines = 1,
				overflow = TextOverflow.Ellipsis
			)
			val degreesIcon by animateFloatAsState(
				targetValue = if (expanded) 180f else 0f
			)
			Icon(
				modifier = Modifier
					.weight(weight = 0.1f)
					.rotate(degreesIcon),
				painter = rememberVectorPainter(image = Icons.Default.ArrowDropDown),
				contentDescription = null,
			)
		}
		if (expanded) {
			DescriptionBoxExpanded(description)
		}
	}
}

@Composable
private fun DescriptionBoxExpanded(
	description: String
) {
	Text(
		modifier = Modifier
			.fillMaxWidth()
			.padding(top = 8.dp),
		text = description,
		style = MaterialTheme.typography.body1,
	)
}

@Preview
@Composable
private fun TimerDetailsDestinationScreenPreview() {
	TimerDetailsDestinationScreen(
		state = TimerDetailsContract.State(null),
		effectFlow = null,
		onEventSent = {  },
		onNavigationRequested = {  }
	)
}