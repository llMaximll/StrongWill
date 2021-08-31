package com.github.llmaximll.strongwill.ui.feature.timer_details

import com.github.llmaximll.strongwill.base.ViewEvent
import com.github.llmaximll.strongwill.base.ViewSideEffect
import com.github.llmaximll.strongwill.base.ViewState
import com.github.llmaximll.strongwill.model.Timer

class TimerDetailsContract {
	sealed class Event : ViewEvent {
		object BackPressed : Event()
	}

	data class State(
		val timer: Timer?,
		val progressList: List<Float> = List(9) { 0f }
	) : ViewState

	sealed class Effect : ViewSideEffect {
		sealed class Navigation : Effect() {
			object ToTimers : Navigation()
		}
	}
}