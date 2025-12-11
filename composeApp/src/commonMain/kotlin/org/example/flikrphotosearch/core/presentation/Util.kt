package org.example.flikrphotosearch.core.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

internal const val SPACING_EXTRA_SMALL = 4
internal const val SPACING_SMALL = 8
internal const val SPACING_MEDIUM = 16
internal const val SPACING_LARGE = 24
internal const val SPACING_EXTRA_LARGE = 48


internal fun screenPaddings(
    append: PaddingValues? = null,
) = PaddingValues(
    start = SPACING_LARGE.dp,
    top = SPACING_EXTRA_LARGE.dp + (append?.calculateTopPadding() ?: 0.dp),
    end = SPACING_LARGE.dp,
    bottom = SPACING_LARGE.dp + (append?.calculateBottomPadding() ?: 0.dp)
)

@Composable
internal fun keyboardAsState(): State<Boolean> {
    val isImeVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0
    return rememberUpdatedState(isImeVisible)
}

@Composable
internal fun StrikeView(modifier: Modifier){
    Canvas(
        modifier = modifier
    ) {
        val strokeWidth = 2.dp.toPx()
        drawLine(
            color = Color.Gray,
            start = Offset(0f, 0f),
            end = Offset(size.width, size.height),
            strokeWidth = strokeWidth
        )
    }
}
