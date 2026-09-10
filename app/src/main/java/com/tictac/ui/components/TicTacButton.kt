package com.tictac.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tictac.R
import com.tictac.core.ui.theme.TicTacTheme

@Composable
public fun TicTacButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    mode: TicTacButtonMode = TicTacButtonMode.Primary,
    size: TicTacButtonSize = TicTacButtonSize.M,
    enabled: Boolean = true,
) {
    val colors = buttonColors(mode = mode, enabled = enabled)
    val metrics = buttonMetrics(size)
    ButtonSurface(
        colors = colors,
        metrics = metrics,
        onClick = onClick,
        enabled = enabled,
        modifier = modifier,
    ) {
        ButtonLabel(text = text, color = colors.content, style = metrics.textStyle)
    }
}

/** Same button with an icon ahead of the label. The icon is tinted to match the label. */
@Composable
public fun TicTacButton(
    text: String,
    leadingIcon: Painter,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    mode: TicTacButtonMode = TicTacButtonMode.Primary,
    size: TicTacButtonSize = TicTacButtonSize.M,
    enabled: Boolean = true,
) {
    val colors = buttonColors(mode = mode, enabled = enabled)
    val metrics = buttonMetrics(size)
    ButtonSurface(
        colors = colors,
        metrics = metrics,
        onClick = onClick,
        enabled = enabled,
        modifier = modifier,
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(metrics.iconSpacing),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = leadingIcon,
                contentDescription = null,
                tint = colors.content,
                modifier = Modifier.size(metrics.iconSize),
            )
            ButtonLabel(text = text, color = colors.content, style = metrics.textStyle)
        }
    }
}

/** Same button with no label - just the icon. [contentDescription] is what a
 * screen reader announces, so it has to say what the button does. */
@Composable
public fun TicTacButton(
    icon: Painter,
    contentDescription: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    mode: TicTacButtonMode = TicTacButtonMode.Primary,
    size: TicTacButtonSize = TicTacButtonSize.M,
    enabled: Boolean = true,
) {
    val colors = buttonColors(mode = mode, enabled = enabled)
    val metrics = buttonMetrics(size)
    ButtonSurface(
        colors = colors,
        metrics = metrics,
        onClick = onClick,
        enabled = enabled,
        // Even padding, so an icon-only button comes out square.
        contentPadding = PaddingValues(metrics.iconOnlyPadding),
        modifier = modifier,
    ) {
        Icon(
            painter = icon,
            contentDescription = contentDescription,
            tint = colors.content,
            modifier = Modifier.size(metrics.iconSize),
        )
    }
}

/** The clickable pill every overload shares, so paint and metrics stay in one place. */
@Composable
private fun ButtonSurface(
    colors: ButtonColors,
    metrics: ButtonMetrics,
    onClick: () -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = metrics.contentPadding,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .defaultMinSize(minWidth = metrics.minHeight, minHeight = metrics.minHeight)
            .clip(TicTacTheme.shapes.button)
            .background(color = colors.container)
            .then(
                if (colors.border != null) {
                    Modifier.border(
                        width = BORDER_WIDTH,
                        color = colors.border,
                        shape = TicTacTheme.shapes.button,
                    )
                } else {
                    Modifier
                },
            )
            .clickable(enabled = enabled, onClick = onClick)
            .padding(contentPadding),
        contentAlignment = Alignment.Center,
        content = { content() },
    )
}

@Composable
private fun ButtonLabel(
    text: String,
    color: Color,
    style: TextStyle,
) {
    Text(
        text = text,
        style = style,
        color = color,
    )
}

/** Resolved paint for one button, so the layout above stays free of branching. */
private data class ButtonColors(
    val container: Color,
    val content: Color,
    val border: Color? = null,
)

/** Resolved metrics for one button - see [TicTacButtonSize]. */
private data class ButtonMetrics(
    val contentPadding: PaddingValues,
    val iconOnlyPadding: Dp,
    val iconSize: Dp,
    val iconSpacing: Dp,
    val minHeight: Dp,
    val textStyle: TextStyle,
)

