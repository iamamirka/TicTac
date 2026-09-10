package com.tictac.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.tictac.core.ui.theme.TicTacTheme
import com.tictac.ui.model.GameModeUi

/**
 * A pill with one selected segment. Selection is driven entirely by [selected],
 * so the caller (the MVI state) is the single source of truth.
 */
@Composable
public fun <T : SegmentOption> TicTacSegmentedControl(
    options: List<T>,
    selected: T,
    onSelect: (T) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Row(
        modifier = modifier
            .padding(4.dp)
            .clip(TicTacTheme.shapes.button)
            .border(
                width = 1.dp,
                color = TicTacTheme.colors.surfaceVariant,
                shape = TicTacTheme.shapes.button
            )
            .padding(4.dp)
    ) {
        options.forEach { option ->
            Segment(
                text = option.text(),
                isSelected = option == selected,
                onClick = { onSelect(option) },
                enabled = enabled,
                trailingIcon = option.icon(),
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Composable
private fun Segment(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    enabled: Boolean,
    trailingIcon: Painter? = null,
    modifier: Modifier = Modifier,
) {
    val colors = TicTacTheme.colors
    val background by animateColorAsState(
        targetValue = if (isSelected) colors.surfaceVariant else Color.Transparent,
        label = "segmentBackground",
    )
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clip(TicTacTheme.shapes.button)
            .background(background)
            .clickable(enabled = enabled, role = Role.RadioButton, onClick = onClick)
            .padding(vertical = 12.dp),
    ) {
        Text(
            text = text,
            style = TicTacTheme.typography.bodyMedium,
            color = colors.onSurface,
        )
        if (trailingIcon != null) {
            Spacer(modifier = Modifier.width(12.dp))
            Icon(
                painter = trailingIcon,
                contentDescription = null,
                tint = colors.onSurface,
            )
        }
    }
}

private const val DISABLED_ALPHA = 0.4f

@PreviewLightDark
@Composable
private fun TicTacSegmentedControlPreview() {
    TicTacTheme {
        TicTacSegmentedControl(
            options = GameModeUi.entries,
            selected = GameModeUi.WithBot,
            onSelect = {},
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
