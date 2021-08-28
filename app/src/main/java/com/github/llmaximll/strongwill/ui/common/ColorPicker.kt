package com.github.llmaximll.strongwill.ui.common

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.llmaximll.strongwill.ui.theme.*

@Composable
fun ColorPicker(
	onSelected: (Int) -> Unit = {  }
) {
	var selected by rememberSaveable { mutableStateOf(0) }

	Column(
		modifier = Modifier
			.fillMaxWidth()
			.wrapContentHeight()
			.shadow(
				elevation = 5.dp,
				shape = RoundedCornerShape(8.dp)
			)
			.clip(RoundedCornerShape(8.dp))
			.background(Color(0xFFFFFFFF))
			.padding(8.dp),
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		ColorPickerRowFirst(
			selected
		) { selectedColor ->
			selected = selectedColor
			onSelected(selected)
		}
		Spacer(modifier = Modifier.padding(top = 8.dp))
		ColorPickerRowSecond(
			selected = selected
		) { selectedColor ->
			selected = selectedColor
			onSelected(selected)
		}
	}
}

@Composable
private fun ColorPickerRowFirst(
	selected: Int,
	onSelected: (Int) -> Unit
) {
	Row(
		modifier = Modifier.fillMaxWidth(),
		horizontalArrangement = Arrangement.SpaceEvenly
	) {
		repeat (4) { count ->
			val borderCount by animateDpAsState(
				targetValue = if (selected == count) 4.dp else 1.dp
			)
			Column(
				modifier = Modifier
					.fillMaxHeight()
					.clickable(
						indication = null,
						interactionSource = remember { MutableInteractionSource() }
					) {
						onSelected(count)
					}
					.width(75.dp),
				verticalArrangement = Arrangement.SpaceBetween,
				horizontalAlignment = Alignment.CenterHorizontally
			) {
				Box(
					modifier = Modifier
						.size(50.dp)
						.clip(CircleShape)
						.background(
							color = when (count) {
								0 -> YellowCategory
								1 -> RedCategory
								2 -> BlueCategory
								else -> GreenCategory
							}
						)
						.border(
							border = BorderStroke(
								borderCount,
								Color(0x4D000000)
							),
							shape = CircleShape
						)
				)
				Text(
					modifier = Modifier.padding(top = 8.dp),
					text = when (count) {
						0 -> "Yellow"
						1 -> "Red"
						2 -> "Blue"
						else -> "Green"
					}
				)
			}
		}
	}
}

@Composable
private fun ColorPickerRowSecond(
	selected: Int,
	onSelected: (Int) -> Unit
) {
	Row(
		modifier = Modifier.fillMaxWidth(),
		horizontalArrangement = Arrangement.SpaceEvenly
	) {
		repeat (4) { count ->
			val borderCount by animateDpAsState(
				targetValue = if (selected == count + 4) 4.dp else 1.dp
			)
			Column(
				modifier = Modifier
					.fillMaxHeight()
					.clickable(
						indication = null,
						interactionSource = remember { MutableInteractionSource() }
					) {
						onSelected(count + 4)
					}
					.width(75.dp),
				verticalArrangement = Arrangement.SpaceBetween,
				horizontalAlignment = Alignment.CenterHorizontally
			) {
				Box(
					modifier = Modifier
						.size(50.dp)
						.clip(CircleShape)
						.background(
							color = when (count) {
								0 -> PinkCategory
								1 -> PurpleCategory
								2 -> LilacCategory
								else -> OrangeCategory
							}
						)
						.border(
							border = BorderStroke(
								borderCount,
								Color(0x4D000000)
							),
							shape = CircleShape
						)
				)
				Text(
					modifier = Modifier.padding(top = 8.dp),
					text = when (count) {
						0 -> "Pink"
						1 -> "Purple"
						2 -> "Lilac"
						else -> "Orange"
					}
				)
			}
		}
	}
}

@Preview
@Composable
private fun ColorPickerPreview() {
	ColorPicker()
}