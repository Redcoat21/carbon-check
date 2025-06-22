package com.carbondev.carboncheck.presentation.common.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.carbondev.carboncheck.presentation.ui.theme.Green80

@Composable
fun LoadingRing(
    size: Dp = 64.dp,
    color: Color = Green80,
    strokeWidth: Dp = 6.dp,
    backGroundColor: Color = Color.Black.copy(alpha = 0.4f)
) {
    Box(
        modifier = Modifier
            .zIndex(5f)
            .fillMaxSize()
            .background(backGroundColor)
            .clickable { },
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(size),
            color = color,
            strokeWidth = strokeWidth
        )
    }
}