package com.github.llmaximll.strongwill.ui.common

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.llmaximll.strongwill.R
import com.github.llmaximll.strongwill.utils.*
import kotlin.random.Random

@Composable
fun CategoryPicker(
	color: Color,
	onCategorySelected: (category: String) -> Unit
) {
	var expanded by rememberSaveable { mutableStateOf(false) }
	var selectedCategory by rememberSaveable { mutableStateOf(Categories.Fun) }

	Column(
		modifier = Modifier
			.fillMaxWidth()
			.shadow(
				elevation = 5.dp,
				shape = RoundedCornerShape(8.dp)
			)
			.clip(RoundedCornerShape(8.dp))
			.background(Color.White)
			.animateContentSize(
				spring(stiffness = Spring.StiffnessLow)
			)
			.padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
	) {
		CategoryFirstPickerRow(
			category = selectedCategory,
			expanded = expanded,
			color = color
		) {
			expanded = it
		}
		if (expanded) {
			repeat(10) { count ->
				val randomCount by rememberSaveable {
					mutableStateOf(Random.nextInt(0, 7))
				}
				CategoryPickerRow(
					category = getCategoriesList()[count],
					colorNumber = randomCount,
					iconRes = getCategoryIcons().getOrNull(count)
				) { category ->
					expanded = false
					selectedCategory = category
					onCategorySelected(selectedCategory)
				}
			}
		}
	}
}

@Composable
private fun CategoryFirstPickerRow(
	category: String = Categories.Fun,
	color: Color,
	expanded: Boolean,
	onClicked: (expanded: Boolean) -> Unit = {  },
) {
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.padding(top = 8.dp)
			.noRippleClickable { onClicked(!expanded) },
		horizontalArrangement = Arrangement.Start,
		verticalAlignment = Alignment.CenterVertically
	) {
		Row(
			modifier = Modifier.weight(weight = 0.9f),
			horizontalArrangement = Arrangement.Start,
			verticalAlignment = Alignment.CenterVertically
		) {
			Box(
				modifier = Modifier
					.clip(CircleShape)
					.background(color)
					.padding(8.dp)
			) {
				Image(
					painter = painterResource(id = when (category) {
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
					}),
					contentDescription = null
				)
			}
			Text(
				modifier = Modifier.padding(start = 8.dp),
				text = category
			)
		}
		val degreesIcon by animateFloatAsState(
			targetValue = if (expanded) 180f else 0f
		)
		Icon(
			modifier = Modifier
				.weight(weight = 0.1f)
				.rotate(degreesIcon),
			painter = rememberVectorPainter(image = Icons.Default.ArrowDropDown),
			contentDescription = null,
		)
	}
}

@Composable
private fun CategoryPickerRow(
	category: String,
	colorNumber: Int,
	@DrawableRes iconRes: Int?,
	onClicked: (category: String) -> Unit = {  },
) {
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.padding(top = 8.dp)
			.noRippleClickable { onClicked(category) },
		horizontalArrangement = Arrangement.Start,
		verticalAlignment = Alignment.CenterVertically
	) {
		Box(
			modifier = Modifier
				.clip(CircleShape)
				.background(getCategoryColors()[colorNumber])
				.padding(8.dp)
		) {
			Image(
				painter = painterResource(id = iconRes ?: R.drawable.ic_game_controller),
				contentDescription = null
			)
		}
		Text(
			modifier = Modifier.padding(start = 8.dp),
			text = category
		)
	}
}

@Preview
@Composable
private fun CategoryPickerPreview2() {
	CategoryPicker(color = Color(0xFFFFF176)) {  }
}