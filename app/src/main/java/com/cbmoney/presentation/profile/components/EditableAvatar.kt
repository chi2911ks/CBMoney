package com.cbmoney.presentation.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.cbmoney.R
import com.cbmoney.presentation.theme.CBMoneyColors


@Composable
fun EditableAvatar(
//    imageRes: Int,
    imageURL: String? = null,
    onEditClick: () -> Unit,
    onAvatarClick: () -> Unit
) {

    Box(
        modifier = Modifier.size(150.dp),
    ) {


        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageURL)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.avatar_boy),
            error = painterResource(R.drawable.avatar_boy),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
                .border(1.dp, Color.Gray, CircleShape)
                .clickable { onAvatarClick() }
        )

        // Edit icon
        Box(
            modifier = Modifier
                .size(28.dp)
                .align(Alignment.BottomEnd)
                .offset(x = (-4).dp, y = (-4).dp)
                .clip(CircleShape)
                .background(CBMoneyColors.Primary.Primary ) // xanh lá
                .clickable { onEditClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit",
                tint = Color.White,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Preview
@Composable
private fun EditableAvatarPreview() {

        EditableAvatar(
            onEditClick = {},
            onAvatarClick = {}
        )

}
