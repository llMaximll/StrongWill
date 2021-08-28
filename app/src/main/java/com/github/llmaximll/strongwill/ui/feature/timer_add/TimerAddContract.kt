package com.github.llmaximll.strongwill.ui.feature.timer_add

import com.github.llmaximll.strongwill.base.ViewEvent
import com.github.llmaximll.strongwill.base.ViewSideEffect
import com.github.llmaximll.strongwill.base.ViewState
import com.github.llmaximll.strongwill.model.Timer

class TimerAddContract {
	sealed class Event : ViewEvent {
		data class SaveTimer(val timer: Timer): Event()
		object BackPressed : Event()
	}

	class State : ViewState

	sealed class Effect : ViewSideEffect {
		sealed class Navigation : Effect() {
			object ToTimers : Navigation()
		}
	}
}