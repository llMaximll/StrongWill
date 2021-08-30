package com.github.llmaximll.strongwill.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import timber.log.Timber
import java.util.*
import kotlin.time.Duration

@OptIn(kotlin.time.ExperimentalTime::class)
fun getProgress(post: Long): List<Float> {

	val nowInstant = Clock.System.now()

	val postInstant = Instant.fromEpochMilliseconds(post)

	val differenceInstant0 = nowInstant - postInstant
	val differenceInstant1 = nowInstant - (postInstant + Duration.hours(1))
	val differenceInstant2 = nowInstant - (postInstant + Duration.days(1) + Duration.hours(1))
	val differenceInstant3 = nowInstant - (postInstant + Duration.days(4) + Duration.hours(1))
	val differenceInstant4 = nowInstant - (postInstant + Duration.days(11) + Duration.hours(1))
	val differenceInstant5 = nowInstant - (postInstant + Duration.days(25) + Duration.hours(1))
	val differenceInstant6 = nowInstant - (postInstant + Duration.days(55) + Duration.hours(1))
	val differenceInstant7 = nowInstant - (postInstant + Duration.days(145) + Duration.hours(1))
	val differenceInstant8 = nowInstant - (postInstant + Duration.days(325) + Duration.hours(1))

	Timber.v("differenceInstant0=$differenceInstant0")

	//-----------------

	val progressList = MutableList(9) { 0f }

	progressList[0] = proceedProgress((differenceInstant0 / Duration.Companion.hours(1)).toFloat())
	progressList[1] = proceedProgress((differenceInstant1 / Duration.Companion.days(1)).toFloat())
	progressList[2] = proceedProgress((differenceInstant2 / Duration.Companion.days(3)).toFloat())
	progressList[3] = proceedProgress((differenceInstant3 / Duration.Companion.days(7)).toFloat())
	progressList[4] = proceedProgress((differenceInstant4 / Duration.Companion.days(14)).toFloat())
	progressList[5] = proceedProgress((differenceInstant5 / Duration.Companion.days(30)).toFloat())
	progressList[6] = proceedProgress((differenceInstant6 / Duration.Companion.days(90)).toFloat())
	progressList[7] = proceedProgress((differenceInstant7 / Duration.Companion.days(180)).toFloat())
	progressList[8] = proceedProgress((differenceInstant8 / Duration.Companion.days(365)).toFloat())

	Timber.v("progressList=$progressList")

	return progressList
}

private fun proceedProgress(value: Float): Float = when {
	value < 0f -> 0f
	value > 1f -> 1f
	else -> value
}

private object TimeMode {
	const val HOUR = 0
	const val DAY = 1
	const val THREE_DAYS = 2
	const val WEEK = 3
	const val TWO_WEEK = 4
	const val MONTH = 5
	const val THREE_MONTH = 6
	const val SIX_MONTH = 7
	const val YEAR = 8
}