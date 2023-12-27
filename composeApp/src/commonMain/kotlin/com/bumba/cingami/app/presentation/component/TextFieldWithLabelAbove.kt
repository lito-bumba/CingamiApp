package com.bumba.cingami.app.presentation.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TextFieldWithLabelAbove(
    labelAboveTextField: String,
    labelInTextField: String,
    value: String,
    onValueChange: (String) -> Unit,
    isSingleLine: Boolean,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    modifier: Modifier = Modifier.fillMaxWidth()
) {
    Text(
        text = labelAboveTextField,
        style = MaterialTheme.typography.bodyMedium
    )
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(labelInTextField) },
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = isSingleLine,
        modifier = modifier
    )
}