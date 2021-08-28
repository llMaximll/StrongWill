package com.github.llmaximll.strongwill.ui.feature.timer_details

import com.github.llmaximll.strongwill.base.ViewEvent
import com.github.llmaximll.strongwill.base.ViewSideEffect
import com.github.llmaximll.strongwill.base.ViewState
import com.github.llmaximll.strongwill.model.Timer
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.DateTimePeriod

class TimerDetailsContract {
	sealed class Event : ViewEvent {
		object BackPressed : Event()
	}

	data class State(
		val timer: Timer?,
		val difference: DateTimePeriod? = null
	) : ViewState

	sealed class Effect : ViewSideEffect {
		sealed class Navigation : Effect() {
			object ToTimers : Navigation()
		}
	}
}