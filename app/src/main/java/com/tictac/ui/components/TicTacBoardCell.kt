package com.tictac.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.tictac.core.ui.theme.TicTacTheme
import com.tictac.domain.model.Mark

/** One square of the 3x3 board. */
@Composable
public fun TicTacBoardCell(
    mark: Mark?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isWinning: Boolean = false,
    enabled: Boolean = true,
) {
    val colors = TicTacTheme.colors
    Box(
        modifier = modifier
            .size(88.dp)
            .clip(TicTacTheme.shapes.boardCell)
            .background(if (isWinning) colors.winHighlight else colors.boardCell)
            .border(
                width = 2.dp,
                color = colors.boardGrid,
                shape = TicTacTheme.shapes.boardCell,
            )
            .clickable(enabled = enabled && mark == null, onClick = onClick)
            .semantics { contentDescription = mark?.name ?: "empty cell" },
        contentAlignment = Alignment.Center,
    ) {
        if (mark != null) {
            Text(
                text = mark.name,
                style = TicTacTheme.typography.mark,
                color = when {
                    isWinning -> colors.onAccent
                    mark == Mark.X -> colors.markX
                    else -> colors.markO
                },
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun TicTacBoardCellPreview() {
    TicTacTheme {
        TicTacBoardCell(mark = Mark.X, onClick = {})
    }
}
