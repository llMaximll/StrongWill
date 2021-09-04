package com.github.llmaximll.strongwill.model.data

import com.github.llmaximll.strongwill.model.Timer
import com.github.llmaximll.strongwill.model.local.WillDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WillTimersRepository @Inject constructor(
	private val willDao: WillDao
) {

	suspend fun getAllTimers(): List<Timer> = willDao.getAllTimers()

	suspend fun getTimer(timerId: Long): Timer = willDao.getTimer(timerId)

	suspend fun getNameTimers(): List<String> = willDao.getNameTimers()

	suspend fun insertTimer(timer: Timer) = willDao.insertTimer(timer)

	suspend fun updateTimer(timer: Timer) = willDao.updateTimer(timer)
}