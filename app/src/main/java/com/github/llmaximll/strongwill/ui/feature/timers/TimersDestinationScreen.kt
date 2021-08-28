package com.github.llmaximll.strongwill.ui.feature.timers

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.llmaximll.strongwill.R
import com.github.llmaximll.strongwill.base.LAUNCH_LISTEN_FOR_EFFECTS
import com.github.llmaximll.strongwill.model.Timer
import com.github.llmaximll.strongwill.utils.categoryToIconRes
import com.github.llmaximll.strongwill.utils.getCategoryColors
import com.github.llmaximll.strongwill.utils.getCategoryIcons
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@Composable
fun TimersDestinationScreen(
	onRefresh: () -> Unit = {  },
	state: TimersContract.State,
	effectFlow: Flow<TimersContract.Effect>?,
	onEventSent: (event: TimersContract.Event) -> Unit,
	onNavigationRequested: (navigationEffect: TimersContract.Effect.Navigation) -> Unit
) {
	val scaffoldState = rememberScaffoldState()

	LaunchedEffect(LAUNCH_LISTEN_FOR_EFFECTS) {
		// Обновление списка таймеров при появлении экрана
		onRefresh()

		effectFlow?.onEach { effect ->
			when (effect) {
				is TimersContract.Effect.DataWasLoaded -> {
//					TODO("DataWasLoaded")
				}
				is TimersContract.Effect.Navigation.ToTimerDetails -> onNavigationRequested(
					effect
				)
				is TimersContract.Effect.Navigation.ToTimerAdd -> onNavigationRequested(
					effect
				)
			}
		}?.collect()
	}

	Scaffold (
		scaffoldState = scaffoldState,
		topBar = { TimersAppBar(
			onEventSent = onEventSent
		) }
	) {
		Box {
			if (state.isLoading) {
				LoadingBar()
			} else {
				if (state.timers.isNotEmpty()) {
					TimersList(timersList = state.timers) { itemId ->
						onEventSent(TimersContract.Event.TimerSelection(itemId))
					}
				} else {
					EmptyBox {
						onEventSent(TimersContract.Event.EmptyBoxSelection)
					}
				}
			}
		}
	}
}

@Composable
fun TimersList(
	timersList: List<Timer>,
	onItemClicked: (id: Long) -> Unit = {  }
) {
	LazyColumn(
		contentPadding = PaddingValues(bottom = 16.dp)
	) {
		items(timersList) { item ->
			TimerItemRow(item = item, onItemClicked = onItemClicked)
		}
	}
}

@Composable
fun TimerItemRow(
	item: Timer,
	onItemClicked: (id: Long) -> Unit = {  }
) {
	Card(
		modifier = Modifier
			.fillMaxWidth()
			.padding(start = 12.dp, end = 12.dp, top = 12.dp)
			.clickable { onItemClicked(item.id) },
		backgroundColor = getCategoryColors()[item.color],
		shape = RoundedCornerShape(8.dp),
		elevation = 3.dp,
	) {
		Row(
			modifier = Modifier
				.fillMaxWidth(),
			verticalAlignment = Alignment.CenterVertically
		) {
			Image(
				modifier = Modifier.padding(10.dp),
				painter = painterResource(id = categoryToIconRes(
					category = item.category
				)),
				contentDescription = null,
				alignment = Alignment.CenterStart
			)
			Text(
				text = item.name,
				textAlign = TextAlign.Center,
				style = MaterialTheme.typography.h6,
				maxLines = 1,
				overflow = TextOverflow.Ellipsis,
				modifier = Modifier
					.padding(top = 22.dp, bottom = 22.dp, end = 16.dp)
			)
		}
	}
}

@Composable
fun LoadingBar() {
	Box(
		contentAlignment = Alignment.Center,
		modifier = Modifier.fillMaxSize()
	) {
		CircularProgressIndicator()
	}
}

@Composable
fun EmptyBox(
	onClick: () -> Unit = {  }
) {
	Box(
		contentAlignment = Alignment.Center,
		modifier = Modifier.fillMaxSize()
	) {
		Column(
			horizontalAlignment = Alignment.CenterHorizontally,
			modifier = Modifier
				.clip(RoundedCornerShape(8.dp))
				.clickable { onClick() }
				.padding(16.dp)
		) {
			Image(
				painter = painterResource(id = R.drawable.search),
				contentDescription = null
			)
			Text(
				text = "No timers found",
				textAlign = TextAlign.Center,
				style = MaterialTheme.typography.h6
			)
			Spacer(modifier = Modifier.height(16.dp))
			Text(
				text = "Click to add a timer",
				textAlign = TextAlign.Center,
				style = MaterialTheme.typography.h5
			)
		}
	}
}

@Composable
fun TimersAppBar(
	onEventSent: (event: TimersContract.Event) -> Unit
) {
	TopAppBar(
		navigationIcon = {
			Icon(
				imageVector = Icons.Default.Home,
				modifier = Modifier.padding(horizontal = 12.dp),
				contentDescription = null
			)
		},
		title = { Text("Timers") },
		backgroundColor = MaterialTheme.colors.background,
		actions = {
			IconButton(
				onClick = {
					onEventSent(TimersContract.Event.EmptyBoxSelection)
				}
			) {
				Icon(
					imageVector = Icons.Default.Add,
					modifier = Modifier.padding(horizontal = 12.dp),
					contentDescription = null
				)
			}
		}
	)
}

@Preview
@Composable
fun TimersDestinationScreenPreview() {
	TimersDestinationScreen(
		onRefresh = {  },
		TimersContract.State(),
		null,
		{ },
		{ }
	)
}