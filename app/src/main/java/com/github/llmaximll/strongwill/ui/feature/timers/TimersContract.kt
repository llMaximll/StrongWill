package com.github.llmaximll.strongwill.ui.feature.timers

import com.github.llmaximll.strongwill.base.ViewEvent
import com.github.llmaximll.strongwill.base.ViewSideEffect
import com.github.llmaximll.strongwill.base.ViewState
import com.github.llmaximll.strongwill.model.Timer

class TimersContract {
	sealed class Event : ViewEvent {
		data class TimerSelection(val timerId: Long) : Event()
		object EmptyBoxSelection : Event()
	}

	data class State(val timers: List<Timer> = listOf(), val isLoading: Boolean = false): ViewState

	sealed class Effect : ViewSideEffect {
		object DataWasLoaded : Effect()

		sealed class Navigation : Effect() {
			data class ToTimerDetails(val timerId: Long): Navigation()
			object ToTimerAdd : Navigation()
		}
	}
}