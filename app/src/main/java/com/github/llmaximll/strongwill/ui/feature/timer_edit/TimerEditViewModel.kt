package com.github.llmaximll.strongwill.ui.feature.timer_edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.github.llmaximll.strongwill.base.BaseViewModel
import com.github.llmaximll.strongwill.model.Timer
import com.github.llmaximll.strongwill.model.data.WillTimersRepository
import com.github.llmaximll.strongwill.ui.feature.NavigationKeys
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimerEditViewModel @Inject constructor(
    private val stateHandle: SavedStateHandle,
    private val repository: WillTimersRepository
) : BaseViewModel<TimerEditContract.Event, TimerEditContract.State, TimerEditContract.Effect>() {

    init {
        viewModelScope.launch {
            val timerId: Long = stateHandle.get<Long>(NavigationKeys.Arg.WILL_TIMER_ID)
                ?: throw IllegalStateException("Не найден timerId.")
            val timer: Timer = repository.getTimer(timerId)
            setState { copy(timer = timer) }

            updateNameTimers()
        }
    }

    override fun setInitialState(): TimerEditContract.State =
        TimerEditContract.State(timer = null)

    override fun handleEvents(event: TimerEditContract.Event) {
        when (event) {
            is TimerEditContract.Event.BackPressed -> {
                setEffect { TimerEditContract.Effect.Navigation.ToTimers }
            }
            is TimerEditContract.Event.SaveTimer -> {
                saveTimer(event.timer)
                setEffect { TimerEditContract.Effect.Navigation.ToTimers }
            }
        }
    }

    private fun saveTimer(timer: Timer) {
        viewModelScope.launch { repository.updateTimer(timer = timer) }
    }

    private fun updateNameTimers() {
        viewModelScope.launch { getNameTimers() }
    }

    private suspend fun getNameTimers() {
        val nameTimers = repository.getNameTimers()
        setState {
            copy(nameTimers = nameTimers)
        }
    }
}