@Composable
private fun buttonColors(
    mode: TicTacButtonMode,
    enabled: Boolean,
): ButtonColors {
    val colors = TicTacTheme.colors
    if (!enabled) {
        return when (mode) {
            TicTacButtonMode.Primary,
            TicTacButtonMode.Secondary,
                -> ButtonColors(
                container = colors.surfaceVariant,
                content = colors.onSurfaceVariant,
            )

            TicTacButtonMode.Tertiary -> ButtonColors(
                container = Color.Transparent,
                content = colors.onSurfaceVariant,
                border = colors.outline,
            )
        }
    }
    return when (mode) {
        TicTacButtonMode.Primary -> ButtonColors(
            container = colors.accent,
            content = colors.onAccent,
        )

        TicTacButtonMode.Secondary -> ButtonColors(
            container = colors.surfaceVariant,
            content = colors.onSurface,
        )

        TicTacButtonMode.Tertiary -> ButtonColors(
            container = Color.Transparent,
            content = colors.onSurface,
            border = colors.outline,
        )
    }
}

@Composable
private fun buttonMetrics(size: TicTacButtonSize): ButtonMetrics {
    val typography = TicTacTheme.typography
    return when (size) {
        TicTacButtonSize.S -> ButtonMetrics(
            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp),
            iconOnlyPadding = 8.dp,
            iconSize = 16.dp,
            iconSpacing = 6.dp,
            minHeight = 36.dp,
            textStyle = typography.label,
        )

        TicTacButtonSize.M -> ButtonMetrics(
            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 14.dp),
            iconOnlyPadding = 14.dp,
            iconSize = 24.dp,
            iconSpacing = 12.dp,
            minHeight = 52.dp,
            textStyle = typography.bodyMedium,
        )

        TicTacButtonSize.L -> ButtonMetrics(
            contentPadding = PaddingValues(horizontal = 32.dp, vertical = 18.dp),
            iconOnlyPadding = 18.dp,
            iconSize = 28.dp,
            iconSpacing = 14.dp,
            minHeight = 64.dp,
            textStyle = typography.button,
        )
    }
}

private val BORDER_WIDTH = 2.dp

@PreviewLightDark
@Composable
private fun TicTacButtonPreview() {
    TicTacTheme {
        Column(
            modifier = Modifier
                .background(TicTacTheme.colors.background)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            TicTacButtonMode.entries.forEach { mode ->
                TicTacButton(text = "New game - $mode", onClick = {}, mode = mode)
                TicTacButton(
                    text = "Disabled - $mode",
                    onClick = {},
                    mode = mode,
                    enabled = false,
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun TicTacButtonSizePreview() {
    TicTacTheme {
        Column(
            modifier = Modifier
                .background(TicTacTheme.colors.background)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            TicTacButtonSize.entries.forEach { size ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    TicTacButton(text = "Size $size", onClick = {}, size = size)
                    TicTacButton(
                        text = "100",
                        leadingIcon = painterResource(R.drawable.ic_coin1x_16),
                        onClick = {},
                        mode = TicTacButtonMode.Secondary,
                        size = size,
                    )
                    TicTacButton(
                        icon = painterResource(R.drawable.ic_settings_24),
                        contentDescription = "Settings",
                        onClick = {},
                        mode = TicTacButtonMode.Tertiary,
                        size = size,
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun TicTacButtonWithIconPreview() {
    TicTacTheme {
        Column(
            modifier = Modifier
                .background(TicTacTheme.colors.background)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            TicTacButtonMode.entries.forEach { mode ->
                TicTacButton(
                    text = "Play bot - $mode",
                    leadingIcon = painterResource(R.drawable.ic_bot_24),
                    onClick = {},
                    mode = mode,
                )
                TicTacButton(
                    text = "Settings - $mode",
                    leadingIcon = painterResource(R.drawable.ic_settings_24),
                    onClick = {},
                    mode = mode,
                )
                TicTacButton(
                    text = "Disabled - $mode",
                    leadingIcon = painterResource(R.drawable.ic_settings_24),
                    onClick = {},
                    mode = mode,
                    enabled = false,
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun TicTacButtonIconOnlyPreview() {
    TicTacTheme {
        Row(
            modifier = Modifier
                .background(TicTacTheme.colors.background)
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TicTacButtonMode.entries.forEach { mode ->
                TicTacButton(
                    icon = painterResource(R.drawable.ic_settings_24),
                    contentDescription = "Settings",
                    onClick = {},
                    mode = mode,
                )
            }
            TicTacButton(
                icon = painterResource(R.drawable.ic_reverse_left_24),
                contentDescription = "Undo move",
                onClick = {},
                mode = TicTacButtonMode.Tertiary,
                enabled = false,
            )
        }
    }
}
