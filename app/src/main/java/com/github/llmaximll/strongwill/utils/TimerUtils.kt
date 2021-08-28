package com.github.llmaximll.strongwill.utils

import kotlinx.datetime.*
import kotlinx.datetime.TimeZone
import timber.log.Timber
import java.util.*
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.toJavaDuration

fun getProgress(date: Long, timeMode: Int): Float =
	when (timeMode) {
		TimeMode.HOUR -> getProgressHour(date)
		TimeMode.DAY -> getProgressDay(date)
		TimeMode.THREE_DAYS -> getProgressThreeDays(date)
		TimeMode.WEEK -> getProgressWeek(date)
		TimeMode.TWO_WEEK -> getProgressTwoWeek(date)
		TimeMode.MONTH -> getProgressMonth(date)
		TimeMode.THREE_MONTH -> getProgressThreeMonth(date)
		TimeMode.SIX_MONTH -> getProgressSixMonth(date)
		else -> getProgressYear(date)
	}

private fun timeBetween(startDate: Date, endDate: Date, timeMode: Int): Long {
	val sDate: Calendar = getDatePart(startDate, timeMode)
	val eDate: Calendar = getDatePart(endDate, timeMode)
	Timber.v("startDate=$sDate | endDate=$eDate")
	var timeBetween: Long = 0
	while (sDate.before(eDate)) {
		when (timeMode) {
			TimeMode.HOUR -> sDate.add(Calendar.MINUTE, 1)
			TimeMode.DAY -> sDate.add(Calendar.HOUR, 1)
			TimeMode.THREE_DAYS -> sDate.add(Calendar.HOUR, 1)
			TimeMode.WEEK -> sDate.add(Calendar.HOUR, 1)
			TimeMode.TWO_WEEK -> sDate.add(Calendar.DAY_OF_MONTH, 1)
			TimeMode.MONTH -> sDate.add(Calendar.DAY_OF_MONTH, 1)
			TimeMode.THREE_MONTH -> sDate.add(Calendar.DAY_OF_MONTH, 1)
			TimeMode.SIX_MONTH -> sDate.add(Calendar.MONTH, 1)
			TimeMode.YEAR -> sDate.add(Calendar.MONTH, 1)
		}
		timeBetween++
	}
	return timeBetween
}

private fun getDatePart(date: Date, timeMode: Int): Calendar {
	val cal = Calendar.getInstance()
	cal.time = date

//	if (timeMode != TimeMode.HOUR) cal[Calendar.HOUR_OF_DAY] = 0
//	cal[Calendar.MINUTE] = 0
//	cal[Calendar.SECOND] = 0
//	cal[Calendar.MILLISECOND] = 0

	return cal
}

// -------------------------------------------------------------------------------------------------

private fun getProgressHour(date: Long): Float {
	val newDate = Date()
	val oldDate = Date(date)
	Timber.v("getProgressHour | newDate=$newDate | oldDate=$oldDate")
	val minutesBetween = timeBetween(
		startDate = oldDate,
		endDate = newDate,
		timeMode = TimeMode.HOUR
	)

	val progress = minutesBetween.toFloat() / 60f

	Timber.v("hour | $progress")

	return progress
}

private fun getProgressDay(date: Long): Float {
	val newDate = Date()
	val oldDate = Date(date)
	val hoursBetween = timeBetween(
		startDate = oldDate,
		endDate = newDate,
		timeMode = TimeMode.DAY
	)

	val progress = hoursBetween.toFloat() / 24f

	Timber.v("day | $progress")

	return progress
}

private fun getProgressThreeDays(date: Long): Float {
	val newDate = Date()
	val oldDate = Date(date)
	val hoursBetween = timeBetween(
		startDate = oldDate,
		endDate = newDate,
		timeMode = TimeMode.THREE_DAYS
	)

	val progress = hoursBetween.toFloat() / 72f

	Timber.v("three days | $progress")

	return progress
}

