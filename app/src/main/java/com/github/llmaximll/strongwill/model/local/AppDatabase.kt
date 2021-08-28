package com.github.llmaximll.strongwill.model.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.llmaximll.strongwill.model.Timer

@Database(entities = [ Timer::class ], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
	abstract fun willDao(): WillDao
}