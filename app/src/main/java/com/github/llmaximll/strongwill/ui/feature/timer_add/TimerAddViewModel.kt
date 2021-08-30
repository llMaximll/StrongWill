package com.github.llmaximll.strongwill.ui.feature.timer_add

import androidx.lifecycle.viewModelScope
import com.github.llmaximll.strongwill.base.BaseViewModel
import com.github.llmaximll.strongwill.model.Timer
import com.github.llmaximll.strongwill.model.data.WillTimersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimerAddViewModel @Inject constructor(
	private val repository: WillTimersRepository
) :
	BaseViewModel<TimerAddContract.Event, TimerAddContract.State, TimerAddContract.Effect>() {

	init {
		updateNameTimers()
	}

	override fun setInitialState(): TimerAddContract.State =
		TimerAddContract.State()

	override fun handleEvents(event: TimerAddContract.Event) {
		when (event) {
			is TimerAddContract.Event.SaveTimer -> {
				saveTimer(event.timer)
				setEffect { TimerAddContract.Effect.Navigation.ToTimers }
			}
			is TimerAddContract.Event.BackPressed -> {
				setEffect { TimerAddContract.Effect.Navigation.ToTimers }
			}
		}
	}

	private fun saveTimer(timer: Timer) {
		viewModelScope.launch { repository.insertTimer(timer) }
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