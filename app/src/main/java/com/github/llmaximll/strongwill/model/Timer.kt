package com.github.llmaximll.strongwill.model

import androidx.annotation.ColorRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.llmaximll.strongwill.utils.Categories
import java.util.*
import kotlin.random.Random

@Entity(tableName = "timers")
data class Timer(
	@PrimaryKey(autoGenerate = true)
	val id: Long = Random.nextLong(),
	val name: String = "",
	val description: String = "",
	val category: String = Categories.Fun,
	@ColorRes val color:  Int = 0,
	val date: Long = Date().time,
	val timeMode: Int = 8
)
