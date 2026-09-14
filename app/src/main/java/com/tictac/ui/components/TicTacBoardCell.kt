package com.tictac.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tictac.core.ui.theme.TicTacTheme
import com.tictac.domain.model.Mark

/**
 * One square of the board. The caller sizes it through [modifier], since a cell on a
 * 12x12 board is a fraction of a cell on a 3x3 one.
 */
@Composable
public fun TicTacBoardCell(
    mark: Mark?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    borderWidth: Dp = 2.dp,
    shape: CornerBasedShape = TicTacTheme.shapes.boardCellLarge,
    markSize: TicTacMarkSize = TicTacMarkSize.L,
) {
    val colors = TicTacTheme.colors
    val backgroundColor = when(mark) {
        Mark.X -> colors.markX
        Mark.O -> colors.markO
        else -> colors.surface
    }
    Box(
        modifier = modifier
            .clip(shape)
            .background(backgroundColor)
            .border(
                width = borderWidth,
                color = colors.boardGrid,
                shape = shape,
            )
            .clickable(enabled = enabled && mark == null, onClick = onClick)
            .semantics { contentDescription = mark?.name ?: "empty cell" },
        contentAlignment = Alignment.Center,
    ) {
        if (mark != null) {
            Icon(
                painter = painterResource(markSize.iconFor(mark)),
                tint = TicTacTheme.colors.onAccent,
                contentDescription = "",
                modifier = Modifier.size(markSize.dimension),
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun TicTacBoardCellPreview() {
    TicTacTheme {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TicTacBoardCell(
                mark = Mark.X,
                onClick = {},
                shape = TicTacTheme.shapes.boardCellLarge,
                markSize = TicTacMarkSize.L,
                modifier = Modifier.size(88.dp),
            )
            TicTacBoardCell(
                mark = Mark.O,
                onClick = {},
                shape = TicTacTheme.shapes.boardCellMedium,
                markSize = TicTacMarkSize.M,
                borderWidth = 1.dp,
                modifier = Modifier.size(48.dp),
            )
            TicTacBoardCell(
                mark = Mark.X,
                onClick = {},
                shape = TicTacTheme.shapes.boardCellSmall,
                markSize = TicTacMarkSize.S,
                borderWidth = 1.dp,
                modifier = Modifier.size(28.dp),
            )
        }
    }
}
