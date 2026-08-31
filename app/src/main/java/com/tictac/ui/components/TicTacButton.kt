package com.tictac.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.tictac.core.ui.theme.TicTacTheme

@Composable
public fun TicTacButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Box(
        modifier = modifier
            .clip(TicTacTheme.shapes.button)
            .background(
                color = if (enabled) TicTacTheme.colors.accent else TicTacTheme.colors.surfaceVariant,
            )
            .clickable(enabled = enabled, onClick = onClick)
            .padding(horizontal = 24.dp, vertical = 14.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            style = TicTacTheme.typography.button,
            color = if (enabled) TicTacTheme.colors.onAccent else TicTacTheme.colors.onSurfaceVariant,
        )
    }
}

@PreviewLightDark
@Composable
private fun TicTacButtonPreview() {
    TicTacTheme {
        TicTacButton(text = "New game", onClick = {})
    }
}
