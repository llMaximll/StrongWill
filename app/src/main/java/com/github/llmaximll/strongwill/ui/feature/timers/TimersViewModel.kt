package com.github.llmaximll.strongwill.ui.feature.timers

import androidx.lifecycle.viewModelScope
import com.github.llmaximll.strongwill.base.BaseViewModel
import com.github.llmaximll.strongwill.model.data.WillTimersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TimersViewModel @Inject constructor(private val repository: WillTimersRepository) :
	BaseViewModel<TimersContract.Event, TimersContract.State, TimersContract.Effect>() {

	init {
		Timber.v("viewModel")
		viewModelScope.launch { getTimers() }
	}


	override fun setInitialState(): TimersContract.State =
		TimersContract.State(timers = listOf(), isLoading = true)

	override fun handleEvents(event: TimersContract.Event) {
		when (event) {
			is TimersContract.Event.TimerSelection -> {
				setEffect { TimersContract.Effect.Navigation.ToTimerDetails(event.timerId) }
			}
			is TimersContract.Event.EmptyBoxSelection -> {
				setEffect { TimersContract.Effect.Navigation.ToTimerAdd }
			}
		}
	}

	private suspend fun getTimers() {
		val timers = repository.getAllTimers()
		setState {
			copy(timers = timers, isLoading = false)
		}
		setEffect { TimersContract.Effect.DataWasLoaded }
	}

	fun updateTimers() {
		viewModelScope.launch { getTimers() }
	}
}