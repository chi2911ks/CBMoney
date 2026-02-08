package com.cbmoney.presentation.category.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import com.cbmoney.R
import com.cbmoney.domain.model.Category
import com.cbmoney.presentation.common.IconResolver
import com.cbmoney.presentation.theme.CBMoneyColors
import com.cbmoney.presentation.theme.CBMoneyShapes
import com.cbmoney.presentation.theme.CBMoneyTypography
import com.cbmoney.presentation.theme.Spacing
import kotlinx.coroutines.launch

@Composable
fun CategoryCard(
    modifier: Modifier = Modifier,
    category: Category,
    onDelete: (Category) -> Unit,
    onEdit: (Category) -> Unit
) {
    val dismissState = rememberSwipeToDismissBoxState()
    val scope = rememberCoroutineScope()
    val isDefault = category.isDefault

    SwipeToDismissBox(
        state = dismissState,
        enableDismissFromEndToStart = !isDefault,
        backgroundContent = {
            val color= when (dismissState.dismissDirection) {
                    SwipeToDismissBoxValue.StartToEnd -> CBMoneyColors.Green2
                    SwipeToDismissBoxValue.EndToStart -> CBMoneyColors.Red2
                    else -> Color.Transparent
            }
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color)
                    .padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = when (dismissState.dismissDirection) {
                    SwipeToDismissBoxValue.StartToEnd -> Arrangement.Start
                    SwipeToDismissBoxValue.EndToStart -> Arrangement.End
                    else -> Arrangement.Start
                }
            ) {
                if (dismissState.dismissDirection == SwipeToDismissBoxValue.StartToEnd) {
                    Icon(
                        Icons.Default.Edit,
                        contentDescription = "Edit",
                        tint = Color.White
                    )
                }
                if (dismissState.dismissDirection == SwipeToDismissBoxValue.EndToStart) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = Color.White
                    )
                }
            }
        },
        onDismiss = { direction ->
            when (direction) {
                SwipeToDismissBoxValue.EndToStart -> {
                    onDelete(category)
                    scope.launch { dismissState.reset() }
                }
                SwipeToDismissBoxValue.StartToEnd -> {
                    onEdit(category)
                    scope.launch { dismissState.reset() }
                }
                else -> {
                    scope.launch { dismissState.reset() }
                }
            }
        },
    ) {
        val color = Color(category.iconColor.toColorInt())
        Row(
            modifier = modifier
                .fillMaxWidth()
                .background(CBMoneyColors.BackGround.BackgroundPrimary),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CBMoneyShapes.medium)
                    .background(color.copy(0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = IconResolver.getImageVector(category.icon),
                    contentDescription = null,
                    tint = color
                )
            }
            Spacer(modifier = Modifier.width(Spacing.sm))
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = category.name,
                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = CBMoneyTypography.Body.Medium.Bold
                )
                if (!isDefault){
                    Text(
                        text = stringResource(R.string.customize),
                        color = CBMoneyColors.Neutral.NeutralGray,
                        style = CBMoneyTypography.Body.Small.Regular
                    )
                }

            }

        }
    }
}





@Preview
@Composable
private fun CategoryCardPreview() {
//    CategoryCard(
//        category = Category(
//            name = "Lương",
//            icon = "attach_money",
//            id = 0,
//            type = CategoryType.EXPENSE,
//            iconColor = "#00B894",
//            order = 1,
//            createdAt = 0
//        )
//
//    )
}