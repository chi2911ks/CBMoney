package com.cbmoney.presentation.components


import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.cbmoney.ui.theme.LightSageGreen

@Composable
fun OutlinedText(
    modifier: Modifier = Modifier,
    value: String,
    label: String = "",
    onValueChange: (String) -> Unit,
    placeholderText: String = "",
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isPasswordVisible: Boolean = true
) {
    Text(text = label, color = Color.Black)
    Spacer(Modifier.height(8.dp))
    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange(it) },
        modifier = modifier.fillMaxWidth(),
        placeholder = {
            Text(placeholderText)
        },
        singleLine = true,
        shape = RoundedCornerShape(24.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = LightSageGreen,
            unfocusedBorderColor = LightSageGreen,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.DarkGray,

            focusedPlaceholderColor = Color.Gray,
            unfocusedPlaceholderColor = Color.LightGray
        ),
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        visualTransformation = if (isPasswordVisible)
            VisualTransformation.None
        else
            PasswordVisualTransformation(),
    )
}