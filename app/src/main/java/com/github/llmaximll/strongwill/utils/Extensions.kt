package com.github.llmaximll.strongwill.utils

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

@SuppressLint("UnnecessaryComposedModifier")
inline fun Modifier.noRippleClickable(crossinline onClick: () -> Unit): Modifier = composed {
	clickable(
		indication = null,
		interactionSource = remember { MutableInteractionSource() })
	{
		onClick()
	}
}