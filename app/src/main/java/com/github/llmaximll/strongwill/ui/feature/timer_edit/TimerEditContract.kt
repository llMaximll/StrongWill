package com.github.llmaximll.strongwill.ui.feature.timer_edit

import com.github.llmaximll.strongwill.base.ViewEvent
import com.github.llmaximll.strongwill.base.ViewSideEffect
import com.github.llmaximll.strongwill.base.ViewState
import com.github.llmaximll.strongwill.model.Timer

class TimerEditContract {
    sealed class Event : ViewEvent {
        object BackPressed : Event()
        data class SaveTimer(val timer: Timer) : Event()
    }

    data class State(val timer: Timer) : ViewState

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            object ToTimers : Navigation()
        }
    }
}