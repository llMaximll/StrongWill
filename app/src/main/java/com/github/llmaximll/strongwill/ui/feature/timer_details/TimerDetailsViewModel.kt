package com.github.llmaximll.strongwill.ui.feature.timer_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.github.llmaximll.strongwill.base.BaseViewModel
import com.github.llmaximll.strongwill.model.Timer
import com.github.llmaximll.strongwill.model.data.WillTimersRepository
import com.github.llmaximll.strongwill.ui.feature.NavigationKeys
import com.github.llmaximll.strongwill.utils.getProgress2
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimerDetailsViewModel @Inject constructor(
	private val stateHandle: SavedStateHandle,
	private val repository: WillTimersRepository
) : BaseViewModel<TimerDetailsContract.Event, TimerDetailsContract.State, TimerDetailsContract.Effect>() {

	init {
		viewModelScope.launch {
			val timerId: Long = stateHandle.get<Long>(NavigationKeys.Arg.WILL_TIMER_ID)
				?: throw IllegalStateException("Не найден timerId.")
			val timer: Timer = repository.getTimer(timerId)
			setState { copy(timer = timer, difference = getProgress2(date = timer.date)) }
		}
	}

	override fun setInitialState(): TimerDetailsContract.State =
		TimerDetailsContract.State(
			timer = Timer()
		)

	override fun handleEvents(event: TimerDetailsContract.Event) {
		when (event) {
			is TimerDetailsContract.Event.BackPressed -> {
				setEffect { TimerDetailsContract.Effect.Navigation.ToTimers }
			}
		}
	}


}