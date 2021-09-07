package com.github.llmaximll.strongwill.ui.common

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.github.llmaximll.strongwill.utils.getRankIcons
import com.github.llmaximll.strongwill.utils.getRanksList
import com.github.llmaximll.strongwill.utils.getTimeRanks

@Composable
fun HistoryBar(
	progressList: List<Float>
) {
	var expanded by rememberSaveable { mutableStateOf(false) }

	Column(
		modifier = Modifier
			.fillMaxWidth()
			.padding(bottom = 8.dp)
	) {
		Row(
			verticalAlignment = Alignment.CenterVertically
		) {
			Text(
				modifier = Modifier.padding(start = 16.dp),
				text = "Ranks",
				style = MaterialTheme.typography.h6
			)
			IconButton(
				onClick = {
					expanded = !expanded
				}
			) {
				Icon(
					imageVector = Icons.Default.Info,
					contentDescription = null
				)
			}
		}
		Spacer(modifier = Modifier.padding(vertical = 6.dp))
		Column(
			modifier = Modifier
				.fillMaxWidth()
				.shadow(
					elevation = 5.dp,
					shape = RoundedCornerShape(16.dp)
				)
				.background(
					color = MaterialTheme.colors.background
				)
				.clip(RoundedCornerShape(16.dp))
		) {
			for (i in progressList.indices) {
				if (progressList[i] != 0f) {
					HistoryBarRow(
						count = i,
						progress = progressList[i],
						expanded = expanded
					)
				}
			}
			if (progressList.any { it < 1f || progressList.indexOf(it) != progressList.lastIndex }) {
				DisabledHistoryBarRow()
			}
		}
	}
}

@Composable
private fun HistoryBarRow(
	count: Int,
	progress: Float,
	expanded: Boolean = false
) {
	val animatedAlphaText = remember { Animatable(0f) }
	val animatedProgress = remember { Animatable(0f) }
	LaunchedEffect(Unit) {
		animatedProgress.animateTo(
			targetValue = progress,
			animationSpec = spring(stiffness = Spring.StiffnessVeryLow)
		)
		animatedAlphaText.animateTo(
			targetValue = progress,
			animationSpec = spring(stiffness = Spring.StiffnessVeryLow)
		)
	}

	Row(
		modifier = Modifier
			.fillMaxWidth()
			.padding(16.dp)
			.animateContentSize(),
		horizontalArrangement = Arrangement.Center
	) {
		Image(
			modifier = Modifier
				.weight(weight = 0.1f)
				.align(Alignment.CenterVertically),
			painter = painterResource(id = getRankIcons()[count]),
			contentDescription = null
		)
		Column(
			modifier = Modifier
				.weight(weight = 0.9f)
				.padding(start = 8.dp),
		) {
			Row(
				horizontalArrangement = Arrangement.SpaceBetween
			) {
				Text(
					modifier = Modifier.weight(weight = 0.7f),
					text = getRanksList()[count],
					style = MaterialTheme.typography.subtitle1,
					maxLines = 1,
					overflow = TextOverflow.Ellipsis
				)
				if (animatedProgress.value == 1f) {
					Text(
						modifier = Modifier
							.padding(start = 8.dp)
							.weight(weight = 0.3f)
							.alpha(animatedAlphaText.value),
						text = "Completed",
						style = MaterialTheme.typography.subtitle1,
						color = Color.Green,
						maxLines = 1,
						textAlign = TextAlign.End
					)
				}
			}
			LinearProgressIndicator(
				modifier = Modifier
					.fillMaxWidth()
					.padding(top = 8.dp),
				progress = animatedProgress.value
			)
			if (expanded) {
				Text(
					modifier = Modifier
						.align(Alignment.End)
						.padding(top = 8.dp),
					text = getTimeRanks()[count],
					style = MaterialTheme.typography.subtitle2,
					maxLines = 1,
					overflow = TextOverflow.Ellipsis
				)
			}
		}
	}
}

@Composable
private fun DisabledHistoryBarRow() {
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.padding(16.dp),
		horizontalArrangement = Arrangement.Center
	) {
		IconButton(
			onClick = {  }
		) {
			Icon(
				painter = rememberVectorPainter(image = Icons.Default.Lock),
				contentDescription = null
			)
		}
	}
}