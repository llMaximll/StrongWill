package com.github.llmaximll.strongwill.ui.common

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.progressSemantics
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun TimerCircularProgressBar(
	text: String,
	progress: Float,
	modifier: Modifier = Modifier,
	backgroundColor: Color = MaterialTheme.colors.background,
	color: Color = MaterialTheme.colors.primary,
	strokeWidth: Dp = 15.dp,
	onInfoClick: () -> Unit = {  }
) {
	val animateFloat: Float by animateFloatAsState(
		targetValue = progress,
		animationSpec = spring(stiffness = Spring.StiffnessVeryLow)
	)

	val stroke = with(LocalDensity.current) {
		Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
	}

	Box(
		modifier = Modifier
			.padding(8.dp)
			.size(250.dp),
		contentAlignment = Alignment.Center
	) {
		Canvas(
			modifier
				.fillMaxSize()
				.progressSemantics(progress)
				.shadow(
					elevation = 8.dp,
					shape = CircleShape
				)
				.border(
					width = 2.dp,
					color = color.copy(alpha = 0.3f),
					shape = CircleShape
				)
				.focusable()
		) {
			drawCircleBackground(backgroundColor)
			val startAngle = 270f
			val sweep = animateFloat * 360f
			drawCircularArc(startAngle, sweep, color, stroke)
		}
		Text(
			modifier = Modifier.padding(24.dp),
			text = text,
			maxLines = 2,
			overflow = TextOverflow.Ellipsis,
			style = MaterialTheme.typography.h5,
			textAlign = TextAlign.Center
		)
	}
}

private fun DrawScope.drawCircularArc(
	startAngle: Float,
	sweep: Float,
	color: Color,
	stroke: Stroke
) {
	val diameterOffset = stroke.width / 2
	val arcDimen = size.width - 2 * diameterOffset
	drawArc(
		color = color,
		startAngle = startAngle,
		sweepAngle = sweep,
		useCenter = false,
		topLeft = Offset(diameterOffset, diameterOffset),
		size = Size(arcDimen, arcDimen),
		style = stroke
	)
}

private fun DrawScope.drawCircleBackground(
	color: Color
) {
	drawCircle(
		color = color
	)
}

@Preview
@Composable
private fun TimerCircularProgressBarPreview() {
	TimerCircularProgressBar(
		text = "",
		progress = 0.6f
	)
}