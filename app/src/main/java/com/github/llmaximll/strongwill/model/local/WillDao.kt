package com.github.llmaximll.strongwill.model.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.llmaximll.strongwill.model.Timer

@Dao
interface WillDao {

	@Query("SELECT * FROM timers")
	suspend fun getAllTimers(): List<Timer>

	@Query("SELECT * FROM timers WHERE id = (:timerId)")
	suspend fun getTimer(timerId: Long): Timer

	@Query("SELECT name FROM timers")
	suspend fun getNameTimers(): List<String>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertTimer(timer: Timer)
}