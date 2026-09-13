package com.tictac.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tictac.core.ui.theme.TicTacTheme

@Composable
internal fun FieldSizeTile(
    tileSize: Int,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val tileStyle = if (isSelected) TicTacTheme.colors.onSurface to 4.dp else TicTacTheme.colors.surface to 2.dp
    val cellClipping = if (tileSize < 6) 8.dp else 4.dp
    val cellSpacing = if (tileSize < 6) 4.dp else 2.dp
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .border(
                color = tileStyle.first,
                width = tileStyle.second,
                shape = TicTacTheme.shapes.medium,
            )
            .clip(TicTacTheme.shapes.medium)
            .clickable(onClick = onClick)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(cellSpacing),
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            repeat(tileSize) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(cellSpacing),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    repeat(tileSize) {
                        CellTile(
                            cellClipping = cellClipping,
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(1f)
                        )
                    }
                }
            }
        }
        SizeLabel(
            size = tileSize,
            isSelected = isSelected,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
private fun CellTile(
    cellClipping: Dp,
    modifier: Modifier
) {
    Box(
        modifier = modifier.background(
            color = TicTacTheme.colors.surfaceVariant,
            shape = RoundedCornerShape(cellClipping),
        )
    )
}

@Composable
private fun SizeLabel(
    size: Int,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    val textValue = "${size}x$size"
    val labelBackgroundColor = if (isSelected) TicTacTheme.colors.onSurfaceVariant else TicTacTheme.colors.surface
    val labelTextColor = if (isSelected) TicTacTheme.colors.surface else TicTacTheme.colors.onSurfaceVariant
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.background(
            color = labelBackgroundColor,
            shape = TicTacTheme.shapes.large
        )
    ) {
        Text(
            text = textValue,
            style = TicTacTheme.typography.bodyLarge,
            color = labelTextColor,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
        )
    }
}

@PreviewLightDark
@Composable
private fun MainScreenWithFriendPreview() {
    val tileSize = 162.dp
    TicTacTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                FieldSizeTile(
                    tileSize = 3,
                    isSelected = false,
                    onClick = {},
                    modifier = Modifier.size(tileSize)
                )
                VerticalDivider(modifier = Modifier.height(96.dp))
                FieldSizeTile(
                    tileSize = 6,
                    isSelected = true,
                    onClick = {},
                    modifier = Modifier.size(tileSize)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                FieldSizeTile(
                    tileSize = 9,
                    isSelected = false,
                    onClick = {},
                    modifier = Modifier.size(tileSize)
                )
                VerticalDivider(modifier = Modifier.height(96.dp))
                FieldSizeTile(
                    tileSize = 12,
                    isSelected = true,
                    onClick = {},
                    modifier = Modifier.size(tileSize)
                )
            }
        }
    }
}