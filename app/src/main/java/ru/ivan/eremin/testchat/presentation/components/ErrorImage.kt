package ru.ivan.eremin.testchat.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.ivan.eremin.testchat.R

@Composable
fun ErrorImage(
    modifier: Modifier = Modifier,
    tint: Color = Color.Gray,
    imageSize: Float = DEFAULT_ERROR_IMAGE_SIZE
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_error_image),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(imageSize),
            tint = tint
        )
    }
}

const val DEFAULT_ERROR_IMAGE_SIZE = 0.50f

@Preview
@Composable
private fun ErrorImagePreview() {
    MaterialTheme {
        ErrorImage(modifier = Modifier.size(200.dp))
    }
}
