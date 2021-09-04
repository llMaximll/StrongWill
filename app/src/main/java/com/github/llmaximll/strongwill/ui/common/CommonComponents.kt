package com.github.llmaximll.strongwill.ui.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
@OptIn(ExperimentalComposeUiApi::class)
fun CurrentTextField(
	label: String = "",
	text: String = "",
	focusManager: FocusManager,
	isError: Boolean = false,
	onTextChanged: (String) -> Unit
) {
	OutlinedTextField(
		modifier = Modifier.fillMaxWidth(),
		value = text,
		onValueChange = {
			onTextChanged(it)
		},
		label = { Text(label) },
		singleLine = true,
		keyboardOptions = KeyboardOptions(
			keyboardType = KeyboardType.Text,
			imeAction = ImeAction.Done
		),
		keyboardActions = KeyboardActions(
			onDone = {
				focusManager.moveFocus(FocusDirection.Down)
			}
		),
		isError = isError,
		shape = RoundedCornerShape(8.dp)
	)
}

@Composable
@OptIn(ExperimentalComposeUiApi::class)
fun DescriptionTextField(
	label: String = "",
	text: String = "",
	focusManager: FocusManager,
	onTextChanged: (String) -> Unit
) {
	OutlinedTextField(
		modifier = Modifier.fillMaxWidth(),
		value = text,
		onValueChange = {
			onTextChanged(it)
		},
		label = { Text(label) },
		keyboardOptions = KeyboardOptions(
			keyboardType = KeyboardType.Text,
			imeAction = ImeAction.Done
		),
		keyboardActions = KeyboardActions(
			onDone = {
				focusManager.moveFocus(FocusDirection.Down)
			}
		),
		shape = RoundedCornerShape(8.dp)
	)
}

@Composable
fun CurrentButton(
	text: String,
	onEventSent: () -> Unit = {  }
) {
	Button(
		modifier = Modifier
			.fillMaxWidth()
			.padding(top = 22.dp, start = 8.dp, end = 8.dp, bottom = 22.dp),
		onClick = { onEventSent() },
		shape = RoundedCornerShape(10.dp)
	) {
		Text(
			text = text,
			style = MaterialTheme.typography.h6
		)
	}
}