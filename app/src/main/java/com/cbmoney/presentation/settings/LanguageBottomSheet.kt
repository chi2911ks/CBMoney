package com.cbmoney.presentation.settings

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cbmoney.R
import com.cbmoney.presentation.settings.model.Languages
import com.cbmoney.presentation.theme.CBMoneyColors
import com.cbmoney.presentation.theme.CBMoneyShapes
import com.cbmoney.presentation.theme.CBMoneyTypography

import com.cbmoney.presentation.theme.Spacing
import com.cbmoney.utils.exts.getLanguageCode
import com.cbmoney.utils.exts.handleOnSaveLanguage


@Composable
fun LanguageBottomSheet() {
    val context = LocalContext.current
//    var currentCode by remember { mutableStateOf("vi") }
    var currentCode by remember { mutableStateOf(context.getLanguageCode()) }

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(Spacing.md),
    ){
        Text(
            text = stringResource(R.string.select_language),
            style = CBMoneyTypography.Title.Large.Medium
        )
        Text(
            text = stringResource(R.string.select_display_language),
            color = Color.Gray,
            style = CBMoneyTypography.Body.Small.Regular
        )
        Spacer(Modifier.height(Spacing.sm))
        Languages.entries.forEach { language ->
            LanguageItem(
                onChangeLanguage = {currentCode = it.code},
                isSelected = currentCode == language.code,
                language = language,
                title = language.title,
                subtitle = language.subtitle,
                icon = language.icon
            )
            Spacer(Modifier.height(Spacing.sm))
        }
        Button(
            onClick = {
                context.handleOnSaveLanguage(currentCode)
            },
            content = {
                Text(
                    text = stringResource(R.string.confirm),
                    style = MaterialTheme.typography.labelLarge
                )
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = CBMoneyColors.Primary.Primary ,
                contentColor = Color.Black
            ),
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
fun LanguageItem(
    onChangeLanguage: (Languages) -> Unit,
    isSelected: Boolean,
    language: Languages,
    title: String,
    subtitle: String,
    @DrawableRes icon: Int,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(CBMoneyShapes.large)
            .clickable {
                onChangeLanguage(language)
            }
            .border(
                2.dp,
                CBMoneyColors.Primary.Primary.copy(alpha = 0.3f),
                CBMoneyShapes.large)
            .background(Color.White)
            .padding(Spacing.md),
    ) {
        Row(
            modifier = Modifier.align(Alignment.CenterStart),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(icon),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.width(Spacing.md))
            Column()
            {
                Text(
                    text = title,
                    style = CBMoneyTypography.Title.Medium.Medium
                )
                Text(
                    text = subtitle,
                    color = Color.Gray,
                    style = CBMoneyTypography.Body.Small.Regular
                )
            }
        }
        RadioButton(
            selected = isSelected,
            onClick = null,
            colors = RadioButtonDefaults.colors(
                selectedColor = CBMoneyColors.Primary.Primary
            ),
            modifier = Modifier
                .align(Alignment.CenterEnd),
        )

    }
}

@Preview
@Composable
private fun LanguageItemPreview() {
    LanguageBottomSheet()
}