private fun getProgressWeek(date: Long): Float {
	val newDate = Date()
	val oldDate = Date(date)
	val hoursBetween = timeBetween(
		startDate = oldDate,
		endDate = newDate,
		timeMode = TimeMode.WEEK
	)

	val progress = hoursBetween.toFloat() / 168f

	Timber.v("week | $progress")

	return progress
}

private fun getProgressTwoWeek(date: Long): Float {
	val newDate = Date()
	val oldDate = Date(date)
	val hoursBetween = timeBetween(
		startDate = oldDate,
		endDate = newDate,
		timeMode = TimeMode.TWO_WEEK
	)

	val progress = hoursBetween.toFloat() / 14f

	Timber.v("two week | $progress")

	return progress
}

private fun getProgressMonth(date: Long): Float {
	val newDate = Date()
	val oldDate = Date(date)
	val hoursBetween = timeBetween(
		startDate = oldDate,
		endDate = newDate,
		timeMode = TimeMode.MONTH
	)

	val progress = hoursBetween.toFloat() / 30f

	Timber.v("month | $progress")

	return progress
}

private fun getProgressThreeMonth(date: Long): Float {
	val newDate = Date()
	val oldDate = Date(date)
	val hoursBetween = timeBetween(
		startDate = oldDate,
		endDate = newDate,
		timeMode = TimeMode.THREE_MONTH
	)

	val progress = hoursBetween.toFloat() / 60f

	Timber.v("three month | $progress")

	return progress
}

private fun getProgressSixMonth(date: Long): Float {
	val newDate = Date()
	val oldDate = Date(date)
	val hoursBetween = timeBetween(
		startDate = oldDate,
		endDate = newDate,
		timeMode = TimeMode.SIX_MONTH
	)
	val progress = hoursBetween.toFloat() / 6f

	Timber.v("six month | $progress")

	return progress
}

private fun getProgressYear(date: Long): Float {
	val newDate = Date()
	val oldDate = Date(date)
	val hoursBetween = timeBetween(
		startDate = oldDate,
		endDate = newDate,
		timeMode = TimeMode.YEAR
	)

	val progress = hoursBetween.toFloat() / 12f

	Timber.v("year | $progress")

	return progress
}

fun getProgress2(date: Long): DateTimePeriod {
	val now = Clock.System.now()

	val post: Instant = Instant.fromEpochMilliseconds(date)

	val difference = post.periodUntil(now, TimeZone.currentSystemDefault())

	Timber.v("getTimeBetween() | now=$now | post=$post")
	Timber.v("difference | minutes=${difference.minutes}")
	Timber.v("difference | hours=${difference.hours}")
	Timber.v("difference | days=${difference.days}")
	Timber.v("difference | months=${difference.months}")
	Timber.v("difference | years=${difference.years}")

	return difference
}

@OptIn(ExperimentalTime::class)
fun getProgressList(difference: DateTimePeriod): List<Float> {
	val list = listOf(
		getProcessedProgress(difference.minutes / 60f),
		getProcessedProgress(difference.hours / 24f),
		getProcessedProgress(difference.hours / 72f),
		getProcessedProgress(difference.hours / 168f),
		getProcessedProgress(difference.hours / 336f),
		getProcessedProgress(difference.hours / 672f),
		getProcessedProgress(difference.hours / 2016f),
		getProcessedProgress(difference.hours / 4032f),
		getProcessedProgress(difference.hours / 12096f)
	)

	val resultList = mutableListOf<Float>()

	var i = 0
	do {
		resultList.add(list[i].values.first())
		i++
	} while (list[i].containsKey(true))

	return resultList
}

fun getProcessedProgress(value: Float): Map<Boolean, Float> {
	if (value < 0f) return mapOf(false to 0f)
	if (value > 1f) return mapOf(true to 1f)

	return mapOf(false to value)
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