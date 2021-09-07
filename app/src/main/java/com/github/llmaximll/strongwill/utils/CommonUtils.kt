package com.github.llmaximll.strongwill.utils

import androidx.compose.ui.graphics.Color
import com.github.llmaximll.strongwill.R
import com.github.llmaximll.strongwill.ui.theme.*

// Categories

fun getCategoryColors(): List<Color> =
	listOf(YellowCategory, RedCategory, BlueCategory, GreenCategory, PinkCategory, PurpleCategory, LilacCategory, OrangeCategory)

fun getCategoryIcons(): List<Int> =
	listOf(
		R.drawable.ic_game_controller,
		R.drawable.ic_science,
		R.drawable.ic_creativity,
		R.drawable.ic_work,
		R.drawable.ic_sport,
		R.drawable.ic_love,
		R.drawable.ic_travelling,
		R.drawable.ic_hobby,
		R.drawable.ic_food,
		R.drawable.ic_business
	)

fun getCategoriesList(): List<String> =
	listOf(
		Categories.Fun,
		Categories.Science,
		Categories.Creativity,
		Categories.Work,
		Categories.Sport,
		Categories.Love,
		Categories.Traveling,
		Categories.Hobby,
		Categories.Food,
		Categories.Business
	)

fun categoryToIconRes(category: String): Int =
	when (category) {
		Categories.Fun -> R.drawable.ic_game_controller
		Categories.Science -> R.drawable.ic_science
		Categories.Creativity -> R.drawable.ic_creativity
		Categories.Work -> R.drawable.ic_work
		Categories.Sport -> R.drawable.ic_sport
		Categories.Love -> R.drawable.ic_love
		Categories.Traveling -> R.drawable.ic_travelling
		Categories.Hobby -> R.drawable.ic_hobby
		Categories.Food -> R.drawable.ic_food
		else -> R.drawable.ic_business
	}

// Ranks

fun getRankIcons(): List<Int> =
	listOf(
		R.drawable.ic_rank_1,
		R.drawable.ic_rank_2,
		R.drawable.ic_rank_3,
		R.drawable.ic_rank_4,
		R.drawable.ic_rank_5,
		R.drawable.ic_rank_6,
		R.drawable.ic_rank_7,
		R.drawable.ic_rank_8,
		R.drawable.ic_rank_9,
		R.drawable.ic_rank_10,
		R.drawable.ic_rank_11,
		R.drawable.ic_rank_12,
		R.drawable.ic_rank_13,
		R.drawable.ic_rank_14,
	)

fun getRanksList(): List<String> =
	listOf(
		Ranks.R1_Cadet,
		Ranks.R2_SpacemanApprentice,
		Ranks.R3_Spaceman,
		Ranks.R4_PettyOfficer,
		Ranks.R5_ChiefPettyOfficer,
		Ranks.R6_WarrantOfficerClassII,
		Ranks.R7_WarrantOfficerClassI,
		Ranks.R8_Ensign,
		Ranks.R9_Lieutenant,
		Ranks.R10_Commander,
		Ranks.R11_Captain,
		Ranks.R12_RearAdmiral,
		Ranks.R13_ViceAdmiral,
		Ranks.R14_Admiral,
	)

fun getTimeRanks(): List<String> =
	listOf(
		"1 hour", "1 day", "3 days", "1 week", "2 week", "1 month", "3 months", "6 months", "1 year"
	)